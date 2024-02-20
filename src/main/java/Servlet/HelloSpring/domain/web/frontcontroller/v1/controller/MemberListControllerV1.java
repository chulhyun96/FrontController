package Servlet.HelloSpring.domain.web.frontcontroller.v1.controller;

import Servlet.HelloSpring.domain.Member;
import Servlet.HelloSpring.domain.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class MemberListControllerV1 implements ControllerV1{
    private MemberRepository repository = MemberRepository.getInstance();

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Member> allMemberList = repository.findAll();
        request.setAttribute("allMembers", allMemberList);

        String viewPath = "/WEB-INF/views/members.jsp";
        request.getRequestDispatcher(viewPath)
                .forward(request, response);

    }
}
