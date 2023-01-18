package de.sample.javax.javaee.persistence;

import de.sample.javax.common.domain.Todo;
import de.sample.javax.common.domain.TodosSink;
import de.sample.javax.common.persistence.TodoEntity;
import de.sample.javax.common.persistence.TodoEntityMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
@Transactional
public class TodosSinkJpaImpl implements TodosSink {

    @Inject
    EntityManager em;
    @Inject
    TodoEntityMapper mapper;

    @Override
    public long getCount() {
        var qb = em.getCriteriaBuilder();
        var cq = qb.createQuery(Long.class);
        return em
          .createQuery(cq.select(qb.count(cq.from(TodoEntity.class))))
          .getSingleResult();
    }

    @Override
    public Stream<Todo> findAll() {
        return em
          .createQuery("SELECT t FROM Todo t", TodoEntity.class)
          .getResultStream()
          .map(mapper::map)
          // we need to catch it all here
          // because lazy fetching occurs an error
          // because the result set is already closed then
          .collect(Collectors.toList())
          .stream();
    }

    @Override
    public Optional<Todo> findById(long id) {
        return Optional
          .ofNullable(em.find(TodoEntity.class, id))
          .map(mapper::map);
    }

    @Override
    public Stream<Todo> findByTitle(String title) {
        var query = em.createQuery(
          "SELECT t FROM Todo t WHERE t.title LIKE :title",
          TodoEntity.class
        );
        query.setParameter("title", "%" + title + "%");
        return query
          .getResultStream()
          .map(mapper::map)
          // we need to catch it all here
          // because lazy fetching occurs an error
          // because the result set is already closed then
          .collect(Collectors.toList())
          .stream();
    }

    @Override
    public long add(Todo todo) {
        TodoEntity entity = mapper.map(todo);
        em.persist(entity);
        return entity.getId();
    }

    @Override
    public boolean update(Todo todo) {
        var existing = em.find(TodoEntity.class, todo.getId());
        if (null != existing) {
            mapper.copy(todo, existing);
            em.persist(existing);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean remove(long id) {
        var existing = em.find(TodoEntity.class, id);
        if (null != existing) {
            em.remove(existing);
            return true;
        } else {
            return false;
        }
    }

}
