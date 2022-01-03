package de.sample.javax.todos.boundary.soap;

import java.util.Collection;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

import de.sample.javax.todos.domain.Todo;
import de.sample.javax.todos.domain.TodosService;
import de.sample.javax.todos.persistence.Database;

@WebService(serviceName = "TodosSoapService")
public class TodosWebservice {

	@Inject
	@Database
	private TodosService service;

	@WebMethod
	public Collection<Todo> findAll() {
		return service.getTodos();
	}

	@WebMethod
	public Todo findById(long id) {
		return service.findById(id).orElse(null);
	}

}
