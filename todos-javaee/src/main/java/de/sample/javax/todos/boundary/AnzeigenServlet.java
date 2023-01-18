package de.sample.javax.todos.boundary;

import de.sample.javax.todos.domain.Todo;
import de.sample.javax.todos.domain.TodosService;
import de.sample.javax.todos.persistence.Database;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@WebServlet("/anzeige")
public class AnzeigenServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    @Database
    TodosService service;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String searchText = request.getParameter("title");
        Collection<Todo> todos = (null != searchText ? service.getTodos(searchText) : service.getTodos())
          .collect(Collectors.toList());

        request.setAttribute("todos", todos);
        getServletContext()
          .getNamedDispatcher("ausgabe-page")
          .forward(request, response);

    }

}
