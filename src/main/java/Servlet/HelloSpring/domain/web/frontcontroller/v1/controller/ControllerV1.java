package Servlet.HelloSpring.domain.web.frontcontroller.v1.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ControllerV1 {
    void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
