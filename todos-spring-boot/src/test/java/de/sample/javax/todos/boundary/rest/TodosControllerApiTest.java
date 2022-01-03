package de.sample.javax.todos.boundary.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.sample.javax.todos.domain.Priority;
import de.sample.javax.todos.domain.Todo;
import de.sample.javax.todos.domain.TodosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collections;
import java.util.Optional;

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
public class TodosControllerApiTest {

	@MockBean // erstellt einen Mock UND ersetzt das Original im Spring-Container
    TodosService service;
	@Autowired
	MockMvc mvc;
	@Autowired
	ObjectMapper mapper; // parses and renders JSON

	@DisplayName("Find Single Todo")
	@Nested
	class FindSingleTodoTest {

		@Test
		void testFindSingleTodo() throws Exception {
			when(service.findById(5)) //
					.thenReturn(Optional.of(new Todo("test", null, Priority.MEDIUM)));
			mvc.perform(get("/api/v1/todos/5").accept(MediaType.APPLICATION_JSON)) //
					.andExpect(status().isOk()) //
					.andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
					.andExpect(jsonPath("$.title").value("test"));
		}

		@Test
		void testFindSingleTodoNotFound() throws Exception {
			when(service.findById(anyLong())).thenReturn(Optional.empty());
			mvc.perform(get("/api/v1/todos/5").accept(MediaType.APPLICATION_JSON)) //
					.andExpect(status().isNotFound());
		}

		@Test
		void testFindSingleTodoInvalidId() throws Exception {
			mvc.perform(get("/api/v1/todos/-1").accept(MediaType.APPLICATION_JSON)) //
					.andExpect(status().isBadRequest());
			verifyNoInteractions(service);
		}

	}

	@DisplayName("Find All Todos")
	@Nested
	class FindTodosTest {

		@Test
		void testFindAllTodos() throws Exception {
			when(service.getTodos()) //
					.thenReturn(Collections.emptyList());
			mvc.perform(get("/api/v1/todos").accept(MediaType.APPLICATION_JSON)) //
					.andExpect(status().isOk()) //
					.andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
					.andExpect(content().string(is(equalTo("[]"))));
		}

	}

	@DisplayName("Delete Single Todo")
	@Nested
	class DeleteSingleTodoTest {

		@Test
		void testDeleteExistingTodos() throws Exception {
			when(service.remove(5)).thenReturn(true);
			mvc.perform(delete("/api/v1/todos/5")) //
					.andExpect(status().isNoContent());
			verify(service).remove(5);
		}

		@Test
		void testDeleteNonExistingTodos() throws Exception {
			when(service.remove(5)).thenReturn(false);
			mvc.perform(delete("/api/v1/todos/5")) //
					.andExpect(status().isNotFound());
			verify(service).remove(5);
		}

		@Test
		void testDeleteInvalidId() throws Exception {
			mvc.perform(delete("/api/v1/todos/-1")) //
					.andExpect(status().isBadRequest());
			verifyNoInteractions(service);
		}

	}

	@DisplayName("Update Single Todo")
	@Nested
	class UpdateSingleTodoTest {

		Todo todo;

		@BeforeEach
		void setup() {
			todo = new Todo();
			todo.setTitle("test");
			todo.setId(5L);
		}

		private String body() throws JsonProcessingException {
			return mapper.writeValueAsString(todo);
		}

		private MockHttpServletRequestBuilder withBody(MockHttpServletRequestBuilder request)
				throws JsonProcessingException {
			return request.contentType(MediaType.APPLICATION_JSON).content(body());
		}

		@Test
		void testUpdateExistingTodos() throws Exception {
			when(service.update(any())).thenReturn(true);
			mvc.perform(withBody(put("/api/v1/todos/5"))) //
					.andExpect(status().isNoContent());
			verify(service).update(todo);
		}

		@Test
		void testUpdateNonExistingTodos() throws Exception {
			when(service.update(any())).thenReturn(false);
			mvc.perform(withBody(put("/api/v1/todos/5"))) //
					.andExpect(status().isNotFound());
			verify(service).update(todo);
		}

		@Test
		void testUpdateInvalidId() throws Exception {
			mvc.perform(withBody(put("/api/v1/todos/-1"))) //
					.andExpect(status().isBadRequest());
			verifyNoInteractions(service);
		}

		@Test
		void testUpdateIdNotMatchingTodoId() throws Exception {
			mvc.perform(withBody(put("/api/v1/todos/4"))) //
					.andExpect(status().isBadRequest());
			verifyNoInteractions(service);
		}

		@Test
		void testUpdateTodoWithoutId() throws Exception {
			todo.setId(null);
			mvc.perform(withBody(put("/api/v1/todos/5"))) //
					.andExpect(status().isBadRequest());
			verifyNoInteractions(service);
		}

		@Test
		void testUpdateTodoWithoutBody() throws Exception {
			todo.setId(null);
			mvc.perform(put("/api/v1/todos/5").contentType(MediaType.APPLICATION_JSON)) //
					.andExpect(status().isBadRequest());
			verifyNoInteractions(service);
		}

	}

	@DisplayName("Insert Single Todo")
	@Nested
	class InsertSingleTodoTest {

		Todo todo;

		@BeforeEach
		void setup() {
			todo = new Todo();
			todo.setTitle("test");
		}

		private String body() throws JsonProcessingException {
			return mapper.writeValueAsString(todo);
		}

		private MockHttpServletRequestBuilder withBody(MockHttpServletRequestBuilder request)
				throws JsonProcessingException {
			return request.contentType(MediaType.APPLICATION_JSON).content(body());
		}

		@Test
		void testInsertTodos() throws Exception {
			when(service.add(any())).thenReturn(5L);
			mvc.perform(withBody(post("/api/v1/todos"))) //
					.andExpect(status().isCreated()) //
					.andExpect(header().exists(HttpHeaders.LOCATION));
			verify(service).add(todo);
		}

		@Test
		void testInsertInvalidTitle() throws Exception {
			todo.setTitle("");
			mvc.perform(withBody(post("/api/v1/todos"))) //
					.andExpect(status().isBadRequest());
			verifyNoInteractions(service);
		}

		@Test
		void testInsertTodoWithId() throws Exception {
			todo.setId(5L);
			mvc.perform(withBody(post("/api/v1/todos"))) //
					.andExpect(status().isBadRequest());
			verifyNoInteractions(service);
		}

		@Test
		void testInsertTodoWithoutBody() throws Exception {
			todo.setId(null);
			mvc.perform(post("/api/v1/todos").contentType(MediaType.APPLICATION_JSON)) //
					.andExpect(status().isBadRequest());
			verifyNoInteractions(service);
		}

		@Test
		void testInsertTodoWithInvalidMediatype() throws Exception {
			mvc.perform(withBody(post("/api/v1/todos")).contentType(MediaType.APPLICATION_XML)) //
					.andExpect(status().isUnsupportedMediaType());
			verifyNoInteractions(service);
		}

	}
}
