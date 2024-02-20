package Servlet.HelloSpring.domain.web.frontcontroller.v3;

import Servlet.HelloSpring.domain.Member;
import Servlet.HelloSpring.domain.MemberRepository;
import Servlet.HelloSpring.domain.web.frontcontroller.ModelView;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {
    private MemberRepository repository = MemberRepository.getInstance();
    @Override
    public ModelView process(Map<String, String> paraMap) {
        List<Member> allMemberList = repository.findAll();
        ModelView modelView = new ModelView("members");
        modelView.getModel().put("allMembers", allMemberList);
        return modelView;
    }
}
