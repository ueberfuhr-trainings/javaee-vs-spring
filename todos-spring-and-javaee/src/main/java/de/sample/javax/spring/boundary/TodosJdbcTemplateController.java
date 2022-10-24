package de.sample.javax.spring.boundary;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import de.sample.javax.common.domain.TodosService;

@Controller
@RequestMapping("jdbc/todos")
public class TodosJdbcTemplateController extends TodosController {

	public TodosJdbcTemplateController(@Qualifier("jdbcTemplateTodosService") TodosService service,
			Validator validator) {
		super(service, validator);
	}

}