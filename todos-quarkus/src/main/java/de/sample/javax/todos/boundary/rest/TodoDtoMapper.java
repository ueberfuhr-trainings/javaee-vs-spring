package de.sample.javax.todos.boundary.rest;

import de.sample.javax.todos.domain.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface TodoDtoMapper {

    Todo map(TodoDto source);

    TodoDto map(Todo source);

}
