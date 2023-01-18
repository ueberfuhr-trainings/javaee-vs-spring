package de.sample.javax.todos.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.enterprise.inject.spi.CDI;
import javax.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories
public class PersistenceConfig {

    @Bean
    EntityManagerFactory entityManagerFactory() {
        // use the container-managed EntityManagerFactory
        return CDI
          .current()
          .select(EntityManagerFactory.class)
          .get();
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        return new JtaTransactionManager();
    }

}
