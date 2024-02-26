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

@WebServlet("/servlet/members/save")
public class MemberSaveServlet extends HttpServlet {
    private MemberRepository repository = MemberRepository.getInstance();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        String userName = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        Member member = new Member(userName, age);
        repository.save(member);

        PrintWriter w = response.getWriter();
        w.write("<!DOCTYPE html>\n"
                + "<html lang=\"kr\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <title>Document</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <p>저장에 성공하였습니다(Member 객체 생성)</p>\n"
                + "\n"
                + "    <ul>\n"
                + "        <li>id :"+ member.getId()+"</li>\n"
                + "        <li>name :" +  member.getUserName()+ "</li>\n"
                + "        <li>age :" + member.getAge()+ "</li>\n"
                + "    </ul>\n"
                + " <a href=\"/index.html\">메인</a>\n"
                + "</body>\n"
                + "</html>");
        return null;
    }
}
