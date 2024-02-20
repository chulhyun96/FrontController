package Servlet.HelloSpring.domain.web.frontcontroller.v2;

import Servlet.HelloSpring.domain.Member;
import Servlet.HelloSpring.domain.MemberRepository;
import Servlet.HelloSpring.domain.web.frontcontroller.MyView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class MemberListControllerV2 implements ControllerV2{
    private MemberRepository repository = MemberRepository.getInstance();
    @Override
    public MyView service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Member> allMemberList = repository.findAll();
        request.setAttribute("allMembers", allMemberList);
        return new MyView("/WEB-INF/views/members.jsp");
    }
}
