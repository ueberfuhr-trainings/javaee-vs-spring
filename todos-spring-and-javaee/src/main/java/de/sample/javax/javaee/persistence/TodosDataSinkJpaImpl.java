package de.sample.javax.javaee.persistence;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import de.sample.javax.common.domain.Todo;
import de.sample.javax.common.domain.TodosDataSink;
import de.sample.javax.common.persistence.TodoEntity;
import de.sample.javax.common.persistence.TodoEntityMapper;

@ApplicationScoped
public class TodosDataSinkJpaImpl implements TodosDataSink {

	@Inject
	EntityManager em;
	@Inject
	TodoEntityMapper mapper;

	@Override
	public Collection<Todo> findAll() {
		TypedQuery<TodoEntity> query = em.createQuery("SELECT t FROM TodoEntity t", TodoEntity.class);
		return query.getResultList()
				.stream()
				.map(mapper::map)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Todo insert(Todo newTodo) {
		TodoEntity entity = mapper.map(newTodo);
		em.persist(entity);
		Todo result = mapper.map(entity);
		return result;
	}

	@Override
	public void save(Todo todo) {
		em.persist(em.merge(mapper.map(todo)));
	}

	@Override
	public void delete(long id) {
		this.findById(id).ifPresent(em::remove);
	}

	@Override
	public Optional<Todo> findById(long id) {
		return Optional.ofNullable(mapper.map(em.find(TodoEntity.class, id)));
	}

}