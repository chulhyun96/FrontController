package Servlet.HelloSpring.domain.web.frontcontroller.my_handler_adapter_V5;

import java.util.HashMap;
import java.util.Map;

public class ModelView {
    private String viewName;
    private Map<String, Object> model = new HashMap<>(); // HttpServlet의 setAttribute 메서드를 대체, 의존성을 제거하기위한 속성
    public ModelView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

}

