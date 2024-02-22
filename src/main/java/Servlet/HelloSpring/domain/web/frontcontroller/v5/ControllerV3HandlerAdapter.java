package Servlet.HelloSpring.domain.web.frontcontroller.v5;

import Servlet.HelloSpring.domain.web.frontcontroller.ModelView;
import Servlet.HelloSpring.domain.web.frontcontroller.v3.ControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV3 controller = (ControllerV3) handler;

        Map<String, String> paraMap = createParaMap(request);

        return controller.process(paraMap);
    }
    private Map<String, String> createParaMap(HttpServletRequest request) {
        // 웹에서 파라미터로 넘어온 정보를 모두 조회해준뒤  HashMap에 저장함 -> 그 뒤 process 메서드의 파라미터로 넘김.
        Map<String, String> paraMap = new HashMap<>();
        //Request 객체로 받은 모든 요청 파라미터 정보를 조회함
        //예를 들어, 요청 URL이 ?username=john&age=30일 경우,
        //paraMap은 {"username": "john", "age": "30"}과 같은 형태로 채워집니다.
        request.getParameterNames().asIterator()
                .forEachRemaining(paraInfo -> paraMap.put(paraInfo, request.getParameter(paraInfo)));
        return paraMap;
    }
}
