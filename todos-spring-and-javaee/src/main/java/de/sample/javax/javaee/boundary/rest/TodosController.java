package de.sample.javax.javaee.boundary.rest;

import de.sample.javax.common.boundary.NotFoundException;
import de.sample.javax.common.boundary.rest.TodoDto;
import de.sample.javax.common.boundary.rest.TodoDtoMapper;
import de.sample.javax.common.domain.TodosService;

import javax.inject.Inject;
import javax.validation.Valid;
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
import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

@Path("/todos")
public class TodosController {

    @Inject
    private TodosService service;
    @Inject
    private TodoDtoMapper mapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public TodoDto findTodoById(@Min(1) @PathParam("id") long id) {
        return service.findById(id)
          .map(mapper::map)
          .orElseThrow(NotFoundException::new);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<TodoDto> findAll() {
        return service.getTodos()
          .map(mapper::map)
          .collect(Collectors.toList());
    }

    @DELETE
    @Path("{id}")
    public void delete(@Min(1) @PathParam("id") long id) {
        if (!service.remove(id)) {
            throw new NotFoundException();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@Min(1) @PathParam("id") long id, @Valid @NotNull TodoDto todo) {
        todo.setId(id);
        if (!service.update(mapper.map(todo))) {
            throw new NotFoundException();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(@Valid @NotNull TodoDto todoDto, @Context UriInfo info) {
        var todo = mapper.map(todoDto);
        service.add(todo);
        URI location = info.getAbsolutePathBuilder()
          .path(Long.toString(todo.getId()))
          .build();
        var result = mapper.map(todo);
        return Response.created(location).entity(result).build();
    }

}
