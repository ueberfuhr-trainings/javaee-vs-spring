package de.sample.javax.todos.boundary;

import de.sample.javax.todos.domain.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoUiDtoMapper {

    Todo map(TodoUiDto source);

    TodoUiDto map(Todo source);

}
