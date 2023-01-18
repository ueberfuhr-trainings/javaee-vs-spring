package de.sample.javax.spring.boundary;

import de.sample.javax.common.domain.Todo;
import de.sample.javax.common.domain.TodosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * What could we do better:
 *  - Use MVC Forms: https://www.baeldung.com/spring-mvc-form-tutorial
 *  - Use Tiles: https://www.baeldung.com/spring-mvc-apache-tiles
 */

@Controller
@RequestMapping("/todos")
@Validated
@RequiredArgsConstructor
public class TodosController {

    private final TodosService service;
    private final Validator validator;

    @GetMapping(value = "/all")
    public String getAllTodos(Model model) {
        // Todos aus dem TodosService lesen
        Collection<Todo> todos = service
          .getTodos()
          .collect(Collectors.toList());
        // Todos ausgeben
        model.addAttribute("todos", todos);
        model.addAttribute("spring", true);
        return "todos-ausgabe";
    }

    @GetMapping(value = "/search")
    public String search(Model model, @RequestParam("searchtext") String st) {
        // Todos aus dem TodosService lesen
        Collection<Todo> todos = service
          .getTodos(st)
          .collect(Collectors.toList());
        // Todos ausgeben
        model.addAttribute("todos", todos);
        model.addAttribute("spring", true);
        return "todos-ausgabe";
    }

    @PostMapping("/create")
    public String create(Model model, @RequestParam("title") String title) {
        // Todo erzeugen
        Todo newTodo = Todo.builder()
          .title(title)
          .dueDate(LocalDate.now().plusDays(14))
          .build();
        // Validate
        Set<ConstraintViolation<Todo>> violations = validator.validate(newTodo);
        // TODO react...
        if (!violations.isEmpty()) {
            model.addAttribute("violations", violations);
            return "validation-errors";
        } else {
            // Aktion: Todo anlegen
            var id = service.add(newTodo);
            newTodo.setId(id);
            // Todo ausgeben
            model.addAttribute("todo", newTodo);
            return "todo-details";
        }

    }

}
