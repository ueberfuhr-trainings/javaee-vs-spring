package de.sample.javax.todos.domain;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public interface TodosService {
	
	Collection<Todo> getTodos();
	
	Collection<Todo> getTodos(String title);
	
	@Validated
	void add(Todo todo);
	
	void remove(Todo todo);

	Optional<Todo> findById(@Min(1) long id);

	boolean update(@Valid @NotNull Todo todo);

}
