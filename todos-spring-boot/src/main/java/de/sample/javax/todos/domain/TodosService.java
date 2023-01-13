package de.sample.javax.todos.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Validated
@Service
public class TodosService {

    private final TodosSink sink;

    public Stream<Todo> getTodos() {
        return sink.findAll();
    }

    public Optional<Todo> findById(long id) {
        return sink.findById(id);
    }

    public Stream<Todo> getTodos(String title) {
        return sink.findByTitle(title);
    }

    public long add(@Valid Todo todo) {
        if (null != todo.getId()) {
            // TODO use Bean Validation (Groups)
            throw new ValidationException();
        }
        return sink.add(todo);
    }

    public boolean update(@Valid Todo todo) {
        if (null == todo.getId()) {
            // TODO use Bean Validation (Groups)
            throw new ValidationException();
        }
        return sink.update(todo);
    }

    public boolean remove(long id) {
        return sink.remove(id);
    }

    public long getCount() {
        return sink.getCount();
    }

}
