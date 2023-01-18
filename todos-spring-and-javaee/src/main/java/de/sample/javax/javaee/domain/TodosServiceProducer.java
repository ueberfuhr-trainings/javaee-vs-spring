package de.sample.javax.javaee.domain;

import de.sample.javax.common.domain.TodosService;
import de.sample.javax.common.domain.TodosServiceImpl;
import de.sample.javax.common.domain.TodosSink;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class TodosServiceProducer {

    @Inject
    private TodosSink sink;

    // einfacher wäre es, @ApplicationScoped direkt an TodosService zu schreiben
    @Produces
    @ApplicationScoped
    public TodosService createTodosService() {
        return new TodosServiceImpl(sink);
    }

}
