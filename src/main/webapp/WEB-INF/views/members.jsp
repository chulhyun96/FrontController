<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
                <c:forEach var="item" items="${allMembers}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.userName}</td>
                    <td>${item.age}</td>
                </tr>
                </c:forEach>
        </tbody>
      </table >
        <a href="/index.html">Home</a>
</body>
</html>

