package de.deutscherv.gb0500.schulung.common.persistence;

import de.deutscherv.gb0500.schulung.common.domain.Todo;

public class TodoEntityMapper {

	public Todo map(TodoEntity entity) {
		// TODO use MapStruct
		if(null == entity) {
			return null;
		} else {
			Todo t = new Todo();
			t.setId(entity.getId());
			t.setTitle(entity.getTitle());
			t.setDueDate(entity.getDueDate());
			t.setDone(entity.isDone());
			return t;
		}
	}
	
}
