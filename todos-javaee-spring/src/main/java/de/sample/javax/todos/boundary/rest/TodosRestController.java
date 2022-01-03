package de.sample.javax.todos.boundary.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.Collection;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import de.sample.javax.todos.domain.Todo;
import de.sample.javax.todos.domain.TodosService;
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

import de.sample.javax.todos.boundary.NotFoundException;

@RestController
@RequestMapping("/api/v1/todos")
@Validated
public class TodosRestController {

	// Constructor statt Field Injection mit @Autowired
	private final TodosService service;
	
	public TodosRestController(TodosService service) {
		super();
		this.service = service;
	}

	@GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Todo findTodoById(@Min(1) @PathVariable("id") long id) {
		return service.findById(id) //
				.orElseThrow(NotFoundException::new);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Todo> findAll() {
		return service.getTodos();
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
	public void update(@Min(1) @PathVariable("id") long id, @Valid @NotNull @RequestBody Todo todo) {
		if (null == todo.getId() || id != todo.getId()) {
			throw new ValidationException();
		}
		if (!service.update(todo)) {
			throw new NotFoundException();
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> insert(@Valid @NotNull @RequestBody Todo todo) {
		if (null != todo.getId()) {
			throw new ValidationException();
		}
		long id = service.add(todo);
		URI location = linkTo(methodOn(TodosRestController.class).findTodoById(id)).toUri();
		return ResponseEntity.created(location).build();
	}

}
