package de.deutscherv.gb0500.schulung.spring.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import de.deutscherv.gb0500.schulung.common.persistence.PersistenceConstants;
import de.deutscherv.gb0500.schulung.common.persistence.TodoEntityMapper;

@Configuration
@EnableJpaRepositories
public class PersistenceConfig {

	@Bean
	EntityManagerFactory entityManagerFactory() {
		return Persistence.createEntityManagerFactory(PersistenceConstants.JPA_PERSISTENCE_UNIT_NAME);
	}
	
	@Bean
	PlatformTransactionManager transactionManager() {
		return new JtaTransactionManager();
	}
	
	@Bean
	TodoEntityMapper todoEntityMapper() {
		return new TodoEntityMapper();
	}
	
	@Bean
	DataSource dataSource() {
		return new JndiDataSourceLookup().getDataSource(PersistenceConstants.JDBC_DATASOURCE_JNDI_NAME);
	}

	@Bean
	JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
}
