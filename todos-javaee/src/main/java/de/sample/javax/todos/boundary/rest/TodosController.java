package de.sample.javax.todos.boundary.rest;

import java.net.URI;
import java.util.Collection;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.sample.javax.todos.domain.Todo;
import de.sample.javax.todos.domain.TodosService;
import de.sample.javax.todos.persistence.Database;

@Path("/todos")
public class TodosController {

	@Inject
	@Database
	private TodosService service;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Todo findTodoById(@Min(1) @PathParam("id") long id) {
		return service.findById(id) //
				.orElseThrow(NotFoundException::new);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Todo> findAll() {
		return service.getTodos();
	}

	@DELETE
	@Path("{id}")
	public Response delete(@Min(1) @PathParam("id") long id) {
		service.remove(this.findTodoById(id));
		return Response.noContent().build();
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@Min(1) @PathParam("id") long id, @Valid @NotNull Todo todo) {
		if (null == todo.getId() || id != todo.getId()) {
			throw new ValidationException();
		}
		if (!service.update(todo)) {
			throw new NotFoundException();
		}
		return Response.noContent().build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(@Valid @NotNull Todo todo, @Context UriInfo info) {
		if (null != todo.getId()) {
			throw new ValidationException();
		}
		service.add(todo); // TODO: wird ID automatisch gesetzt?
		URI location = info.getAbsolutePathBuilder() //
				.path(Long.toString(todo.getId())) //
				.build();
		return Response.created(location).build();
	}

}
