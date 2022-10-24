package de.sample.javax.spring.boundary;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.sample.javax.common.domain.NotFoundException;
import de.sample.javax.common.domain.Todo;
import de.sample.javax.common.domain.TodosService;

@RestController
@RequestMapping("/api/todos")
public class TodosRestController {

	private final TodosService service;

	public TodosRestController(TodosService service) {
		super();
		this.service = service;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Todo> getAllTodos() {
		return service.getTodos();
	}
	
	@GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Todo findById(@PathVariable("id") long id) throws NotFoundException {
		// TODO in BL runterziehen
		return service.findTodoById(id)
				.orElseThrow(NotFoundException::new);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@Valid @RequestBody Todo todo) throws NotFoundException {
		Todo createdTodo = service.createTodo(todo);
		URI location = 
				linkTo(methodOn(TodosRestController.class).findById(createdTodo.getId()))
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable("id") long id, @Valid  @RequestBody Todo todo) throws NotFoundException {
		todo.setId(id);
		service.updateTodo(todo);
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") long id) throws NotFoundException {
		service.deleteTodo(id);
	}


}
