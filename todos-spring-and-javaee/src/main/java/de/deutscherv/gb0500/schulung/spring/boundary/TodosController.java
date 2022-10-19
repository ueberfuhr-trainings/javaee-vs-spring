package de.deutscherv.gb0500.schulung.spring.boundary;

import java.time.LocalDate;
import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.deutscherv.gb0500.schulung.common.domain.Todo;
import de.deutscherv.gb0500.schulung.common.domain.TodosService;

/*
 * What could we do better:
 *  - Use MVC Forms: https://www.baeldung.com/spring-mvc-form-tutorial
 *  - Use Tiles: https://www.baeldung.com/spring-mvc-apache-tiles
 */

@Controller
@RequestMapping("/todos")
@Validated
public class TodosController {

	private final TodosService service;
	private final TodoValidator validator;

	public TodosController(TodosService service, TodoValidator validator) {
		super();
		this.service = service;
		this.validator = validator;
	}
	
	@GetMapping(value="/all")
	public String getAllTodosAsString(Model model) {
		// Todos aus dem TodosService lesen
		Collection<Todo> todos = service.getTodos();
		// Todos ausgeben
		model.addAttribute("todos", todos);
		model.addAttribute("spring", true);
		return "todos-ausgabe";
	}
	
	@GetMapping(value="/search")
	public String search(Model model, @RequestParam("searchtext") String st) {
		// Todos aus dem TodosService lesen
		Collection<Todo> todos = service.findTodos(st);
		// Todos ausgeben
		model.addAttribute("todos", todos);
		model.addAttribute("spring", true);
		return "todos-ausgabe";
	}
	
	@PostMapping("/create")
	public String create(Model model, @Valid @Size(min=3) @RequestParam("title") String title) {
		// Todo erzeugen
		Todo newTodo = new Todo();
		newTodo.setTitle(title);
		newTodo.setDueDate(LocalDate.now().plusDays(14));
		this.validator.validate(newTodo);
		
		// Aktion: Todos suchen
		Todo todo = service.createTodo(newTodo);
		// Todo ausgeben
		model.addAttribute("todo", todo);
		return "todo-details";

	}
	
}
