package de.sample.javax.servlets;

import lombok.Data;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.Dependent;

@Dependent
@ConfigProperties(prefix = "hello")
@Data
public class HelloWorldConfig {

    @ConfigProperty(defaultValue = "text/html")
    String contentType;
    String pattern = "<h1>Hello {0}!</h1>";
    String defaultName = "World";

}
