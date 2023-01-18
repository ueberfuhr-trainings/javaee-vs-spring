package de.sample.javax.spring.persistence;

import de.sample.javax.common.persistence.PersistenceConstants;
import de.sample.javax.common.persistence.TodoEntityMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.enterprise.inject.spi.CDI;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

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

    @Bean
    TodoEntityMapper todoEntityMapper() {
        return Mappers.getMapper(TodoEntityMapper.class);
    }

    @Bean
    DataSource dataSource() {
        return new JndiDataSourceLookup()
          .getDataSource(PersistenceConstants.JDBC_DATASOURCE_JNDI_NAME);
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
