package de.deutscherv.gb0500.schulung.javaee.persistence;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.deutscherv.gb0500.schulung.common.domain.Todo;
import de.deutscherv.gb0500.schulung.common.domain.TodosDataSink;

@ApplicationScoped
public class TodosDataSinkJpaImpl implements TodosDataSink {

    @PersistenceContext(name = "puTodoDB")
	EntityManager em;

	@Override
	public Collection<Todo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
