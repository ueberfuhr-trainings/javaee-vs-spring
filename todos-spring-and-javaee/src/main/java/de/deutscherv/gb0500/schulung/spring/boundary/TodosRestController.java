package de.deutscherv.gb0500.schulung.spring.boundary;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.deutscherv.gb0500.schulung.common.domain.Todo;
import de.deutscherv.gb0500.schulung.common.domain.TodosService;

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
	public ResponseEntity<Todo> findById(@PathVariable("id") long id) {
		// TODO in BL runterziehen
		return service.findTodoById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> insert(@RequestBody Todo todo) {
		Todo createdTodo = service.createTodo(todo);
		URI location = 
				linkTo(methodOn(TodosRestController.class).findById(createdTodo.getId()))
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("{id}")
	public ResponseEntity<Void> update(@PathVariable("id") long id, @RequestBody Todo todo) {
		todo.setId(id);
		if(service.updateTodo(todo)) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") long id) {
		if(service.deleteTodo(id)) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}


}
