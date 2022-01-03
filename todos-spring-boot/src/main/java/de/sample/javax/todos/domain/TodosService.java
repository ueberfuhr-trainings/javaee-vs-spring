package de.sample.javax.todos.domain;

import java.util.Collection;
import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.sample.javax.todos.persistence.TodosRepository;

@Service
public class TodosService {

	@Autowired
	TodosRepository repo;

	public Collection<Todo> getTodos() {
		return repo.findAll();
	}

	public Optional<Todo> findById(long id) {
		return repo.findById(id);
	}

	public Collection<Todo> getTodos(String title) {
		return repo.findByTitleContaining(title);
	}

	public long add(Todo todo) {
		if(null != todo.getId()) {
			// TODO use Bean Validation (Groups)
			throw new ValidationException();
		}
		return repo.save(todo).getId();
	}
	
	public boolean update(Todo todo) {
		if(null == todo.getId()) {
			// TODO use Bean Validation (Groups)
			throw new ValidationException();
		}
		if(repo.existsById(todo.getId())) {
			repo.save(todo);
			return true;
		} else {
			return false;
		}
	}

	public boolean remove(long id) {
		if(repo.existsById(id)) {
			repo.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	public void remove(Todo todo) {
		repo.delete(todo);
	}

	public long getCount() {
		return repo.count();
	}

}
