package Servlet.HelloSpring.domain.web.frontcontroller.v1.controller;

import Servlet.HelloSpring.domain.Member;
import Servlet.HelloSpring.domain.MemberRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberSaveControllerV1 implements ControllerV1{
    private MemberRepository repository = MemberRepository.getInstance();

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(userName, age);
        repository.save(member);
        // 호출하고 양식에 맞게 입력한 뒤 정보를 저장


        // 그리고 request 객체에 정보를 바인딩 한뒤 jsp View로 넘김 -> detail
        request.setAttribute("member",member);
        String viewPath = "/WEB-INF/views/save-result.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewPath);
        requestDispatcher.forward(request, response);
    }
}
