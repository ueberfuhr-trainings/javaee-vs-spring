package de.sample.javax.todos.domain;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApplicationScoped
public class TodosServiceMemoryImpl implements TodosService {
	
	private final Collection<Todo> todos = new HashSet<>();
	
	// Beispiel-Daten
	public TodosServiceMemoryImpl()	{
		todos.add(new Todo("Datenbank einbinden", null, Priority.LOW));
		todos.add(new Todo("JSPs lernen", LocalDate.now().plusDays(1), Priority.HIGH));
	}

	@Override
	public Collection<Todo> getTodos() {
		return todos;
	}
	
	@Override
	public Collection<Todo> getTodos(String title) {
		return todos.stream()
				.filter(t -> t.getTitle().toLowerCase().contains(title.toLowerCase()))
				.collect(Collectors.toList());
	}
	
	@Override
	@Validated
	public void add(Todo todo) {
		this.todos.add(todo);
	}
	
	@Override
	public void remove(Todo todo) {
		this.todos.remove(todo);
	}

	@Override
	public Optional<Todo> findById(@Min(1) long id) {
		return todos.stream().filter(t -> t.getId() == id).findFirst();
	}

	@Override
	public boolean update(@Valid @NotNull Todo todo) {
		Optional<Todo> result = findById(todo.getId());
		if(result.isPresent()) {
			this.todos.remove(result.get());
			this.todos.add(todo);
			return true;
		} else {
			return false;
		}
	}

}
