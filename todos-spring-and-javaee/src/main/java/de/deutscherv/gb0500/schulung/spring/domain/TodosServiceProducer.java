package de.deutscherv.gb0500.schulung.spring.domain;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import de.deutscherv.gb0500.schulung.common.domain.TodosDataSink;
import de.deutscherv.gb0500.schulung.common.domain.TodosService;
import de.deutscherv.gb0500.schulung.common.domain.TodosServiceImpl;

@Configuration
public class TodosServiceProducer {

	// einfacher wäre es, @Service direkt an TodosService zu schreiben
	@Primary
	@Bean
	public TodosService createTodosService(TodosDataSink sink) {
		return new TodosServiceImpl(sink);
	}
	
	@Bean
	public TodosService jdbcTemplateTodosService(@Qualifier("todosJdbcTemplateSink") TodosDataSink sink) {
		return new TodosServiceImpl(sink);
	}

}
