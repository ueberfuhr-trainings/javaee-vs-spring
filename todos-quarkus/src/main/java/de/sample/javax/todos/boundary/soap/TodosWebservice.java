package de.sample.javax.todos.boundary.soap;

import de.sample.javax.todos.domain.TodosService;
import de.sample.javax.todos.persistence.panache.Panache;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;
import java.util.stream.Collectors;

@WebService(
  serviceName = "TodosService",
  targetNamespace = "http://www.samples.de/q/todos"
)
public class TodosWebservice {

    @Inject
    @Panache
    TodosService service;
    @Inject
    TodoDtoMapper mapper;

    @WebMethod
    public Collection<TodoDto> findAll() {
        return service.getTodos()
          .map(mapper::map)
          .collect(Collectors.toList());
    }

    @WebMethod
    public TodoDto findById(@WebParam(name = "id") Long id) {
        return service.findById(id)
          .map(mapper::map)
          .orElse(null);
    }

}
