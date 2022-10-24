package de.sample.javax.javaee.boundary;

import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.sample.javax.common.domain.Todo;
import de.sample.javax.common.domain.TodosService;

@WebServlet("/todos")
public class ReadAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	private TodosService service;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Todos aus dem TodosService lesen
		Collection<Todo> todos = service.getTodos();
		// Todos ausgeben
//		response.setContentType("text/plain");
//		try(PrintWriter out = response.getWriter()) {
//			out.println("Todos:");
//			out.println("======");
//			out.println();
//			todos.forEach(out::println);
//		}
		request.setAttribute("todos", todos);
		getServletContext()
			.getRequestDispatcher("/WEB-INF/jsp/todos-ausgabe.jsp")
			.forward(request, response);
		
	}

}
