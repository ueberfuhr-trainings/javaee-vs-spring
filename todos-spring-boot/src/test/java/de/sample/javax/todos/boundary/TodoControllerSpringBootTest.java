package de.sample.javax.todos.boundary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;

import de.sample.javax.todos.domain.Todo;
import de.sample.javax.todos.domain.TodosService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;

@SpringBootTest
class TodoControllerSpringBootTest {

	@MockBean // erstellt einen Mock UND ersetzt das Original im Spring-Container
    TodosService service;
	@Mock // erstellt NUR einen Mock
	Model model;
	@Autowired
	TodosController controller;

	// 1) title == null && Service liefert Todos -> Model enthält Todos
	// 2) title != null && Service liefert Todos -> Model enthält Todos

	@Test
	void testAnzeige() {
		// Arrange
		Collection<Todo> todos = Collections.emptyList();
		when(service.getTodos()).thenReturn(todos);
		// Act
		String result = controller.findAll(null, model);
		// Assert
		assertAll( //
				() -> verify(model).addAttribute("todos", todos), //
				() -> assertThat(result).isEqualTo("ausgabe") //
		);
	}

	@Test
	void testSuche() {
		// Arrange
		String title = "suchtext";
		Collection<Todo> todos = Collections.emptyList();
		when(service.getTodos(title)).thenReturn(todos);
		// Act
		String result = controller.findAll(title, model);
		// Assert
		assertAll( //
				() -> verify(model).addAttribute("todos", todos), //
				() -> assertThat(result).isEqualTo("ausgabe") //
		);
	}

}
