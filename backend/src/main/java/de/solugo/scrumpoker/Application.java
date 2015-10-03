package de.solugo.scrumpoker;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

@Configuration
@ComponentScan
public class Application implements WebApplicationInitializer {

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        final AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(Application.class);

        final ServletRegistration.Dynamic servlet = servletContext.addServlet("spring", new DispatcherServlet(applicationContext));
        servlet.addMapping("/*");

        final FilterRegistration.Dynamic filters = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
        filters.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST), true, "spring");
    }

}
