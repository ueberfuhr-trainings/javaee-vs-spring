package de.sample.javax.common.domain;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Stream;

public interface TodosService {
    Stream<Todo> getTodos();

    Optional<Todo> findById(long id);

    Stream<Todo> getTodos(String title);

    long add(@Valid Todo todo);

    boolean update(@Valid Todo todo);

    boolean remove(long id);

    long getCount();
}
