package de.deutscherv.gb0500.schulung.common.domain;

import java.util.Collection;

public interface TodosService {
	
	Collection<Todo> getTodos();
	Collection<Todo> findTodos(String st);

}
