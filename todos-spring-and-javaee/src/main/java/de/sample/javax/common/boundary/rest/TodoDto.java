package de.sample.javax.common.boundary.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.sample.javax.common.domain.DueDate;
import de.sample.javax.common.domain.Priority;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
public class TodoDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull
    @Size(min = 3, message = "muss länger als {min} Zeichen sein")
    private String title;
    @Future
    @DueDate(period = 12, unit = ChronoUnit.WEEKS)
    @JsonProperty("due_date")
    private LocalDate dueDate;
    @NotNull
    private Priority priority = Priority.MEDIUM;

}
