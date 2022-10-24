package de.deutscherv.gb0500.schulung.javaee.boundary.rest;

import java.net.URI;
import java.util.Collection;

import javax.inject.Inject;
import javax.validation.Valid;
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

import de.deutscherv.gb0500.schulung.common.domain.NotFoundException;
import de.deutscherv.gb0500.schulung.common.domain.Todo;
import de.deutscherv.gb0500.schulung.common.domain.TodosService;

@Path("/todos")
public class TodosRestController {

	@Inject
	private TodosService service;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Todo> getAllTodos() {
		return service.getTodos();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Todo findById(@PathParam("id") long id) throws NotFoundException {
		return service.findTodoById(id).orElseThrow(NotFoundException::new);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(@Valid @NotNull Todo todo, @Context UriInfo info) {
		service.createTodo(todo);
		URI location = info.getAbsolutePathBuilder() //
				.path(Long.toString(todo.getId())) //
				.build();
		return Response.created(location).build();
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") long id, @Valid @NotNull Todo todo) throws NotFoundException {
		todo.setId(id);
		service.updateTodo(todo);
		return Response.noContent().build();
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") long id) throws NotFoundException {
		service.deleteTodo(id);
		return Response.noContent().build();
	}
}
