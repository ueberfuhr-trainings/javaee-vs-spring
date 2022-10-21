package de.deutscherv.gb0500.schulung.spring.persistence;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import de.deutscherv.gb0500.schulung.common.domain.Todo;
import de.deutscherv.gb0500.schulung.common.domain.TodosDataSink;
import de.deutscherv.gb0500.schulung.common.persistence.TodoEntity;
import de.deutscherv.gb0500.schulung.common.persistence.TodoEntityMapper;

@Primary
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

	@Override
	public Todo insert(Todo newTodo) {
		TodoEntity entity = mapper.map(newTodo);
		TodoEntity savedEntity = repo.save(entity);
		Todo result = mapper.map(savedEntity);
		return result;
	}

	@Override
	public void save(Todo todo) {
		this.repo.save(mapper.map(todo));
	}

	@Override
	public void delete(long id) {
		this.repo.deleteById(id);
	}

	@Override
	public Optional<Todo> findById(long id) {
		return this.repo.findById(id).map(mapper::map);
	}

}
