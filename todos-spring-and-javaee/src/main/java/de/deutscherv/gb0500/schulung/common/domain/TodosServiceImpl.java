package de.deutscherv.gb0500.schulung.common.domain;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class TodosServiceImpl implements TodosService {

	private final TodosDataSink sink;

	public TodosServiceImpl(TodosDataSink sink) {
		super();
		this.sink = sink;
	}

	@Override
	public Collection<Todo> getTodos() {
		return sink.findAll();
	}

	@Override
	public Collection<Todo> findTodos(String st) {
		// TODO Suche direkt auf der Datenbank
		return getTodos().stream().filter(t -> t.getTitle().toLowerCase().contains(st.toLowerCase()))
				.collect(Collectors.toList());
	}

	@Override
	public Todo createTodo(Todo newTodo) {
		return sink.insert(newTodo);
	}

	@Override
	public Optional<Todo> findTodoById(long id) {
		return sink.findById(id);
	}

	@Override
	public void deleteTodo(long id) throws NotFoundException {
		if (sink.findById(id).isPresent()) {
			sink.delete(id);
		} else {
			throw new NotFoundException();
		}
	}

	@Override
	public void updateTodo(Todo todo) throws NotFoundException {
		if (sink.findById(todo.getId()).isPresent()) {
			sink.save(todo);
		} else {
			throw new NotFoundException();
		}
	}

}
