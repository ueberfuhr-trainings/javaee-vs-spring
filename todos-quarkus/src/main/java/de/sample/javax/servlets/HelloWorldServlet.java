package de.sample.javax.servlets;

import org.eclipse.microprofile.config.inject.ConfigProperties;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class HelloWorldServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    @ConfigProperties
    HelloWorldConfig config;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

        // Request auslesen (Parameter, Header)
        String name = request.getParameter("name");
        // Konvertieren und Validieren
        if (null == name || name.isEmpty()) {
            name = config.getDefaultName();
        }

        // Aktion
        // (keine)

        // Antwortmöglichkeiten
        // - response.getWriter()
        // - response.getOutputStream()
        // - response.sendError(405);
        // - response.sendRedirect("xyz");
        // oder: forward
        response.setContentType(config.getContentType());
        try (PrintWriter out = response.getWriter()) {
            out.println(config.getPattern().replace("{0}", name));
        }
    }

}
