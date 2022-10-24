package de.sample.javax.javaee.boundary;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import de.sample.javax.common.domain.Todo;
import de.sample.javax.common.domain.TodosService;

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
		if(null == title) {
			// Technischer Fehler: 400 Bad Request
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter searchtext must not be null");
			return;
		}
		
		// Todo erzeugen
		Todo newTodo = new Todo();
		newTodo.setTitle(title);
		newTodo.setDueDate(LocalDate.now().plusDays(14));
		
		// Bean Validation
		Set<ConstraintViolation<Todo>> violations = validator.validate(newTodo);
		if(!violations.isEmpty()) {
			request.setAttribute("violations", violations);
			getServletContext()
				.getRequestDispatcher("/WEB-INF/jsp/validation-errors.jsp")
				.forward(request, response);
		} else {
			// Aktion: Todos suchen
			Todo todo = service.createTodo(newTodo);
			// Antwort generieren
			request.setAttribute("todo", todo);
			getServletContext()
				.getRequestDispatcher("/WEB-INF/jsp/todo-details.jsp")
				.forward(request, response);

		}

	}

}
