package de.deutscherv.gb0500.schulung.javaee.boundary;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/db-jpa")
public class DatabaseJPAConnectionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @PersistenceContext(name = "puTodoDB")
	EntityManager em;
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            out.println("EntityManager:");
            out.print(em);
        }
    }

}
