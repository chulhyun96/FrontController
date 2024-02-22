package Servlet.HelloSpring.domain.web.frontcontroller.v4;

import Servlet.HelloSpring.domain.Member;
import Servlet.HelloSpring.domain.MemberRepository;

import java.util.List;
import java.util.Map;

public class MemberListControllerV4 implements ControllerV4{
    private MemberRepository repository = MemberRepository.getInstance();
    /**
     * @param paraMap
     * @param model
     * @return viewName
     */
    @Override
    public String process(Map<String, String> paraMap, Map<String, Object> model) {
        List<Member> allMemberList = repository.findAll();
        model.put("allMembers", allMemberList);
        return "members";
    }
}
