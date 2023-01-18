package de.sample.javax.common.persistence;

import de.sample.javax.common.domain.DueDate;
import de.sample.javax.common.domain.Priority;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity(name = "Todo")
@Table(name = "todos")
@NamedQueries({
  @NamedQuery(name = "findAll", query = "SELECT t FROM Todo t"),
  @NamedQuery(name = "findByTitle",
    query = "SELECT t FROM Todo t WHERE LOWER(t.title) LIKE LOWER (:titleparam)")
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
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MEDIUM;

}
