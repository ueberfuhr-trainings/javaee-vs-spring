package de.sample.javax.spring.domain;

import de.sample.javax.common.domain.TodosService;
import de.sample.javax.common.domain.TodosServiceImpl;
import de.sample.javax.common.domain.TodosSink;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TodosServiceProducer {

    // einfacher wäre es, @Service direkt an TodosService zu schreiben
    @Primary
    @Bean
    public TodosService createTodosService(TodosSink sink) {
        return new TodosServiceImpl(sink);
    }

    @Bean
    public TodosService jdbcTemplateTodosService(@Qualifier("todosJdbcTemplateSink") TodosSink sink) {
        return new TodosServiceImpl(sink);
    }

}
