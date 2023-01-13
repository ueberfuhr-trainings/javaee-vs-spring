package de.sample.javax.todos.persistence;

import de.sample.javax.todos.domain.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoEntityMapper {

    Todo map(TodoEntity source);

    TodoEntity map(Todo source);

}
