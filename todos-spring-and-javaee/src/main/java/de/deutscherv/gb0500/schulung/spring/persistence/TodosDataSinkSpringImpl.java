package de.deutscherv.gb0500.schulung.spring.persistence;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import de.deutscherv.gb0500.schulung.common.domain.Todo;
import de.deutscherv.gb0500.schulung.common.domain.TodosDataSink;
import de.deutscherv.gb0500.schulung.common.persistence.TodoEntityMapper;

@Component
public class TodosDataSinkSpringImpl implements TodosDataSink {

	private final TodosRepository repo;
	private final TodoEntityMapper mapper;

	public TodosDataSinkSpringImpl(TodosRepository repo, TodoEntityMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	@Override
	public Collection<Todo> findAll() {
		return repo.findAll().stream().map(mapper::map).collect(Collectors.toList());
	}

}
