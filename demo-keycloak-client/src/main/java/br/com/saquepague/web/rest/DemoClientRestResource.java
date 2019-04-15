package br.com.saquepague.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DemoClientRest controller
 */
@RestController
@RequestMapping("/api/demo-client-rest")
public class DemoClientRestResource {

    private final Logger log = LoggerFactory.getLogger(DemoClientRestResource.class);

    /**
    * GET defaultAction
    */
    @GetMapping("/default-action")
    public String defaultAction() {
        return "defaultAction";
    }
    
	@GetMapping("/teste")
	public ResponseEntity<String> testarAutenticacao(){
		return ResponseEntity.ok("Ok");
	}

}
