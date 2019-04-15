package br.com.saquepague.web.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.saquepague.client.AuthorizedFeignClient;
import br.com.saquepague.config.FeignConfiguration;

@AuthorizedFeignClient(name="client-rest", url="http://localhost:3001", path = "/api/demo-client-rest", configuration = FeignConfiguration.class )
public interface ClientRequest {

	@GetMapping("/teste")
	public ResponseEntity<String> testeClient();
	
	@GetMapping("/teste")
	public ResponseEntity<String> testeClientHeader(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth);
}
