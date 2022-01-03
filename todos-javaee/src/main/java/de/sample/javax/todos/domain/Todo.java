package de.sample.javax.todos.domain;

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

@Entity
@Table(name = "todo")
@NamedQueries({ //
		@NamedQuery(name = "findAll", query = "SELECT t FROM Todo t"), // JPQL
		@NamedQuery(name = "findByTitle", 
			query = "SELECT t FROM Todo t WHERE LOWER(t.title) LIKE LOWER (:titleparam)") // JPQL
})
public class Todo { // TODO TodoEntity als separate Klasse haben, Mapping mit MapStruct

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(min = 3, message = "muss länger als {min} Zeichen sein")
	private String title;
	@Future
	@DueDate(period = 12, unit = ChronoUnit.WEEKS)
	@Column(name = "duedate")
	//@XmlElement(name = "duedate")
	private LocalDate dueDate;
	@NotNull
	private Priority priority = Priority.MEDIUM;

	public Todo() {
		super();
	}

	public Todo(String title, LocalDate dueDate, Priority priority) {
		super();
		this.title = title;
		this.dueDate = dueDate;
		this.priority = priority;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
