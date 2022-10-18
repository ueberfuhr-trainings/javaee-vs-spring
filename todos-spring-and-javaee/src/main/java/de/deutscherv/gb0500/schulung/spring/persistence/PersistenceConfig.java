package de.deutscherv.gb0500.schulung.spring.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import de.deutscherv.gb0500.schulung.common.persistence.TodoEntityMapper;

@Configuration
@EnableJpaRepositories
public class PersistenceConfig {

	@Bean
	EntityManagerFactory entityManagerFactory() {
		return Persistence.createEntityManagerFactory("puTodoDB");
	}
	
	@Bean
	PlatformTransactionManager transactionManager() {
		return new JtaTransactionManager();
	}
	
	@Bean
	TodoEntityMapper todoEntityMapper() {
		return new TodoEntityMapper();
	}

}
