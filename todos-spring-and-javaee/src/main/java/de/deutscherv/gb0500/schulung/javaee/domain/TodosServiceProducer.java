package de.deutscherv.gb0500.schulung.javaee.domain;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import de.deutscherv.gb0500.schulung.common.domain.TodosDataSink;
import de.deutscherv.gb0500.schulung.common.domain.TodosService;
import de.deutscherv.gb0500.schulung.common.domain.TodosServiceImpl;

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
