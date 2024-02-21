package Servlet.HelloSpring.domain.web.frontcontroller.v4;

import Servlet.HelloSpring.domain.Member;
import Servlet.HelloSpring.domain.MemberRepository;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4{
    private MemberRepository repository = MemberRepository.getInstance();
    /**
     * @param paraMap
     * @param model
     * @return viewName
     */
    @Override
    public String process(Map<String, String> paraMap, Map<String, Object> model) {
        String username = paraMap.get("username");
        int age = Integer.parseInt(paraMap.get("age"));

        Member member = new Member(username, age);
        repository.save(member);

        model.put("member", member);
        return "save-result";
    }
}
