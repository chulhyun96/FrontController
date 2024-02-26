package Servlet.HelloSpring.basic.request;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/request-param")
public class RequestParam extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("[단일 파라미터 조회] -  start");
        String username = request.getParameter("username");
        System.out.println("username = " + username);

        String age = request.getParameter("age");
        System.out.println("age = " + age);

        System.out.println("[단일 파라미터 조회] -  end");

        String method = request.getMethod();
        System.out.println("method = " + method);

        System.out.println();
        response.getWriter().write("OK\n");
        response.getWriter().write("username = " + username +"\n");
        response.getWriter().write("age = " + age +"\n");
        // 멤버리스트를 꺼내와서 username과 age를 입력할거임
        return null;
    }

}
