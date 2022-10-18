package de.sample.javax.todos.persistence;

import java.util.List;

import de.sample.javax.todos.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TodosRepository extends JpaRepository<Todo, Long> {

	@Query("SELECT t FROM Todo t WHERE LOWER(t.title) LIKE LOWER (:titleparam)")
	List<Todo> findByTitleContaining(@Param("titleparam") String title);
	
}
