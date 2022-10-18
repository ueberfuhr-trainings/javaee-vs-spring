package de.deutscherv.gb0500.schulung.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import de.deutscherv.gb0500.schulung.spring.boundary.WebConfig;

@Configuration
@ComponentScan(basePackages = "de.deutscherv.gb0500.schulung.spring")
@Import({ WebConfig.class })
public class AppConfig {

}