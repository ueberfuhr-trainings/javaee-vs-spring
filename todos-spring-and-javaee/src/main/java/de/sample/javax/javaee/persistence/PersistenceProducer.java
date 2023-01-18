package de.sample.javax.javaee.persistence;

import de.sample.javax.common.persistence.PersistenceConstants;
import de.sample.javax.common.persistence.TodoEntityMapper;
import org.mapstruct.factory.Mappers;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

@ApplicationScoped
public class PersistenceProducer {

    @Produces
    @Resource(lookup = PersistenceConstants.JDBC_DATASOURCE_JNDI_NAME)
    DataSource ds;

    @Produces
    @ApplicationScoped
    public TodoEntityMapper createTodoEntityMapper() {
        return Mappers.getMapper(TodoEntityMapper.class);
    }

}
