package Servlet.HelloSpring.domain.web.frontcontroller.v3;

import Servlet.HelloSpring.domain.web.frontcontroller.ModelView;import java.util.Map;
public class MemberFormControllerV3 implements ControllerV3{
  @Override
    public ModelView process(Map<String, String> paraMap) {
        return new ModelView("new-form");
    }
}
