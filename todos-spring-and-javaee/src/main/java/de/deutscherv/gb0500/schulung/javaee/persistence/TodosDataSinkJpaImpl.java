package de.deutscherv.gb0500.schulung.javaee.persistence;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.deutscherv.gb0500.schulung.common.domain.Todo;
import de.deutscherv.gb0500.schulung.common.domain.TodosDataSink;
import de.deutscherv.gb0500.schulung.common.persistence.TodoEntity;
import de.deutscherv.gb0500.schulung.common.persistence.TodoEntityMapper;

@ApplicationScoped
public class TodosDataSinkJpaImpl implements TodosDataSink {

	@Inject
	EntityManager em;
	@Inject
	TodoEntityMapper mapper;

	@Override
	public Collection<Todo> findAll() {
		TypedQuery<TodoEntity> query = em.createQuery("SELECT t FROM TodoEntity t", TodoEntity.class);
		return query.getResultStream().map(mapper::map).collect(Collectors.toList());
	}

}
