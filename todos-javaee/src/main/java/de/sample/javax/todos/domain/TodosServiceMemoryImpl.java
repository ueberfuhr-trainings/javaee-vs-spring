package de.sample.javax.todos.domain;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class TodosServiceMemoryImpl implements TodosService {

    private final Collection<Todo> todos = new HashSet<>();

    // Beispiel-Daten
    @PostConstruct
    public void init() {
        todos.add(
          Todo.builder()
            .title("Datenbank einbinden")
            .priority(Priority.LOW)
            .build()
        );
        todos.add(
          Todo.builder()
            .title("JSPs lernen")
            .priority(Priority.HIGH)
            .dueDate(LocalDate.now().plusDays(1))
            .build()
        );
    }

    @Override
    public Stream<Todo> getTodos() {
        return todos.stream();
    }

    @Override
    public Stream<Todo> getTodos(String title) {
        return todos.stream()
          .filter(t -> t.getTitle().toLowerCase().contains(title.toLowerCase()));
    }

    @Override
    @Validated
    public void add(Todo todo) {
        var newId = this.todos.stream()
          .mapToLong(Todo::getId)
          .max()
          .orElse(0L)
          + 1;
        todo.setId(newId);
        this.todos.add(todo);
    }

    @Override
    public boolean remove(long id) {
        return this.todos.removeIf(t -> t.getId() == id);
    }

    @Override
    public Optional<Todo> findById(@Min(1) long id) {
        return todos.stream().filter(t -> t.getId() == id).findFirst();
    }

    @Override
    public boolean update(@Valid @NotNull Todo todo) {
        Optional<Todo> result = findById(todo.getId());
        if (result.isPresent()) {
            this.todos.remove(result.get());
            this.todos.add(todo);
            return true;
        } else {
            return false;
        }
    }

}
