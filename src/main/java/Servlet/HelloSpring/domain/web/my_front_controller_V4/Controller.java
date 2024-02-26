package Servlet.HelloSpring.domain.web.my_front_controller_V4;

import java.util.Map;

public interface Controller {
    String service (Map<String,String> paraMap, Map<String, Object> model);
}
