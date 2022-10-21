package de.deutscherv.gb0500.schulung.common.domain;

import java.util.Collection;
import java.util.Optional;

public interface TodosDataSink {

	Collection<Todo> findAll();

	Todo insert(Todo newTodo);
	void save(Todo todo);
	void delete(long id);
	Optional<Todo> findById(long id);

}
