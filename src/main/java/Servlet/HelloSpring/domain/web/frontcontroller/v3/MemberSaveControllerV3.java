package Servlet.HelloSpring.domain.web.frontcontroller.v3;

import Servlet.HelloSpring.domain.Member;
import Servlet.HelloSpring.domain.MemberRepository;
import Servlet.HelloSpring.domain.web.frontcontroller.ModelView;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {
    private final MemberRepository repository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paraMap) {
        //createParaMap 메서드에 저장되어있는 쿼리 파라미터의 값을 꺼내온다
        String username = paraMap.get("username");
        int age = Integer.parseInt(paraMap.get("age"));

        Member member = new Member(username,age);
        repository.save(member);

        ModelView modelView = new ModelView("save-result");
        modelView.getModel().put("member",member);
        return modelView;
    }
}
