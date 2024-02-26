package Servlet.HelloSpring.domain.web.my_front_controller_V4;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/*")
public class FrontController extends HttpServlet {
    private Map<String, Controller> mappingController = new HashMap<>();
    public FrontController() {
        mappingController.put("/save", new SaveController());
        mappingController.put("/list", new ListController());
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        Controller controller = mappingController.get(requestURI);

        Map<String, String> paraMap = createParam(request);
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.service(paraMap, model);
        ModelView v = new ModelView(viewName);

        ForwardView forwardView = viewResolver(v.getViewName());
        forwardView.render(model,request, response);

    }

    private ForwardView viewResolver(String viewName) {
        return new ForwardView("/WEB-INF/view/" + viewName + ".jsp");
    }

    private Map<String, String> createParam(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paraName -> paramMap.put(paraName, request.getParameter(paraName)));

        return paramMap;
    }
}
