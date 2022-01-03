package de.sample.javax.todos.boundary;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition( //
		info = @Info( //
				title = "Todos Management API", //
				version = "1.0", //
				description = "A REST API to manage todos." //
		) //
)
@Configuration
public class OpenApiConfig {

}
