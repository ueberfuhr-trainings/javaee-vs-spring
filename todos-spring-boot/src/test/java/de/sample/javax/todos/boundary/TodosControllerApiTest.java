package de.sample.javax.todos.boundary;

import de.sample.javax.todos.domain.Todo;
import de.sample.javax.todos.domain.TodosService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Testet auf HTTP-Ebene (URLs, Parameter, Header, Response Status Codes...)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class TodosControllerApiTest {

    @MockBean // erstellt einen Mock UND ersetzt das Original im Spring-Container
    TodosService service;
    @Autowired
    MockMvc mvc;

    @Test
    void testAnzeige() throws Exception {
        // Arrange
        when(service.getTodos()).thenReturn(Stream.empty());
        // Act+Assert
        mvc.perform(get("/anzeige"))
          .andExpect(status().isOk());
        verify(service).getTodos();
    }

    @Test
    void testSuche() throws Exception {
        // Arrange
        String title = "test";
        when(service.getTodos(title)).thenReturn(Stream.empty());
        // Act+Assert
        mvc.perform(get("/anzeige").param("title", title))
          .andExpect(status().isOk());
        verify(service).getTodos(title);
    }

    @Test
    void testInsert() throws Exception {
        Todo newTodo = Todo.builder().build();
        newTodo.setTitle("test-title");
        mvc.perform( //
          post("/insert")
            .param("title", newTodo.getTitle())
            .param("dueDate", "")
        ).andExpect(status().isFound());
        verify(service).add(newTodo);
    }

    @Test
    void testInsertWithDate() throws Exception {
        Todo newTodo = Todo.builder().build();
        newTodo.setTitle("test-title");
        newTodo.setDueDate(LocalDate.now().plusDays(14));
        mvc.perform(
          post("/insert")
            .param("title", newTodo.getTitle())
            .param("dueDate", newTodo.getDueDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
        ).andExpect(status().isFound());
        verify(service).add(newTodo);
    }

    @DisplayName("invalid date Format -> 200 OK, the service must not be invoked!")
    @Test
    void testInsertWithInvalidDateFormat() throws Exception {
        mvc.perform(
          post("/insert")
            .param("title", "test")
            .param("dueDate", "xyz")
        ).andExpect(status().isOk());
        verify(service, never()).add(any());
    }

    @DisplayName("missing parameters -> 200 OK, the service must not be invoked!")
    @Test
    void testInsertWithoutParameters() throws Exception {
        mvc.perform(post("/insert"))
          .andExpect(status().isOk());
        verify(service, never()).add(any());
    }

    @DisplayName("invalid title -> 200 OK, the service must not be invoked!")
    @Test
    void testInsertWithInvalidTitle() throws Exception {
        mvc.perform(
          post("/insert")
            .param("title", "te")
            .param("dueDate", "")
        ).andExpect(status().isOk());
        verify(service, never()).add(any());
    }

    @DisplayName("invalid date in past -> 200 OK, the service must not be invoked!")
    @Test
    void testInsertWithDueDateInPast() throws Exception {
        mvc.perform(
          post("/insert")
            .param("title", "test")
            .param(
              "dueDate",
              LocalDate.now()
                .minusDays(1)
                .format(DateTimeFormatter.ISO_LOCAL_DATE)
            )
        ).andExpect(status().isOk());
        verify(service, never()).add(any());
    }

}
