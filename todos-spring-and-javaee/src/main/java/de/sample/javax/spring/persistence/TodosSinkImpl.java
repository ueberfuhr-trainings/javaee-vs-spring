package de.sample.javax.spring.persistence;

import de.sample.javax.common.domain.Todo;
import de.sample.javax.common.domain.TodosSink;
import de.sample.javax.common.persistence.TodoEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Primary
@Component
public class TodosSinkImpl implements TodosSink {

    private final TodosRepository repo;
    private final TodoEntityMapper mapper;

    public Stream<Todo> findAll() {
        return repo.findAll()
          .stream()
          .map(mapper::map);
    }

    public Optional<Todo> findById(long id) {
        return repo.findById(id)
          .map(mapper::map);
    }

    @Override
    public Stream<Todo> findByTitle(String title) {
        return repo.findByTitleContaining(title)
          .stream()
          .map(mapper::map);
    }

    @Override
    public long add(Todo todo) {
        return repo.save(mapper.map(todo)).getId();
    }

    @Override
    public boolean update(Todo todo) {
        if (repo.existsById(todo.getId())) {
            repo.save(mapper.map(todo));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean remove(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public long getCount() {
        return repo.count();
    }

}
