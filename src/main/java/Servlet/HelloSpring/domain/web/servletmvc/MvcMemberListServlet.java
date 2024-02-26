package Servlet.HelloSpring.domain.web.servletmvc;

import Servlet.HelloSpring.domain.Member;
import Servlet.HelloSpring.domain.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/servlet-mvc/members")
public class MvcMemberListServlet extends HttpServlet {
    private MemberRepository repository = MemberRepository.getInstance();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Member> allMemberList = repository.findAll();
        request.setAttribute("allMembers", allMemberList);

        String viewPath = "/WEB-INF/views/members.jsp";
        request.getRequestDispatcher(viewPath)
                .forward(request, response);

        return null;
    }
}
