package de.deutscherv.gb0500.schulung.spring.persistence;

import java.util.Collection;

import org.springframework.stereotype.Component;

import de.deutscherv.gb0500.schulung.common.domain.Todo;
import de.deutscherv.gb0500.schulung.common.domain.TodosDataSink;

@Component
public class TodosDataSinkSpringImpl implements TodosDataSink {

	@Override
	public Collection<Todo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
