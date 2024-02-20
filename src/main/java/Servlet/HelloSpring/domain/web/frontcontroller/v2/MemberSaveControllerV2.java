package Servlet.HelloSpring.domain.web.frontcontroller.v2;

import Servlet.HelloSpring.domain.Member;
import Servlet.HelloSpring.domain.MemberRepository;
import Servlet.HelloSpring.domain.web.frontcontroller.MyView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberSaveControllerV2 implements ControllerV2{
    private MemberRepository repository = MemberRepository.getInstance();

    @Override
    public MyView service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(userName, age);
        repository.save(member);


        request.setAttribute("member",member);
        return new MyView("/WEB-INF/views/save-result.jsp");

    }
}
