package de.sample.javax.todos.domain;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.stream.Stream;

@Validated
public interface TodosService {

    Stream<Todo> getTodos();

    Stream<Todo> getTodos(String title);

    void add(@Valid Todo todo);

    boolean remove(long id);

    Optional<Todo> findById(@Min(1) long id);

    boolean update(@Valid @NotNull Todo todo);

}
