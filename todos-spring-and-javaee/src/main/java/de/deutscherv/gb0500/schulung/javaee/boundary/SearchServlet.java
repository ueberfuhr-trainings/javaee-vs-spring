package de.deutscherv.gb0500.schulung.javaee.boundary;

import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.deutscherv.gb0500.schulung.common.domain.Todo;
import de.deutscherv.gb0500.schulung.common.domain.TodosService;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	private TodosService service;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Benutzereingabe auslesen -> konvertieren, validieren
		String st = request.getParameter("searchtext");
		if(null == st) {
			// Technischer Fehler: 400 Bad Request
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter searchtext must not be null");
			return;
		}
		
		// Aktion: Todos suchen
		Collection<Todo> todos = service.findTodos(st);
		
		// Antwort generieren
		request.setAttribute("todos", todos);
		getServletContext()
			.getRequestDispatcher("/WEB-INF/jsp/todos-ausgabe.jsp")
			.forward(request, response);

	}

}
