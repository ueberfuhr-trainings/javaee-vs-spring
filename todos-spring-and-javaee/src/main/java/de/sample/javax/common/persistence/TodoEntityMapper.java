package de.sample.javax.common.persistence;

import de.sample.javax.common.domain.Todo;

public class TodoEntityMapper {

	public Todo map(TodoEntity source) {
		// TODO use MapStruct
		if(null == source) {
			return null;
		} else {
			Todo t = new Todo();
			t.setId(source.getId());
			t.setTitle(source.getTitle());
			t.setDueDate(source.getDueDate());
			t.setDone(source.isDone());
			return t;
		}
	}

	public TodoEntity map(Todo source) {
		// TODO use MapStruct
		if(null == source) {
			return null;
		} else {
			TodoEntity t = new TodoEntity();
			t.setId(source.getId());
			t.setTitle(source.getTitle());
			t.setDueDate(source.getDueDate());
			t.setDone(source.isDone());
			return t;
		}
	}

}
