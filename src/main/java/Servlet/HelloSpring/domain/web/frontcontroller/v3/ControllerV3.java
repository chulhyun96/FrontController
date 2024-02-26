package Servlet.HelloSpring.domain.web.frontcontroller.v3;

import Servlet.HelloSpring.domain.web.frontcontroller.ModelView;

import java.util.Map;
public interface ControllerV3 {
    ModelView process(Map<String, String> paraMap);
}
