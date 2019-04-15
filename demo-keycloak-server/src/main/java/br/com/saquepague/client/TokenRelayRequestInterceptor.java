package br.com.saquepague.client;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.saquepague.security.oauth2.AuthorizationHeaderUtil;
import br.com.saquepague.web.rest.util.HeaderParameters;
import br.com.saquepague.web.rest.util.KeycloakClientUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class TokenRelayRequestInterceptor implements RequestInterceptor {

	@Autowired
	private KeycloakClientUtils keycloakClient;
	
    @Override
    public void apply(RequestTemplate template) {
        if (AuthorizationHeaderUtil.getAuthorizationHeader().isPresent()) {
            template.header(HeaderParameters.AUTHORIZATION, AuthorizationHeaderUtil.getAuthorizationHeader().get());
        } else {
        	
        	template.header(HeaderParameters.AUTHORIZATION, 
        					HeaderParameters.BEARER + keycloakClient.getAccessTokenString());
        }
    }
}
