package de.deutscherv.gb0500.schulung.spring.boundary;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import de.deutscherv.gb0500.schulung.common.domain.TodosService;

@Controller
@RequestMapping("jdbc/todos")
public class TodosJdbcTemplateController extends TodosController {

	public TodosJdbcTemplateController(@Qualifier("jdbcTemplateTodosService") TodosService service) {
		super(service);
	}
	
}
