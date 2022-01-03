package de.sample.javax.todos.persistence;

import java.util.Collection;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import de.sample.javax.todos.domain.Todo;
import de.sample.javax.todos.domain.TodosService;
import de.sample.javax.todos.domain.Validated;

@ApplicationScoped
@Database
@Transactional
public class TodosServiceDatabaseImpl implements TodosService {

	@PersistenceContext(name = "puTodoDB")
	EntityManager em;
	
	@Override
	public Collection<Todo> getTodos() {
		return em.createNamedQuery("findAll", Todo.class).getResultList();
	}

	@Override
	public Collection<Todo> getTodos(String title) {
		TypedQuery<Todo> query = 
				em.createNamedQuery("findByTitle", Todo.class);
		query.setParameter("titleparam", "%" +  title + "%");
		return query.getResultList();
	}
	
	public Optional<Todo> findById(long id) {
		return Optional.ofNullable(em.find(Todo.class, id));
	}

	@Validated
	@Override
	public void add(Todo todo) {
		em.persist(todo);
	}

	@Override
	public void remove(Todo todo) {
		em.remove(em.merge(todo));
	}

	@Override
	public boolean update(@Valid @NotNull Todo todo) {
		Optional<Todo> result = findById(todo.getId());
		if(result.isPresent()) {
			em.merge(todo); // TODO ???
			return true;
		} else {
			return false;
		}
	}

}
