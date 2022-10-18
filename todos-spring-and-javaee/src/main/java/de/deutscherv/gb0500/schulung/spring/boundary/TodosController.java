package de.deutscherv.gb0500.schulung.spring.boundary;

import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.deutscherv.gb0500.schulung.common.domain.Todo;
import de.deutscherv.gb0500.schulung.common.domain.TodosService;

@Controller
@RequestMapping("/todos")
public class TodosController {

	private final TodosService service;

	public TodosController(TodosService service) {
		super();
		this.service = service;
	}
	
	@GetMapping(value="/all", produces = MediaType.TEXT_PLAIN_VALUE)
//	@ResponseBody
	public String getAllTodosAsString(Model model) {
		// Todos aus dem TodosService lesen
		Collection<Todo> todos = service.getTodos();
		// Todos ausgeben
//		StringBuilder sb = new StringBuilder();
//		sb.append("Todos mit Spring:\n");
//		sb.append("=================\n");
//		sb.append("\n");
//		todos.forEach(t -> sb.append(t).append("\n"));
//		return sb.toString();
		model.addAttribute("todos", todos);
		model.addAttribute("spring", true);
		return "todos-ausgabe";
	}
	
	@GetMapping(value="/search", produces = MediaType.TEXT_PLAIN_VALUE)
//	@ResponseBody
	public String search(Model model, @RequestParam("searchtext") String st) {
		// Todos aus dem TodosService lesen
		Collection<Todo> todos = service.findTodos(st);
		// Todos ausgeben
		model.addAttribute("todos", todos);
		model.addAttribute("spring", true);
		return "todos-ausgabe";
	}
	
	
}
