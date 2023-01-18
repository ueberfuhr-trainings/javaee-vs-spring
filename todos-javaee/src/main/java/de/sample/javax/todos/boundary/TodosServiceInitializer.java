package de.sample.javax.todos.boundary;

import de.sample.javax.todos.domain.TodosService;
import de.sample.javax.todos.domain.TodosServiceMemoryImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//@WebListener -> ersetzt durch CDI
public class TodosServiceInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        TodosService service = new TodosServiceMemoryImpl();
        sce.getServletContext().setAttribute("todosService", service);
    }

}
