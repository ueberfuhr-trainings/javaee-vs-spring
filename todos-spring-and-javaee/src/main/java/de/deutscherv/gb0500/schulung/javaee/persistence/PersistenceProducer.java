package de.deutscherv.gb0500.schulung.javaee.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.deutscherv.gb0500.schulung.common.persistence.TodoEntityMapper;

@ApplicationScoped
public class PersistenceProducer {

	@Produces
	@PersistenceContext(name = "puTodoDB")
	EntityManager em;

	@Produces
	@ApplicationScoped
	public TodoEntityMapper createTodoEntityMapper() {
		return new TodoEntityMapper();
	}

}
