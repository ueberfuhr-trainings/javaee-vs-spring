package de.sample.javax.todos.boundary.soap;

import de.sample.javax.todos.domain.Todo;
import de.sample.javax.todos.domain.TodosService;
import de.sample.javax.todos.persistence.Database;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Collection;
import java.util.stream.Collectors;

@WebService(serviceName = "TodosSoapService")
public class TodosWebservice {

	@Inject
	@Database
	private TodosService service;

	@WebMethod
	public Collection<Todo> findAll() {
		return service.getTodos()
		.collect(Collectors.toList());
	}

	@WebMethod
	public Todo findById(long id) {
		return service.findById(id).orElse(null);
	}

}
