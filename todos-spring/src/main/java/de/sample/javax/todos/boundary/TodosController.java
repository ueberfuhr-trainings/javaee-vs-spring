package de.sample.javax.todos.boundary;

import de.sample.javax.todos.domain.TodosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class TodosController {

    private final TodosService service;
    private final TodoUiDtoMapper mapper;

    @GetMapping("/anzeige")
    public String findAll(@RequestParam(name = "title", required = false) String title, Model model) {
        var todos = (null != title ? service.getTodos(title) : service.getTodos())
          .map(mapper::map)
          .collect(Collectors.toList());
        model.addAttribute("todos", todos);
        return "ausgabe";
    }

    // INSERT
    @GetMapping("/insert.html")
    public String openInsertForm(Model model) {
        model.addAttribute("newTodo", new TodoUiDto());
        return "insert";
    }

    @PostMapping("/insert")
    public String executeInsert( //
      @Valid @ModelAttribute("newTodo") TodoUiDto newTodo, //
      BindingResult result //
    ) {
        if (result.hasErrors()) {
            return "insert";
        } else {
            service.add(mapper.map(newTodo));
            return "redirect:/anzeige";
        }
    }

}
