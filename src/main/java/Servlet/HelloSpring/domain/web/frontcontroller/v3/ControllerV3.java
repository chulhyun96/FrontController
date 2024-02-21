package Servlet.HelloSpring.domain.web.frontcontroller.v3;

import Servlet.HelloSpring.domain.web.frontcontroller.ModelView;

import java.util.Map;public interface ControllerV3 {
    ModelView process(Map<String, String> paraMap);

}

//Map<String, String> paraMap 해당 코드조각이
// 더이상 servlet의 getParameter() 메서드를 의존하지 않음.(사용하지 않음)
