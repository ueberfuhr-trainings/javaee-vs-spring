package de.sample.javax.todos.config;

import de.sample.javax.todos.boundary.WebConfig;
import de.sample.javax.todos.persistence.PersistenceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "de.sample.javax.todos")
@Import({ WebConfig.class, PersistenceConfig.class })
public class AppConfig {

}
