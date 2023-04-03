package de.sample.javax.todos.persistence.panache;

import de.sample.javax.todos.domain.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface TodoEntityMapper {

    Todo map(TodoEntity source);

    TodoEntity map(Todo source);

    void copy(Todo todo, @MappingTarget TodoEntity entity);

}
