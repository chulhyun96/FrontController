package Servlet.HelloSpring.domain.web.my_front_controller_V4;

import Servlet.HelloSpring.domain.Member;
import Servlet.HelloSpring.domain.MemberRepository;

import java.util.List;
import java.util.Map;

public class ListController implements Controller{
    private MemberRepository rep = MemberRepository.getInstance();
    @Override
    public String service(Map<String, String> paraMap, Map<String, Object> model) {
        List<Member> all = rep.findAll();
        model.put("all",all);
        return "list";
    }
}
