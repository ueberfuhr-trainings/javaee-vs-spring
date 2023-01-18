package de.sample.javax.javaee.boundary;

import de.sample.javax.common.domain.Todo;
import de.sample.javax.common.domain.TodosService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private TodosService service;
    @Inject
    private Validator validator;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
        // Benutzereingabe auslesen -> konvertieren, validieren
        String title = request.getParameter("title");
        if (null == title) {
            // Technischer Fehler: 400 Bad Request
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter searchtext must not be null");
            return;
        }

        // Todo erzeugen
        Todo newTodo = Todo.builder()
          .title(title)
          .dueDate(LocalDate.now().plusDays(14))
          .build();

        // Bean Validation
        Set<ConstraintViolation<Todo>> violations = validator.validate(newTodo);
        if (!violations.isEmpty()) {
            request.setAttribute("violations", violations);
            getServletContext()
              .getRequestDispatcher("/WEB-INF/jsp/validation-errors.jsp")
              .forward(request, response);
        } else {
            // Aktion: Todo erzeugen
            var id = service.add(newTodo);
            newTodo.setId(id);
            // Antwort generieren
            request.setAttribute("todo", newTodo);
            getServletContext()
              .getRequestDispatcher("/WEB-INF/jsp/todo-details.jsp")
              .forward(request, response);

        }

    }

}
