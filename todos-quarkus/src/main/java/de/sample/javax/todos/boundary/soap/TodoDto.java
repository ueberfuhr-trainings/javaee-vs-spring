package de.sample.javax.todos.boundary.soap;

import de.sample.javax.todos.domain.DueDate;
import de.sample.javax.todos.domain.Priority;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@XmlRootElement(name="todo")
@XmlAccessorType(XmlAccessType.FIELD)
public class TodoDto {

    private Long id;
    @NotNull
    @Size(min = 3, message = "muss länger als {min} Zeichen sein")
    private String title;
    @Future
    @DueDate(period = 12, unit = ChronoUnit.WEEKS)
    @XmlElement(name = "due-date")
    private LocalDate dueDate;
    @NotNull
    private Priority priority = Priority.MEDIUM;

}
