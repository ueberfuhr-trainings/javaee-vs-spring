package de.sample.javax.todos.domain;

import java.util.Optional;
import java.util.stream.Stream;

public interface TodosSink {

    long getCount();

    Stream<Todo> findAll();

    Stream<Todo> findByTitle(String title);

    Optional<Todo> findById(long id);

    long add(Todo todo);

    boolean update(Todo todo);

    boolean remove(long id);

}
