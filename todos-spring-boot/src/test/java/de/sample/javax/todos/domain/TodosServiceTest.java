package de.sample.javax.todos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase
class TodosServiceTest {

	@Autowired
	TodosService service;
	
	/*
	 * 1) Beim Einfügen eines Todos muss im Datenbestand ein Datensatz mehr drin sein
	 *  - getCount() -> merken
	 *  - add(...)
	 *  - getCount() -> merken
	 *  -> 2. Wert = 1. Wert + 1  
	 */
	@Test
	void testAddIncreasesCount() {
		final long countBefore = service.getCount();
		service.add(new Todo("test", null, Priority.MEDIUM));
		final long countAfter = service.getCount();
		assertThat(countAfter).isEqualTo(countBefore + 1);
	}
	
	/*  
	 * 2) Nach dem Einfügen eines Todos muss es hinterher in der Datenbank drin sein
	 *  - getTodos("title") -> merken
	 *  - add( title = 'test' )
	 *  - getTodos("title")
	 *  -> Collection danach muss +1 größer sein als davor
	 *  -> Collection muss ein Todo enthalten, das dem eingefügten Todo entspricht
	 */
	@Test
	void testAddedTodoIsFoundAfter() {
		Todo newTodo = new Todo("testAddedTodoIsFoundAfter", null, Priority.MEDIUM);
		service.add(newTodo);
		Collection<Todo> result = service.getTodos(newTodo.getTitle());
		assertThat(result).contains(newTodo); // vergleicht mit equals
	}

	/*  
	 * 3) Nach dem Löschen von Todos werden diese nicht mehr gefunden
	 *  - add( title = 'test' )
	 *  - getTodos("title") -> (Zwischenprüfung? - Ergebnis NICHT leer)
	 *  - für jedes gefundene Todo -> remove(todo)
	 *  - getTodos("title") -> Ergebnis ist leer
	 */
	@Test
	void testRemovedTodosAreNotFoundAfterDeletion() {
		Todo newTodo = new Todo("testRemovedTodosAreNotFoundAfterDeletion", null, Priority.MEDIUM);
		service.add(newTodo);

		Collection<Todo> resultBefore = service.getTodos(newTodo.getTitle());
		assertThat(resultBefore).isNotEmpty();
		resultBefore.forEach(service::remove);;

		Collection<Todo> resultAfter = service.getTodos(newTodo.getTitle());
		assertThat(resultAfter).isEmpty();
	}
}
