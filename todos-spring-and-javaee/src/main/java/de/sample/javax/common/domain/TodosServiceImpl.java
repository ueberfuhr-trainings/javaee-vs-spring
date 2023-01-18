package de.sample.javax.common.domain;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class TodosServiceImpl implements TodosService {

    private final TodosSink sink;

    @Override
    public Stream<Todo> getTodos() {
        return sink.findAll();
    }

    @Override
    public Optional<Todo> findById(long id) {
        return sink.findById(id);
    }

    @Override
    public Stream<Todo> getTodos(String title) {
        Objects.requireNonNull(title);
        return sink.findByTitle(title);
    }

    @Override
    public long add(@Valid @NotNull Todo todo) {
        if (null != todo.getId()) {
            // TODO use Bean Validation (Groups)
            throw new ValidationException();
        }
        return sink.add(todo);
    }

    @Override
    public boolean update(@Valid @NotNull Todo todo) {
        if (null == todo.getId()) {
            // TODO use Bean Validation (Groups)
            throw new ValidationException();
        }
        return sink.update(todo);
    }

    @Override
    public boolean remove(long id) {
        return sink.remove(id);
    }

    @Override
    public long getCount() {
        return sink.getCount();
    }

}
