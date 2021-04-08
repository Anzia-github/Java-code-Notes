<%--
  Created by IntelliJ IDEA.
  User: 34794
  Date: 2021/3/16
  Time: 19:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<c:set var = "score" value="95"/>

<c:choose>
    <c:when test="${score >= 90}">你的成绩为优秀</c:when>
    <c:when test="${score >= 80}">你的成绩为良好</c:when>
    <c:when test="${score >= 70}">你的成绩为一般</c:when>
    <c:when test="${score >= 60}">你的成绩为普通</c:when>

</c:choose>





</body>
</html>
