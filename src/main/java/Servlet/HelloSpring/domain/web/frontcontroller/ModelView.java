package Servlet.HelloSpring.domain.web.frontcontroller;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


@Setter
@Getter
public class ModelView {
    private String viewName; // view의 논리이름을 가져갈 속성
    private Map<String, Object> model = new HashMap<>(); // HttpServlet의 setAttribute 메서드를 대체, 의존성을 제거하기위한 속성

    public ModelView(String viewName) {
        this.viewName = viewName;
    }

}
