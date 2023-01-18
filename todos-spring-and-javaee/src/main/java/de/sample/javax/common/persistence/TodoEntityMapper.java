package de.sample.javax.common.persistence;

import de.sample.javax.common.domain.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface TodoEntityMapper {

    Todo map(TodoEntity source);

    TodoEntity map(Todo source);

    @Mapping(target = "id", ignore = true)
    void copy(Todo source, @MappingTarget TodoEntity target);

}
