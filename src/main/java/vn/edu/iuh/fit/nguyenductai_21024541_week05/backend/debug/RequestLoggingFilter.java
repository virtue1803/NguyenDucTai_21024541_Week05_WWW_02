package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.debug;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RequestLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("URL yêu cầu đến: " + request.getRequestURL());
        System.out.println("Phương thức HTTP: " + request.getMethod());
        filterChain.doFilter(request, response); // Tiếp tục với yêu cầu
    }
}
