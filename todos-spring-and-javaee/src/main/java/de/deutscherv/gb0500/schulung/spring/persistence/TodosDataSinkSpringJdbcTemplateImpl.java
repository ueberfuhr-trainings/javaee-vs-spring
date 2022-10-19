package de.deutscherv.gb0500.schulung.spring.persistence;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import de.deutscherv.gb0500.schulung.common.domain.Todo;
import de.deutscherv.gb0500.schulung.common.domain.TodosDataSink;

@Component("todosJdbcTemplateSink")
public class TodosDataSinkSpringJdbcTemplateImpl implements TodosDataSink {

	private final JdbcTemplate template;
	private final TodosRowMapper rowMapper;


	public TodosDataSinkSpringJdbcTemplateImpl(JdbcTemplate template, TodosRowMapper rowMapper) {
		super();
		this.template = template;
		this.rowMapper = rowMapper;
	}

	@Override
	public Collection<Todo> findAll() {
		return template.query("SELECT * FROM todos", rowMapper);
	}

	@SuppressWarnings("serial")
	@Override
	public Todo insert(Todo newTodo) {
		throw new DataAccessException("not yet implemented") {};
	}

}
