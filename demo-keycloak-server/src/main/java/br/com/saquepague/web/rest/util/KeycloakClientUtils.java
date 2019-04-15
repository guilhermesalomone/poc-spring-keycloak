package br.com.saquepague.web.rest.util;

import java.math.BigInteger;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;
import java.util.Map;

import org.keycloak.OAuth2Constants;
import org.keycloak.RSATokenVerifier;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.common.VerificationException;
import org.keycloak.jose.jws.JWSHeader;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KeycloakClientUtils {

	private final String serverUrl;

	private final String realmId;

	private final String clientId;

	private final String clientSecret;

	public KeycloakClientUtils(@Value("${keycloak.server-url}") String serverUrl,
			@Value("${keycloak.realm-id}") String realmId, @Value("${keycloak.client-id}") String clientId,
			@Value("${keycloak.client-secret}") String clientSecret) {

		this.serverUrl = serverUrl;
		this.realmId = realmId;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	public AccessToken getAccessToken() {
		return getAccessToken(newKeycloakBuilderWithClientCredentials().build());
	}

	public String getAccessTokenString() {
		return getAccessTokenString(newKeycloakBuilderWithClientCredentials().build());
	}

	public AccessToken getAccessToken(String username, String password) {
		return getAccessToken(newKeycloakBuilderWithPasswordCredentials(username, password).build());
	}

	public String getAccessTokenString(String username, String password) {
		return getAccessTokenString(newKeycloakBuilderWithPasswordCredentials(username, password).build());
	}

	private AccessToken getAccessToken(Keycloak keycloak) {
		return extractAccessTokenFrom(keycloak, getAccessTokenString(keycloak));
	}

	private String getAccessTokenString(Keycloak keycloak) {
		AccessTokenResponse tokenResponse = getAccessTokenResponse(keycloak);
		return tokenResponse == null ? null : tokenResponse.getToken();
	}

	private AccessToken extractAccessTokenFrom(Keycloak keycloak, String token) {

		if (token == null) {
			return null;
		}

		try {
			RSATokenVerifier verifier = RSATokenVerifier.create(token);
			PublicKey publicKey = getRealmPublicKey(keycloak, verifier.getHeader());
			return verifier.realmUrl(getRealmUrl()) //
					.publicKey(publicKey) //
					.verify() //
					.getToken();
		} catch (VerificationException e) {
			return null;
		}
	}

	private KeycloakBuilder newKeycloakBuilderWithPasswordCredentials(String username, String password) {
		return newKeycloakBuilderWithClientCredentials() //
				.username(username) //
				.password(password) //
				.grantType(OAuth2Constants.PASSWORD);
	}

	private KeycloakBuilder newKeycloakBuilderWithClientCredentials() {
		return KeycloakBuilder.builder() //
				.realm(realmId) //
				.serverUrl(serverUrl)//
				.clientId(clientId) //
				.clientSecret(clientSecret) //
				.grantType(OAuth2Constants.CLIENT_CREDENTIALS);
	}

	private AccessTokenResponse getAccessTokenResponse(Keycloak keycloak) {
		try {
			return keycloak.tokenManager().getAccessToken();
		} catch (Exception ex) {
			return null;
		}
	}

	public String getRealmUrl() {
		return serverUrl + "/realms/" + realmId;
	}

	public String getRealmCertsUrl() {
		return getRealmUrl() + "/protocol/openid-connect/certs";
	}

	private PublicKey getRealmPublicKey(Keycloak keycloak, JWSHeader jwsHeader) {

		return retrievePublicKeyFromCertsEndpoint(jwsHeader);
	}

	@SuppressWarnings("unchecked")
	private PublicKey retrievePublicKeyFromCertsEndpoint(JWSHeader jwsHeader) {
		try {
			ObjectMapper om = new ObjectMapper();
			Map<String, Object> certInfos = om.readValue(new URL(getRealmCertsUrl()).openStream(), Map.class);

			List<Map<String, Object>> keys = (List<Map<String, Object>>) certInfos.get("keys");

			Map<String, Object> keyInfo = null;
			for (Map<String, Object> key : keys) {
				String kid = (String) key.get("kid");

				if (jwsHeader.getKeyId().equals(kid)) {
					keyInfo = key;
					break;
				}
			}

			if (keyInfo == null) {
				return null;
			}

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			String modulusBase64 = (String) keyInfo.get("n");
			String exponentBase64 = (String) keyInfo.get("e");

			// see org.keycloak.jose.jwk.JWKBuilder#rs256
			Decoder urlDecoder = Base64.getUrlDecoder();
			BigInteger modulus = new BigInteger(1, urlDecoder.decode(modulusBase64));
			BigInteger publicExponent = new BigInteger(1, urlDecoder.decode(exponentBase64));

			return keyFactory.generatePublic(new RSAPublicKeySpec(modulus, publicExponent));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
