package de.sample.javax.spring.persistence;

import de.sample.javax.common.domain.Priority;
import de.sample.javax.common.domain.Todo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class TodosRowMapper implements RowMapper<Todo> {

    @Override
    public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
        Todo todo = Todo.builder()
          .id(rs.getLong("id"))
          .title(rs.getString("title"))
          .build();
        Optional.ofNullable(rs.getTimestamp("duedate"))
          .map(Timestamp::toLocalDateTime)
          .map(LocalDateTime::toLocalDate)
          .ifPresent(todo::setDueDate);
        Optional.ofNullable(rs.getString("priority"))
          .map(Priority::valueOf)
          .ifPresent(todo::setPriority);
        return todo;
    }

}
