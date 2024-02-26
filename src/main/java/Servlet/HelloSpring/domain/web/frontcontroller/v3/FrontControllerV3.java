package Servlet.HelloSpring.domain.web.frontcontroller.v3;

import Servlet.HelloSpring.domain.web.frontcontroller.ModelView;
import Servlet.HelloSpring.domain.web.frontcontroller.MyView;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/*front-controller/v3/members/new-form*/
@WebServlet("/front-controller/v3/*")
public class FrontControllerV3 extends HttpServlet {
    private final Map<String, ControllerV3> controllerV3Map = new HashMap<>();

    public FrontControllerV3() {
        controllerV3Map.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerV3Map.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerV3Map.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV3 controllerV3 = controllerV3Map.get(requestURI);

        if (controllerV3 == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        HashMap<String, String> paraMap = createParaMap(request);
        ModelView mv = controllerV3.process(paraMap);

        MyView view = viewResolver(mv.getViewName());
        view.render(mv.getModel(), request, response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private HashMap<String, String> createParaMap(HttpServletRequest request) {
        // 웹에서 파라미터로 넘어온 정보를 모두 조회해준뒤  HashMap에 저장함 -> 그 뒤 process 메서드의 파라미터로 넘김.
        HashMap<String, String> paraMap = new HashMap<>();
        //Request 객체로 받은 모든 요청 파라미터 정보를 조회함
        //예를 들어, 요청 URL이 ?username=john&age=30일 경우,
        //paraMap은 {"username": "john", "age": "30"}과 같은 형태로 채워집니다.
        request.getParameterNames().asIterator()
                .forEachRemaining(paraInfo -> paraMap.put(paraInfo, request.getParameter(paraInfo)));
        return paraMap;
    }
}
