package de.sample.javax.javaee.boundary.rest;

import de.sample.javax.common.boundary.rest.TodoDtoMapper;
import org.mapstruct.factory.Mappers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class TodoDtoMapperProducer {

    @Produces
    public TodoDtoMapper todoDtoMapper() {
        return Mappers.getMapper(TodoDtoMapper.class);
    }

}
