package Servlet.HelloSpring.domain.web.servlet;

import Servlet.HelloSpring.domain.Member;
import Servlet.HelloSpring.domain.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/servlet/members/list")
public class MemberListServlet extends HttpServlet {
    private MemberRepository repository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        List<Member> allMemberList = repository.findAll();

        PrintWriter w = response.getWriter();
        w.write("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <title>Document</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<table>\n"
                + "<thead>\n"
                +"<th>id</th>\n"
                +"<th>name</th>\n"
                +"<th>age</th>\n"
                + "</thead>\n"
                +"<tbody>\n");
        for (Member member : allMemberList) {
            w.write("<tr>");
            w.write("<td>" + member.getId() + "</td>");
            w.write("<td>" + member.getUserName() + "</td>");
            w.write("<td>" + member.getAge() + "</td>");
            w.write("</tr>");
        }
        w.write("</tbody>");
        w.write("</table>");
        w.write("</body>");
        w.write("</html>");
        return null;
    }
}

