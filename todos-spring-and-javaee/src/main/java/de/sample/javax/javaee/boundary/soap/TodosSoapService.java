package de.sample.javax.javaee.boundary.soap;

import java.util.Collection;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

import de.sample.javax.common.domain.Todo;
import de.sample.javax.common.domain.TodosService;

@WebService(
	serviceName = "TodosSoapService",
	portName = "TodosSoapPort",
	targetNamespace = "http://www.sample.de/services/todos"	
)
public class TodosSoapService {

	@Inject
	private TodosService service;
	
	@WebMethod(
		action = "findAll", 
		operationName = "findAllOperation"
	)
	public Collection<Todo> findAll() {
		return service.getTodos();
	}
	
}
