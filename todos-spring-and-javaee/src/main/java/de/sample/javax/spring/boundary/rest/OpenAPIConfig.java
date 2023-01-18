package de.sample.javax.spring.boundary.rest;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SpringDocConfiguration;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springdoc.core.SwaggerUiOAuthProperties;
import org.springdoc.webmvc.core.SpringDocWebMvcConfiguration;
import org.springdoc.webmvc.ui.SwaggerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Import({
  SpringDocConfiguration.class,
  SpringDocWebMvcConfiguration.class,
  SpringDocConfigProperties.class,
  SwaggerConfig.class,
  SwaggerUiOAuthProperties.class
})
public class OpenAPIConfig {

    @Bean
    SwaggerUiConfigProperties swaggerUiConfig() {
        SwaggerUiConfigProperties result = new SwaggerUiConfigProperties();
        result.setConfigUrl("/spring/v3/api-docs/swagger-config");
        result.setDisableSwaggerDefaultUrl(true);
        return result;
    }

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
          .components(new Components())
          .info(new Info().title("Todos Management Service")
            .description("Manages todos, topics, priorities and assignees.").version("1.0"));
    }

    @Bean
    public WebMvcConfigurer configureWeb() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                  .allowedOrigins("https://editor.swagger.io");
            }

        };
    }
}
