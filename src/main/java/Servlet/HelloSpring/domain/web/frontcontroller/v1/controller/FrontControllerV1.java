package Servlet.HelloSpring.domain.web.frontcontroller.v1.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/front-controller/v1/*")
// *처리를 함으로써 v1의 하위 웹 매핑 객체들이 작동하게 됨
public class FrontControllerV1 extends HttpServlet {
    private  Map<String, ControllerV1> controllerV1Map = new HashMap<>();

    public FrontControllerV1() {
        controllerV1Map.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerV1Map.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerV1Map.put("/front-controller/v1/members/members", new MemberListControllerV1());
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestUrl = request.getRequestURI();
        ControllerV1 controllerV1 = controllerV1Map.get(requestUrl);

        if (controllerV1 == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;

        }
        controllerV1.service(request,response);
    }
}
