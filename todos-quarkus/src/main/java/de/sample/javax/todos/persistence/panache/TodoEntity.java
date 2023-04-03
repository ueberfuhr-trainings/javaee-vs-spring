package de.sample.javax.todos.persistence.panache;

import de.sample.javax.todos.domain.DueDate;
import de.sample.javax.todos.domain.Priority;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity(name = "TodoPanacheEntity")
@Table(name = "todos")
@NamedQueries({
  @NamedQuery(name = "Todo.findByTitle",
    query = "SELECT t FROM TodoPanacheEntity t WHERE LOWER(t.title) LIKE LOWER (:titleparam)")
})
@Getter
@Setter
public class TodoEntity extends PanacheEntity {

    @NotNull
    @Size(min = 3, message = "muss länger als {min} Zeichen sein")
    private String title;
    @Future
    @DueDate(period = 12, unit = ChronoUnit.WEEKS)
    @Column(name = "duedate")
    private LocalDate dueDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MEDIUM;

}
