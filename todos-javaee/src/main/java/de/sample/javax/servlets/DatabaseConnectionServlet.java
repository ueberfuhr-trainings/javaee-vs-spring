package de.sample.javax.servlets;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Servlet implementation class DatabaseConnectionServlet
 */
@WebServlet("/db")
public class DatabaseConnectionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Resource(lookup = "jdbc/TodoDB")
    DataSource ds;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        try (Connection con = ds.getConnection();
          PrintWriter out = response.getWriter()) {
            out.println("Verbindungsdaten\n================\n\n");
            DatabaseMetaData metaData = con.getMetaData();
            out.print(metaData.getDatabaseProductName());
            out.print(" ");
            out.println(metaData.getDatabaseProductVersion());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

}
