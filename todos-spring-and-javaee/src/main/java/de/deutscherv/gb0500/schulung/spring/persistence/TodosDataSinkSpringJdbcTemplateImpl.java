package de.deutscherv.gb0500.schulung.spring.persistence;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import de.deutscherv.gb0500.schulung.common.domain.Todo;
import de.deutscherv.gb0500.schulung.common.domain.TodosDataSink;

@Component("todosJdbcTemplateSink")
public class TodosDataSinkSpringJdbcTemplateImpl implements TodosDataSink {

	private final JdbcTemplate template;
	private final TodosRowMapper rowMapper;
	private final SimpleJdbcInsert insertTemplate;


	public TodosDataSinkSpringJdbcTemplateImpl(JdbcTemplate template, TodosRowMapper rowMapper) {
		super();
		this.template = template;
		this.insertTemplate = new SimpleJdbcInsert(template)
				.withTableName("todos")
				.usingGeneratedKeyColumns("id");
		this.rowMapper = rowMapper;
	}

	@Override
	public Collection<Todo> findAll() {
		return template.query("SELECT * FROM todos", rowMapper);
	}

	@Override
	public Todo insert(Todo newTodo) {
		// create column names and values
		Map<String, Object> columns = new HashMap<>();
		Optional.ofNullable(newTodo.getTitle())
			.ifPresent(value -> columns.put("title", value));
		Optional.ofNullable(newTodo.getDescription())
			.ifPresent(value -> columns.put("description", value));
		Optional.ofNullable(newTodo.getDueDate())
			.map(LocalDate::atStartOfDay)
			.map(Timestamp::valueOf)
			.ifPresent(value -> columns.put("description", value));
		columns.put("done", newTodo.isDone());
		// execute INSERT INTO and fetch generated id
		long id = this.insertTemplate.executeAndReturnKey(columns).longValue();
		// create response
		Todo todo = new Todo();
		todo.setId(id);
		todo.setTitle(newTodo.getTitle());
		todo.setDescription(newTodo.getDescription());
		todo.setDueDate(newTodo.getDueDate());
		todo.setDone(newTodo.isDone());
		return todo;
	}

	@Override
	public void save(Todo todo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Todo> findById(long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
