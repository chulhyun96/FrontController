package Servlet.HelloSpring.domain.web.frontcontroller.v2;

import Servlet.HelloSpring.domain.web.frontcontroller.MyView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ControllerV2 {
    MyView service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
