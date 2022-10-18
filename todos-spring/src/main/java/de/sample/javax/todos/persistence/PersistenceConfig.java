package de.sample.javax.todos.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

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

}
