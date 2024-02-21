package Servlet.HelloSpring.domain.web.frontcontroller.v4;

import java.util.Map;

public class MemberFormControllerV4 implements ControllerV4{
    /**
     * @param paraMap
     * @param model
     * @return viewName
     */
    @Override
    public String process(Map<String, String> paraMap, Map<String, Object> model) {
        return "new-form";
    }
}
