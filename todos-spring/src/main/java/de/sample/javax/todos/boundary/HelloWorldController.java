package de.sample.javax.todos.boundary;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {
	
	@GetMapping(value = "/hello", produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String sayHello(@RequestParam(name = "name", defaultValue = "World") String name) {
		return "<h1>Hello " + name + "</h1>";
	}
	
	@GetMapping(value="/hello-jsp", produces = MediaType.TEXT_HTML_VALUE)
	public String sayHelloWithJsp(
		@RequestParam(defaultValue = "World") String name,
		Model model
			) {
		model.addAttribute("nameToDisplay", name);
		return "helloworld";
	}

}
