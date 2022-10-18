package de.deutscherv.gb0500.schulung.common.domain;

import java.util.Collection;
import java.util.stream.Collectors;

public class TodosServiceImpl implements TodosService {
	
	private final TodosDataSink sink;

//	{ // Quick n dirty
//		Todo todo = new Todo();
//		todo.setTitle("Staubsaugen");
//		todo.setDueDate(LocalDate.now().plusDays(10));
//		getTodos().add(todo);
//		Todo todo2 = new Todo();
//		todo2.setTitle("Aufräumen");
//		todo2.setDueDate(LocalDate.now().plusDays(5));
//		getTodos().add(todo2);
//		Todo todo3 = new Todo();
//		todo3.setTitle("Spring lernen");
//		todo3.setDueDate(LocalDate.now().plusDays(2));
//		getTodos().add(todo3);
//	}

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
		return getTodos().stream()
				.filter(t -> t.getTitle().toLowerCase().contains(st.toLowerCase()))
				.collect(Collectors.toList());
	}

}
