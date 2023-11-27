package de.sample.javax.spring.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.enterprise.inject.spi.CDI;
import javax.validation.Validator;

@Configuration
public class ValidationConfig {

    @Bean
    Validator validator() {
        return CDI.current()
                .select(Validator.class)
                .get();
    }

    @Bean
    MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}
