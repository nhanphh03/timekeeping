package nhanph.timekeeping.admin.config;

import com.sun.faces.config.ConfigureListener;
import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FacesServlet {
    @Bean
    public ServletRegistrationBean<javax.faces.webapp.FacesServlet> facesServletRegistration() {
        ServletRegistrationBean<javax.faces.webapp.FacesServlet> registration = new ServletRegistrationBean<>(new javax.faces.webapp.FacesServlet(), "*.xhtml");
        registration.setLoadOnStartup(1);
        return registration;
    }

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> {
            servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
            servletContext.setInitParameter("primefaces.THEME", "nova-light");
            servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
            servletContext.setInitParameter("primefaces.UPLOADER", "commons");
        };
    }

    @Bean
    public FilterRegistrationBean<FileUploadFilter> fileUploadFilter() {
        FilterRegistrationBean<FileUploadFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new FileUploadFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
        return new ServletListenerRegistrationBean<>(new ConfigureListener());
    }
}
