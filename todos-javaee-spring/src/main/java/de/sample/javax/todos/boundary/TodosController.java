package de.sample.javax.todos.boundary;

import java.util.Collection;

import javax.validation.Valid;

import de.sample.javax.todos.domain.Todo;
import de.sample.javax.todos.domain.TodosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodosController {

	@Autowired
    TodosService service;

	@GetMapping("/anzeige")
	public String findAll(@RequestParam(required = false) String title, Model model) {
		Collection<Todo> todos = null != title ? service.getTodos(title) : service.getTodos();
		model.addAttribute("todos", todos);
		return "ausgabe"; // -> TODO Weiterleitung auf JSP+
	}

	// INSERT
	@GetMapping("/insert.html")
	public String openInsertForm(Model model) {
		model.addAttribute("newTodo", new Todo());
		return "insert";
	}

	@PostMapping("/insert")
	public String executeInsert( //
			@Valid @ModelAttribute("newTodo") Todo newTodo, //
			BindingResult result //
	) {
		if(result.hasErrors()) {
			return "insert";
		} else {
			service.add(newTodo);
			return "redirect:/anzeige";
		}
	}

}
