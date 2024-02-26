package Servlet.HelloSpring.domain.web.my_front_controller_V4;

public class ModelView {
    private String viewName;

    public ModelView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }
}
