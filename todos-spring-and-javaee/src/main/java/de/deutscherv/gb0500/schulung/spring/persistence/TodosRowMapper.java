package de.deutscherv.gb0500.schulung.spring.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import de.deutscherv.gb0500.schulung.common.domain.Todo;

@Component
public class TodosRowMapper implements RowMapper<Todo>{

	@Override
	public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Todo todo = new Todo();
		todo.setId(rs.getLong("id"));
		todo.setTitle(rs.getString("title"));
		todo.setDescription(rs.getString("description"));
		Optional.ofNullable(rs.getTimestamp("dueDate"))
			.map(Timestamp::toLocalDateTime)
			.map(LocalDateTime::toLocalDate)
			.ifPresent(todo::setDueDate);
		todo.setDone(rs.getBoolean("done"));
		return todo;
	}

}
