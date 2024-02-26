package Servlet.HelloSpring.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Test")
public class HelloServelt extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.write("<h1>Servlet</h1>");

        String username = req.getParameter("username");
        System.out.println("username = " + username);

        resp.setContentType("text/html; charset=UTF-8");

        resp.getWriter().write("hello");


        return null;
    }
}
