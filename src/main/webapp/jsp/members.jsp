<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="Servlet.HelloSpring.domain.Member"%>
<%@page import="Servlet.HelloSpring.domain.MemberRepository"%>
<%@page import="jakarta.servlet.ServletException"%>
<%@page import="jakarta.servlet.annotation.WebServlet"%>
<%@page import="jakarta.servlet.http.HttpServlet"%>
<%@page import="jakarta.servlet.http.HttpServletRequest"%>
<%@page import="jakarta.servlet.http.HttpServletResponse"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.List"%>

<%
        MemberRepository repository = MemberRepository.getInstance();
        List<Member> allMemberList = repository.findAll();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>

      <table>
                <thead>
                <th>id</th>
                <th>name</th>
                <th>age</th>
                </thead>
                <tbody>
       <% for (Member member : allMemberList) {
            out.write("<tr>");
            out.write("<td>" + member.getId() + "</td>");
            out.write("<td>" + member.getUserName() + "</td>");
            out.write("<td>" + member.getAge() + "</td>");
            out.write("</tr>");
        }
        %>
        </tbody>
        </table>
        <a href="/index.html">Home</a>
</body>
</html>

