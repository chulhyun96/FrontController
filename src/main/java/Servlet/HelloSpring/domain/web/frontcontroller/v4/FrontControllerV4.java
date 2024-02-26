package Servlet.HelloSpring.domain.web.frontcontroller.v4;

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
@WebServlet("/front-controller/v4/*")
public class FrontControllerV4 extends HttpServlet {
    private final Map<String, ControllerV4> controllerV4Map = new HashMap<>();

    public FrontControllerV4() {
        controllerV4Map.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerV4Map.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerV4Map.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV4 controllerV4 = controllerV4Map.get(requestURI);

        if (controllerV4 == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        Map<String, String> paraMap = createParaMap(request);
        Map<String , Object> model = new HashMap<>();
        String viewName = controllerV4.process(paraMap, model);

        MyView view = viewResolver(viewName);

        view.render(model, request, response);
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

        System.out.println(paraMap.get("username"));
        System.out.println(paraMap.get("age"));

        return paraMap;
    }
}
