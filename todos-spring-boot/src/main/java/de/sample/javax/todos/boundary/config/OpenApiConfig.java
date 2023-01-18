package de.sample.javax.todos.boundary.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

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
