package de.sample.javax.todos.persistence.panache;

import de.sample.javax.todos.domain.Todo;
import de.sample.javax.todos.domain.TodosService;
import de.sample.javax.todos.domain.Validated;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
@Panache
@Transactional
@Validated
public class TodosServicePanacheImpl implements TodosService {

    @Inject
    TodoEntityMapper mapper;

    @Override
    public Stream<Todo> getTodos() {
        // we need to catch it all here
        // because lazy fetching occurs an error
        // because the result set is already closed then
        return TodoEntity.<TodoEntity>listAll()
          .stream()
          .map(mapper::map);
    }

    @Override
    public Stream<Todo> getTodos(String title) {
        return TodoEntity.<TodoEntity>find("#Todo.findByTitle", "%" + title + "%")
          .stream()
          .map(mapper::map)
          // we need to catch it all here
          // because lazy fetching occurs an error
          // because the result set is already closed then
          .collect(Collectors.toList())
          .stream();
    }

    public Optional<Todo> findById(long id) {
        return TodoEntity.<TodoEntity>findByIdOptional(id)
          .map(mapper::map);
    }

    @Override
    public void add(@Valid Todo todo) {
        var entity = mapper.map(todo);
        entity.id = null;
        entity.persist();
        todo.setId(entity.id);
    }

    @Override
    public boolean remove(long id) {
        var entity = TodoEntity.<TodoEntity>findById(id);
        if (entity != null) {
            entity.delete();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(@Valid @NotNull Todo todo) {
        var entity = TodoEntity.<TodoEntity>findById(todo.getId());
        if (null != entity) {
            mapper.copy(todo, entity);
            entity.persist();
            return true;
        } else {
            return false;
        }
    }

}
