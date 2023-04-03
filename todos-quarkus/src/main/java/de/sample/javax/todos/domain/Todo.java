package de.sample.javax.todos.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@Builder
public class Todo {

    private Long id;
    @NotNull
    @Size(min = 3, message = "muss länger als {min} Zeichen sein")
    private String title;
    @Future
    @DueDate(period = 12, unit = ChronoUnit.WEEKS)
    private LocalDate dueDate;
    @NotNull
    @Builder.Default
    private Priority priority = Priority.MEDIUM;

}
