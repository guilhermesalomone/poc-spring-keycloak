package br.com.saquepague.client;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import br.com.saquepague.security.oauth2.AuthorizationHeaderUtil;
import br.com.saquepague.web.rest.util.HeaderParameters;
import br.com.saquepague.web.rest.util.KeycloakClientUtils;

@Component
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

	@Autowired
	private KeycloakClientUtils keycloakClient;
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		if (AuthorizationHeaderUtil.getAuthorizationHeader().isPresent()) {

			request.getHeaders().add(HeaderParameters.AUTHORIZATION,
					AuthorizationHeaderUtil.getAuthorizationHeader().get());

		} else {

			String token = keycloakClient.getAccessTokenString();
			request.getHeaders().add(HeaderParameters.AUTHORIZATION, HeaderParameters.BEARER + token);
		}
		
		return execution.execute(request, body);
	}
}
