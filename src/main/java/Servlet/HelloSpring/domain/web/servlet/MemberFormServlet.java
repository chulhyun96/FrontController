package Servlet.HelloSpring.domain.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/servlet/members/save-form")
public class MemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter w = response.getWriter();

        w.write("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <title>Document</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<form action=\"/servlet/members/save\" method=\"post\">\n"
                + "    <label>\n"
                + "        username<input type=\"text\" name=\"username\">\n"
                + "    </label>\n"
                + "    <label>\n"
                + "        age<input type=\"text\" name=\"age\">\n"
                + "    </label>\n"
                + "    <button type=\"submit\">전송</button>\n"
                + "</form>\n"
                + "</body>\n"
                + "</html>");
    }
}
