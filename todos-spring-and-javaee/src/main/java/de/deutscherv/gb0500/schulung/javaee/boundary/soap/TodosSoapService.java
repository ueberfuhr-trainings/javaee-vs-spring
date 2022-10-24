package de.deutscherv.gb0500.schulung.javaee.boundary.soap;

import java.util.Collection;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

import de.deutscherv.gb0500.schulung.common.domain.Todo;
import de.deutscherv.gb0500.schulung.common.domain.TodosService;

@WebService(
	serviceName = "TodosSoapService",
	portName = "TodosSoapPort",
	targetNamespace = "http://www.deutsche-rv.de/services/todos"	
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
