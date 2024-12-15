package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000","http://localhost:8080","localhost:8080")  // Cho phép yêu cầu từ frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }


}
