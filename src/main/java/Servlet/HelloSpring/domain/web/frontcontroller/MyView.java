package Servlet.HelloSpring.domain.web.frontcontroller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class MyView {
    String viewPath;
    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }
    //ControllerV3
    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(viewPath).forward(request, response);
    }
    //ControllerV4
    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        model.forEach(request::setAttribute);
        request.getRequestDispatcher(viewPath).forward(request, response);
    }
}
