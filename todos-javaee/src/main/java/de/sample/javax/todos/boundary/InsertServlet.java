package de.sample.javax.todos.boundary;

import de.sample.javax.todos.domain.Priority;
import de.sample.javax.todos.domain.Todo;
import de.sample.javax.todos.domain.TodosService;
import de.sample.javax.todos.persistence.Database;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@WebServlet("/insert")
public class InsertServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    @Database
    TodosService service;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
        // Request-Parameter auslesen
        String titleParam = request.getParameter("title");
        String dueDateParam = request.getParameter("dueDate");

        // ... konvertieren
        String title = titleParam != null ? titleParam.trim() : null;
        LocalDate dueDate;
        try {
            dueDate = null != dueDateParam && !dueDateParam.isEmpty()
              ? LocalDate.parse(dueDateParam, DateTimeFormatter.ISO_LOCAL_DATE)
              : null;
        } catch (DateTimeParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        // ... validieren

        // Action
        Todo todo = Todo.builder()
          .title(title)
          .dueDate(dueDate)
          .priority(Priority.MEDIUM)
          .build();
        try {
            service.add(todo);
        } catch (ValidationException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }

        // Antwortgenerierung
        response.sendRedirect("anzeige");
    }

}
