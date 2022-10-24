package de.sample.javax.javaee.domain;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import de.sample.javax.common.domain.TodosDataSink;
import de.sample.javax.common.domain.TodosService;
import de.sample.javax.common.domain.TodosServiceImpl;

@ApplicationScoped
public class TodosServiceProducer {
	
	@Inject
	private TodosDataSink sink;
	
	// einfacher wäre es, @ApplicationScoped direkt an TodosService zu schreiben
	@Produces
	@ApplicationScoped
	public TodosService createTodosService() {
		return new TodosServiceImpl(sink);
	}

}
