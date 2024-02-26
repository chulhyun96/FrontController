package Servlet.HelloSpring.domain.web.my_front_controller_V4;

import Servlet.HelloSpring.domain.Member;
import Servlet.HelloSpring.domain.MemberRepository;

import java.util.Map;

public class SaveController implements Controller{
    private MemberRepository rep = MemberRepository.getInstance();
    @Override
    public String service(Map<String, String> paraMap, Map<String, Object> model) {
        String username = paraMap.get("username");
        int age = Integer.parseInt(paraMap.get("age"));

        Member member = new Member(username, age);
        rep.save(member);

        model.put("member", member);
        return "save";
    }
}
