package de.sample.javax.todos.boundary;

import de.sample.javax.todos.domain.TodosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodosControllerTest {

    @Mock
    TodosService service;
    @Mock
    Model model;

    TodosController controller;

    @BeforeEach
    void setup() {
        controller = new TodosController(
          service,
          Mappers.getMapper(TodoUiDtoMapper.class)
        );
    }

    // 1) title == null && Service liefert Todos -> Model enthält Todos
    // 2) title != null && Service liefert Todos -> Model enthält Todos

    @Test
    void testAnzeige() {
        // Arrange
        when(service.getTodos()).thenReturn(Stream.empty());
        // Act
        String result = controller.findAll(null, model);
        // Assert
        var todosModelCaptor = ArgumentCaptor.forClass(Collection.class);
        assertAll(
          () -> verify(model).addAttribute(eq("todos"), todosModelCaptor.capture()),
          () -> assertThat(result).isEqualTo("ausgabe")
        );
        assertThat(todosModelCaptor.getValue()).isEmpty();
    }

    @Test
    void testSuche() {
        // Arrange
        String title = "suchtext";
        when(service.getTodos(title)).thenReturn(Stream.empty());
        // Act
        String result = controller.findAll(title, model);
        // Assert
        var todosModelCaptor = ArgumentCaptor.forClass(Collection.class);
        assertAll( //
          () -> verify(model).addAttribute(eq("todos"), todosModelCaptor.capture()),
          () -> assertThat(result).isEqualTo("ausgabe")
        );
        assertThat(todosModelCaptor.getValue()).isEmpty();
    }

}
