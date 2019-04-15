package br.com.saquepague.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import br.com.saquepague.client.RestTemplateInterceptor;

@Configuration
public class RestTamplateConfigure  {

	@Autowired
	private RestTemplateInterceptor interceptor;
	
	@Bean
	public RestTemplate restTamplate() {
		
		final RestTemplate template = new RestTemplate();
		
		template.getInterceptors().add(interceptor);
		
		return template;
	}
	
  
}
