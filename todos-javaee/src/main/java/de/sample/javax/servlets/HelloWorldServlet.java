package de.sample.javax.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloWorldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Request auslesen (Parameter, Header)
		String name = request.getParameter("name");
		// Konvertieren und Validieren
		if (null == name || name.isEmpty()) {
			name = "World";
		}

		// Aktion
		// (keine)

		// Antwortmöglichkeiten
		// - response.getWriter()
		// - response.getOutputStream()
		// - response.sendError(405);
		// - response.sendRedirect("xyz");
		// oder: forward
		response.setContentType("text/html");
		try (PrintWriter out = response.getWriter()) {
			out.println("<h1>Hello " + name + "!</h1>");
		}
	}

}
