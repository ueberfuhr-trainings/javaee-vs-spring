package de.deutscherv.gb0500.schulung.common.domain;

import java.util.Collection;
import java.util.Optional;

public interface TodosService {
	
	Collection<Todo> getTodos();
	Collection<Todo> findTodos(String st);
	Todo createTodo(Todo newTodo);
	Optional<Todo> findTodoById(long id);
	void deleteTodo(long id) throws NotFoundException;
	void updateTodo(Todo todo) throws NotFoundException;

}
