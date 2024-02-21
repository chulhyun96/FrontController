<%--@elvariable id="member" type="org.springframework.web.servlet.tags.form.AbstractDataBoundFormElementTag"--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import ="Servlet.HelloSpring.domain.Member"%>
<%@ page import ="Servlet.HelloSpring.domain.MemberRepository"%>
<%
    MemberRepository repository = MemberRepository.getInstance();
     String userName = request.getParameter("username");
     int age = Integer.parseInt(request.getParameter("age"));
     Member member = new Member(userName, age);
     repository.save(member);
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>
    <p>Member의 정보를 성공적으로 저장하였습니다(멤버 객체 생성 및 저정 완료)</p></h2>
<ul>
    <li>Id :${member.id}</li>
    <li>Username : ${member.userName}</li>
    <li>Age : ${member.age}</li>
</ul>
<a href="/index.html">HOME</a>
</body>
</html>