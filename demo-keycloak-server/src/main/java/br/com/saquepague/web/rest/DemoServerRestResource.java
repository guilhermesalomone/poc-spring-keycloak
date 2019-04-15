package br.com.saquepague.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * DemoServerRest controller
 */
@RestController
@RequestMapping("/api/demo-server-rest")
public class DemoServerRestResource {


    
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ClientRequest clientFeign;
	
	
	@GetMapping("/rest-template")
	public ResponseEntity<String> testarAutenticacao() {

		
//		TODO: Inserir Manual
//		KeycloakClientUtils facade = new KeycloakClientUtils( //
//				"http://localhost:8080/auth" //
//				, "demo-realm" //
//				, "demo-client" //
//				, "cd876d55-d8ff-4f51-9e17-e8d78187b409" //
//		);
//
//		
//		HttpHeaders headers = new HttpHeaders();
//
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.add(HeaderParameters.AUTHORIZATION, HeaderParameters.BEARER + 
//		facade.getAccessTokenString());
//		
//		return restTemplate.exchange("http://localhost:3001/api/demo-client-rest/teste", HttpMethod.GET,
//				new HttpEntity<>("parameters", headers), String.class);
		
		return restTemplate.getForEntity("http://localhost:3001/api/demo-client-rest/teste", String.class);
		
	}

	@GetMapping("/rest-feign")
	public ResponseEntity<String> testarAutenticacao2() {

//		TODO: Inserir Manual
//		return clientFeign.testeClientHeader(
//				"Bearer "
//				+ "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJtUktnQ1FxVktBaDF6bl9tX0xiSTNSd0dEdGhmTGpvRUczOVctV3NZVTEwIn0.eyJqdGkiOiIyODBmMmJjMC0yNmU5LTQ4OWQtOWYyYi05MGEwN2YzZDhiZGIiLCJleHAiOjE1NDUxNDg0NTEsIm5iZiI6MCwiaWF0IjoxNTQ1MTQ4MTUxLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvZGVtby1yZWFsbSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI4NzNjZWJhNy02OTc3LTQwZTItOWE0NC04OWIwZmVkNzhlZGEiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJkZW1vLWNsaWVudCIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6IjMyMDQxMzU5LTk2OGMtNDM4ZS04MTkxLTgxZDY0ZWExYmVkNiIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImRlbW8tY2xpZW50Ijp7InJvbGVzIjpbInVtYV9wcm90ZWN0aW9uIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJjbGllbnRIb3N0IjoiMTcyLjE3LjAuMSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiY2xpZW50SWQiOiJkZW1vLWNsaWVudCIsIlVzZXJfbmFtZSI6InNlcnZpY2UtYWNjb3VudC1kZW1vLWNsaWVudCIsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC1kZW1vLWNsaWVudCIsImNsaWVudEFkZHJlc3MiOiIxNzIuMTcuMC4xIiwiZW1haWwiOiJzZXJ2aWNlLWFjY291bnQtZGVtby1jbGllbnRAcGxhY2Vob2xkZXIub3JnIn0.XKgJFE01B3Ss48-BFEVFviSG9vxiqcl6jG8VJ6FvbweT5qeQKaSP38eP2RJZ0l8y_59GD4dKuL2_Of2UyF3o-qtVsNHLtsB41Spt4g25WiZSqBo7J1WtU6lHAL_H8PfsLRaQGvUIAPBhZGhLFg8tw6M1C_NzkKxyS_TLkiem5384zywSB0g1OpaDtVMkbAjEC4AyCFBLajnF4A8YsgIWyJzKAuBnDdrpV-1u4PrPCOg-lfUJ2XGOgZUJA2Mp58oFEZLZ18x-7vuwTWVBjunebkHNVsb1c3h8oVYxK9M1KPhWgvU-fN8_4yfeUgIMLaTVMWtOUdpCVnAO4O1d4T527g"
//				);

		return clientFeign.testeClient();
				
	}

}
