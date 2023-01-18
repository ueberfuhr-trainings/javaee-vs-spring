package de.sample.javax.javaee.boundary.soap;

import de.sample.javax.common.domain.Todo;
import de.sample.javax.common.domain.TodosService;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Collection;
import java.util.stream.Collectors;

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
        return service
          .getTodos()
          .collect(Collectors.toList());
    }

}
