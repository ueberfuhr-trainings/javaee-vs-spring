package de.sample.javax.todos.persistence;

import de.sample.javax.todos.domain.DueDate;
import de.sample.javax.todos.domain.Priority;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name="Todo")
@Table(name = "todos")
@NamedQueries({ //
		@NamedQuery(name = "findAll", query = "SELECT t FROM Todo t"), // JPQL
		@NamedQuery(name = "findByTitle", 
			query = "SELECT t FROM Todo t WHERE LOWER(t.title) LIKE LOWER (:titleparam)") // JPQL
})
@Getter
@Setter
public class TodoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(min = 3, message = "muss länger als {min} Zeichen sein")
	private String title;
	@Future
	@DueDate(period = 12, unit = ChronoUnit.WEEKS)
	@Column(name = "duedate")
	private LocalDate dueDate;
	@NotNull
	private Priority priority = Priority.MEDIUM;

}
