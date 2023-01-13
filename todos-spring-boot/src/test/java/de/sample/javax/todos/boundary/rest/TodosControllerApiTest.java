package de.sample.javax.todos.boundary.rest;

import de.sample.javax.todos.domain.Todo;
import de.sample.javax.todos.domain.TodosService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class TodosControllerApiTest {

    @MockBean // erstellt einen Mock UND ersetzt das Original im Spring-Container
    TodosService service;
    @Autowired
    MockMvc mvc;

    @DisplayName("Find Single Todo")
    @Nested
    class FindSingleTodoTest {

        @Test
        void testFindSingleTodo() throws Exception {
            when(service.findById(5))
              .thenReturn(
                Optional.of(
                  Todo.builder()
                    .title("test")
                    .build()
                )
              );
            mvc.perform(get("/api/v1/todos/5").accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.title").value("test"));
        }

        @Test
        void testFindSingleTodoNotFound() throws Exception {
            when(service.findById(anyLong())).thenReturn(Optional.empty());
            mvc.perform(get("/api/v1/todos/5").accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isNotFound());
        }

        @Test
        void testFindSingleTodoInvalidId() throws Exception {
            mvc.perform(get("/api/v1/todos/-1").accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isBadRequest());
            verifyNoInteractions(service);
        }

    }

    @DisplayName("Find All Todos")
    @Nested
    class FindTodosTest {

        @Test
        void testFindAllTodos() throws Exception {
            when(service.getTodos())
              .thenReturn(Stream.empty());
            mvc.perform(get("/api/v1/todos").accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(content().string(is(equalTo("[]"))));
        }

    }

    @DisplayName("Delete Single Todo")
    @Nested
    class DeleteSingleTodoTest {

        @Test
        void testDeleteExistingTodos() throws Exception {
            when(service.remove(5)).thenReturn(true);
            mvc.perform(delete("/api/v1/todos/5"))
              .andExpect(status().isNoContent());
            verify(service).remove(5);
        }

        @Test
        void testDeleteNonExistingTodos() throws Exception {
            when(service.remove(5)).thenReturn(false);
            mvc.perform(delete("/api/v1/todos/5"))
              .andExpect(status().isNotFound());
            verify(service).remove(5);
        }

        @Test
        void testDeleteInvalidId() throws Exception {
            mvc.perform(delete("/api/v1/todos/-1"))
              .andExpect(status().isBadRequest());
            verifyNoInteractions(service);
        }

    }

    @DisplayName("Update Single Todo")
    @Nested
    class UpdateSingleTodoTest {

        @Test
        void testUpdateExistingTodos() throws Exception {
            when(service.update(any())).thenReturn(true);
            mvc.perform(
                put("/api/v1/todos/5")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{\"title\": \"test\"}")
              )
              .andExpect(status().isNoContent());
            var captor = ArgumentCaptor.forClass(Todo.class);
            verify(service).update(captor.capture());
            assertThat(captor.getValue())
              .extracting(Todo::getId, Todo::getTitle)
              .containsExactly(5L, "test");
        }

        @Test
        void testUpdateNonExistingTodos() throws Exception {
            when(service.update(any())).thenReturn(false);
            mvc.perform(
                put("/api/v1/todos/5")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{\"title\": \"test\"}")
              )
              .andExpect(status().isNotFound());
        }

        @Test
        void testUpdateInvalidId() throws Exception {
            mvc.perform(
                put("/api/v1/todos/-1")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{\"title\": \"test\"}")
              )
              .andExpect(status().isBadRequest());
            verifyNoInteractions(service);
        }

        @Test
        void testUpdateTodoWithoutBody() throws Exception {
            mvc.perform(
                put("/api/v1/todos/5")
                  .contentType(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isBadRequest());
            verifyNoInteractions(service);
        }

    }

    @DisplayName("Insert Single Todo")
    @Nested
    class InsertSingleTodoTest {

        @Test
        void testInsertTodos() throws Exception {
            when(service.add(any())).thenReturn(5L);
            mvc.perform(
                post("/api/v1/todos")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{\"title\": \"test\"}")
              )
              .andExpect(status().isCreated())
              .andExpect(header().exists(HttpHeaders.LOCATION));
            var captor = ArgumentCaptor.forClass(Todo.class);
            verify(service).add(captor.capture());
            assertThat(captor.getValue())
              .extracting(Todo::getTitle)
              .isEqualTo("test");
        }

        @Test
        void testInsertInvalidTitle() throws Exception {
            mvc.perform(
                post("/api/v1/todos")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{\"title\": \"\"}")

              )
              .andExpect(status().isBadRequest());
            verifyNoInteractions(service);
        }

        @Test
        void testInsertTodoWithId() throws Exception {
            mvc.perform(
                post("/api/v1/todos")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{\"id\":12,\"title\": \"test\"}")
              )
              .andExpect(status().isCreated());
            var captor = ArgumentCaptor.forClass(Todo.class);
            verify(service).add(captor.capture());
            assertThat(captor.getValue())
              .extracting(Todo::getId)
              .isNull();
        }

        @Test
        void testInsertTodoWithoutBody() throws Exception {
            mvc.perform(
                post("/api/v1/todos")
                  .contentType(MediaType.APPLICATION_JSON)
              )
              .andExpect(status().isBadRequest());
            verifyNoInteractions(service);
        }

        @Test
        void testInsertTodoWithInvalidMediatype() throws Exception {
            mvc.perform(post("/api/v1/todos")
                .contentType(MediaType.APPLICATION_XML)
                .content("<todo><title>test</title></todo>")
              )
              .andExpect(status().isUnsupportedMediaType());
            verifyNoInteractions(service);
        }

    }
}
