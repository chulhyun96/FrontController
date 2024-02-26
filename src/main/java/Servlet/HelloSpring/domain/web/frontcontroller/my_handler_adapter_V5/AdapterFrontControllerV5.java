package Servlet.HelloSpring.domain.web.frontcontroller.my_handler_adapter_V5;


import Servlet.HelloSpring.domain.web.my_front_controller_V4.ListController;
import Servlet.HelloSpring.domain.web.my_front_controller_V4.SaveController;
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

@WebServlet("/*")
public class AdapterFrontControllerV5  extends HttpServlet {
    private Map<String, Object> handlerMapping = new HashMap<>();
    private List<HandlerAdapter> handlerList = new ArrayList<>();

    public AdapterFrontControllerV5() {
        handlerMapping.put("/save", new SaveController());
        handlerMapping.put("/list", new ListController());

        handlerList.add(new MyFrontControllerHandlerAdapter());
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        Object handler  = handlerMapping.get(requestURI);

        HandlerAdapter adapter = getAdapter(handler);

        assert adapter != null;
        ModelView mv = adapter.service(request, response, handler);
        ForwardView forwardView = viewResolver(mv.getViewName());
        forwardView.render(request, response, mv.getModel());
    }

    private ForwardView viewResolver(String viewName) {
        return new ForwardView("/WEB-INF/view/" + viewName + ".jsp");

    }

    private HandlerAdapter getAdapter(Object handler) {
        for (HandlerAdapter adapter : handlerList) {
            if (adapter.supports(handler)) return adapter;
        }
        return null;
    }
}
