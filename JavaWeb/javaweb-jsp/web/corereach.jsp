<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: 34794
  Date: 2021/3/16
  Time: 19:33
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    ArrayList<String> people = new ArrayList<>();
    people.add(0,"张三1");
    people.add(1,"张三2");
    people.add(2,"张三3");
    people.add(3,"张三4");
    people.add(4,"张三5");
    people.add(5,"张三6");
    people.add(6,"张三7");
    request.setAttribute("list",people);
%>
<%--
var，每一次遍历出来的变量
items，要遍历的变量
--%>
<c:forEach var="people" items="${list}">
    <c:out value="${people}"/> <br>
</c:forEach>

<hr>

<c:forEach var="people" items="${list}" begin="1" end="3" step="2">
    <c:out value = "${people}"/><br>
</c:forEach>
</body>
</html>
