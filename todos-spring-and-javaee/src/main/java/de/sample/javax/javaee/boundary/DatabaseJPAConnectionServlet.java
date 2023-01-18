package de.sample.javax.javaee.boundary;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/db-jpa")
public class DatabaseJPAConnectionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    EntityManager em;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            out.println("EntityManager:");
            out.print(em);
        }
    }

}
