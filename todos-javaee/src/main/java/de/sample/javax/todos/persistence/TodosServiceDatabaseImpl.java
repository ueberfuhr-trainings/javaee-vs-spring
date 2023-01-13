package de.sample.javax.todos.persistence;

import de.sample.javax.todos.domain.Todo;
import de.sample.javax.todos.domain.TodosService;
import de.sample.javax.todos.domain.Validated;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
@Database
@Transactional
@Validated
public class TodosServiceDatabaseImpl implements TodosService {

    @Inject
    EntityManager em;
    @Inject
    TodoEntityMapper mapper;

    @Override
    public Stream<Todo> getTodos() {
        return em.createNamedQuery("findAll", TodoEntity.class)
          .getResultStream()
          .map(mapper::map);
    }

    @Override
    public Stream<Todo> getTodos(String title) {
        var query = em.createNamedQuery("findByTitle", TodoEntity.class);
        query.setParameter("titleparam", "%" + title + "%");
        return query.getResultStream()
          .map(mapper::map);
    }

    public Optional<Todo> findById(long id) {
        return Optional.ofNullable(em.find(TodoEntity.class, id))
          .map(mapper::map);
    }

    @Override
    public void add(@Valid Todo todo) {
        var entity = mapper.map(todo);
        em.persist(entity);
        todo.setId(entity.getId());
    }

    @Override
    public boolean remove(long id) {
        var entity = em.find(TodoEntity.class, id);
        if (entity != null) {
            em.remove(entity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(@Valid @NotNull Todo todo) {
        var entity = em.find(TodoEntity.class, todo.getId());
        if (null != entity) {
            mapper.copy(todo, entity);
            em.persist(entity);
            return true;
        } else {
            return false;
        }
    }

}
