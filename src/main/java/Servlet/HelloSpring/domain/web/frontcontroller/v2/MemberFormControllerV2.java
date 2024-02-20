package Servlet.HelloSpring.domain.web.frontcontroller.v2;

import Servlet.HelloSpring.domain.web.frontcontroller.FormV2View;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberFormControllerV2 implements ControllerV2{
    @Override
    public FormV2View service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return new FormV2View("/WEB-INF/views/new-form.jsp");
    }
}
