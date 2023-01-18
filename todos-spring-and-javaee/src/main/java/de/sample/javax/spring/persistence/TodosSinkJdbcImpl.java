package de.sample.javax.spring.persistence;

import de.sample.javax.common.domain.Priority;
import de.sample.javax.common.domain.Todo;
import de.sample.javax.common.domain.TodosSink;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Component("todosJdbcTemplateSink")
public class TodosSinkJdbcImpl implements TodosSink {

    private final JdbcTemplate template;
    private final TodosRowMapper rowMapper;
    private final SimpleJdbcInsert insertTemplate;

    public TodosSinkJdbcImpl(JdbcTemplate template, TodosRowMapper rowMapper) {
        super();
        this.template = template;
        this.insertTemplate = new SimpleJdbcInsert(template)
          .withTableName("todos")
          .usingGeneratedKeyColumns("id");
        this.rowMapper = rowMapper;
    }

    @Override
    public long getCount() {
        return
          Optional.ofNullable(
              template
                .queryForObject("SELECT count(*) FROM todos", Long.class)
            )
            .orElse(0L);
    }

    @Override
    public Stream<Todo> findAll() {
        return template
          .queryForStream("SELECT * FROM todos", rowMapper);
    }

    @Override
    public Stream<Todo> findByTitle(String title) {
        return template
          .queryForStream(
            "SELECT * FROM todos WHERE title LIKE ?",
            rowMapper,
            "%" + title + "%"
          );
    }

    @Override
    public Optional<Todo> findById(long id) {
        return Optional.ofNullable(
          template
            .queryForObject(
              "SELECT * FROM todos WHERE id = ?",
              rowMapper,
              id
            )
        );
    }

    @Override
    public long add(Todo todo) {
        // create column names and values
        Map<String, Object> columns = new HashMap<>();
        Optional.ofNullable(todo.getTitle())
          .ifPresent(value -> columns.put("title", value));
        Optional.ofNullable(todo.getDueDate())
          .map(LocalDate::atStartOfDay)
          .map(Timestamp::valueOf)
          .ifPresent(value -> columns.put("duedate", value));
        columns.put(
          "priority",
          Optional.ofNullable(todo.getPriority())
            .orElse(Priority.MEDIUM).name()
        );
        // execute INSERT INTO and fetch generated id
        return insertTemplate
          .executeAndReturnKey(columns)
          .longValue();
    }

    @Override
    public boolean update(Todo todo) {
        return 0 < template
          .update(
            "UPDATE todos SET title = ?, duedate = ?, priority = ? WHERE id = ?",
            todo.getTitle(),
            Optional.ofNullable(todo.getDueDate())
              .map(LocalDate::atStartOfDay)
              .map(Timestamp::valueOf)
              .orElse(null),
            Optional.ofNullable(todo.getPriority())
              .orElse(Priority.MEDIUM).name(),
            todo.getId()
          );
    }

    @Override
    public boolean remove(long id) {
        return 0 < template
          .update(
            "DELETE FROM todos WHERE id = ?",
            id
          );
    }

}
