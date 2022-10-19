package de.deutscherv.gb0500.schulung.spring.boundary;

import java.security.Principal;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test-security")
public class TestSecurityController {
	
	@GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String testSecurity(Principal principal, Authentication auth) {
		return 
				"principal:" + principal
				+ "\n"
				+ "\nname: " + principal.getName()
				+ "\nauthorities:" + auth.getAuthorities();
	}

}
