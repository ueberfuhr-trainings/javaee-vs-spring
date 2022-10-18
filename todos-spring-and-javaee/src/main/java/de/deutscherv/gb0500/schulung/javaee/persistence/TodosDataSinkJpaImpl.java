package de.deutscherv.gb0500.schulung.javaee.persistence;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.deutscherv.gb0500.schulung.common.domain.Todo;
import de.deutscherv.gb0500.schulung.common.domain.TodosDataSink;
import de.deutscherv.gb0500.schulung.common.persistence.TodoEntity;
import de.deutscherv.gb0500.schulung.common.persistence.TodoEntityMapper;

@ApplicationScoped
public class TodosDataSinkJpaImpl implements TodosDataSink {

    @PersistenceContext(name = "puTodoDB")
	EntityManager em;

	@Override
	public Collection<Todo> findAll() {
		// TODO Dep. Inj.
		TodoEntityMapper mapper = new TodoEntityMapper();
		TypedQuery<TodoEntity> query = em.createQuery("SELECT t FROM TodoEntity t", TodoEntity.class);
		return query.getResultStream()
			.map(mapper::map)
			.collect(Collectors.toList());
	}

}
