package Servlet.HelloSpring.domain.web.frontcontroller.my_handler_adapter_V5;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface HandlerAdapter {
    boolean supports (Object handler);
    ModelView service(HttpServletRequest request, HttpServletResponse response, Object   handler) throws ServletException, IOException;
}
