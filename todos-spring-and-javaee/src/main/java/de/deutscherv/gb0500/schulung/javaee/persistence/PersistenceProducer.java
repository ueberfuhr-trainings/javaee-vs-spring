package de.deutscherv.gb0500.schulung.javaee.persistence;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import de.deutscherv.gb0500.schulung.common.persistence.PersistenceConstants;
import de.deutscherv.gb0500.schulung.common.persistence.TodoEntityMapper;

@ApplicationScoped
public class PersistenceProducer {

	@Produces
    @Resource(lookup = PersistenceConstants.JDBC_DATASOURCE_JNDI_NAME)
    DataSource ds;

	@Produces
	@PersistenceContext(name = PersistenceConstants.JPA_PERSISTENCE_UNIT_NAME)
	EntityManager em;

	@Produces
	@ApplicationScoped
	public TodoEntityMapper createTodoEntityMapper() {
		return new TodoEntityMapper();
	}

}
