package proton.face.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JSFConfig {
    @Bean
    public ServletRegistrationBean<?> facesServlets() {
        ServletRegistrationBean<?> servletRegistrationBean =
                new ServletRegistrationBean<>(new javax.faces.webapp.FacesServlet(), "*.xhtml");
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }
}
