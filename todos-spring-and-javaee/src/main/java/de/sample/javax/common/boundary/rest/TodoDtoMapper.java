package de.sample.javax.common.boundary.rest;

import de.sample.javax.common.domain.Todo;
import org.mapstruct.Mapper;

@Mapper
public interface TodoDtoMapper {

    Todo map(TodoDto source);

    TodoDto map(Todo source);

}
