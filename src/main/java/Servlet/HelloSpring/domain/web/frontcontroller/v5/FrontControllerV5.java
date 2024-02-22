package Servlet.HelloSpring.domain.web.frontcontroller.v5;


import Servlet.HelloSpring.domain.web.frontcontroller.ModelView;
import Servlet.HelloSpring.domain.web.frontcontroller.MyView;
import Servlet.HelloSpring.domain.web.frontcontroller.v3.MemberFormControllerV3;
import Servlet.HelloSpring.domain.web.frontcontroller.v3.MemberListControllerV3;
import Servlet.HelloSpring.domain.web.frontcontroller.v3.MemberSaveControllerV3;
import Servlet.HelloSpring.domain.web.frontcontroller.v4.MemberFormControllerV4;
import Servlet.HelloSpring.domain.web.frontcontroller.v4.MemberListControllerV4;
import Servlet.HelloSpring.domain.web.frontcontroller.v4.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/front-controller/v5/*")
public class FrontControllerV5 extends HttpServlet {
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    //여러개의 어댑터가 담겨있고, 내가 그중에서 하나를 찾아 꺼내 쓰기 위한 List
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerV5() {
        initHandlerMapping();
        initHandlerAdapters();
    }

    private void initHandlerMapping() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //핸들러 조회
        //url정보를 통해서 핸들러 조회,
        Object handler = getHandler(request);
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //조회한 핸들러어댑터 가져와
        //조회된 핸들러를 핸들러어댑터 목록에서 어댑터를 꺼내옴
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        //핸들러 어댑터가 적절한 핸들러한테 핸들을 호출하면 ModelView를 반환
        ModelView mv = adapter.handle(request, response, handler);

        //그리고 ViewResolver를 호출해서 MyView 정보를 반환받는다
        MyView myView = viewResolver(mv.getViewName());

        //그럼 MyView가 렌더를 호출 -> HTML로 응답
        myView.render(mv.getModel(), request, response);

    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        System.out.println("requestURI = " + requestURI);

        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler not found this.handler = " + handler);
    }
}
