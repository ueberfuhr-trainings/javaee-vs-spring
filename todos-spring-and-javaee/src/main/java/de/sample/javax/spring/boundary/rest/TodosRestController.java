package de.sample.javax.spring.boundary.rest;

import de.sample.javax.common.boundary.NotFoundException;
import de.sample.javax.common.boundary.rest.TodoDto;
import de.sample.javax.common.boundary.rest.TodoDtoMapper;
import de.sample.javax.common.domain.TodosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/todos")
@Validated
public class TodosRestController {

    private final TodosService service;
    private final TodoDtoMapper mapper;

    @Operation(summary = "Find a single todo", responses = {
      @ApiResponse(responseCode = "200", description = "Todo with the given id was found."),
      @ApiResponse(responseCode = "404", description = "Todo with the given id was not found")
    })
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TodoDto findTodoById(@Min(1) @PathVariable("id") long id) {
        return service.findById(id)
          .map(mapper::map)
          .orElseThrow(NotFoundException::new);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<TodoDto> findAll() {
        return service.getTodos()
          .map(mapper::map)
          .collect(Collectors.toList());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Min(1) @PathVariable("id") long id) {
        if (!service.remove(id)) {
            throw new NotFoundException();
        }
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Min(1) @PathVariable("id") long id, @Valid @NotNull @RequestBody TodoDto todo) {
        todo.setId(id);
        if (!service.update(mapper.map(todo))) {
            throw new NotFoundException();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoDto> insert(@Valid @NotNull @RequestBody TodoDto todo) {
        long id = service.add(mapper.map(todo));
        todo.setId(id);
        URI location = linkTo(methodOn(TodosRestController.class).findTodoById(id)).toUri();
        return ResponseEntity.created(location).body(todo);
    }

}
