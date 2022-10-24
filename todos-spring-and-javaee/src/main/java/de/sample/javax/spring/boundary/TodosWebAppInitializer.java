package de.sample.javax.spring.boundary;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import de.sample.javax.spring.AppConfig;

public class TodosWebAppInitializer implements WebApplicationInitializer {

	private static final String SPRING_SERVLET_NAME = "spring";
	
	@Override
	public void onStartup(ServletContext servletContext) {

		// Load Spring web application configuration
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(AppConfig.class);

		servletContext.addListener(new ContextLoaderListener(context));

		// Create and register the DispatcherServlet
		DispatcherServlet servlet = new DispatcherServlet(context);
		ServletRegistration.Dynamic registration = servletContext.addServlet(SPRING_SERVLET_NAME, servlet);
		registration.setLoadOnStartup(1);
		registration.addMapping("/spring/*");

		// register the security filter
//		servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class)
//				.addMappingForServletNames(EnumSet.allOf(DispatcherType.class), false, SPRING_SERVLET_NAME);
	}
}