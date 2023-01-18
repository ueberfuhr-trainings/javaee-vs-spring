package de.sample.javax.spring.boundary.rest;

import de.sample.javax.common.boundary.rest.TodoDtoMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TodoDtoMapperProducer {

    @Bean
    public TodoDtoMapper todoDtoMapper() {
        return Mappers.getMapper(TodoDtoMapper.class);
    }

}
