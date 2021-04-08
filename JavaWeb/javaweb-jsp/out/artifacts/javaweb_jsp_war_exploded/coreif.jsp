<%--
  Created by IntelliJ IDEA.
  User: 34794
  Date: 2021/3/16
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h4>if测试</h4>

<hr>

<form action="coreif.jsp" method="get">
    <%--
    EL表达式获取表单中的数据
    ${param.参数名}
    --%>
    <input type="text" name="username" value="${param.username}">
    <input type="submit" value="登陆">
</form>

<%--判断如果提交的用户名是管理员，则操作成功--%>
<c:if test="${param.username == 'admin'}" var = "isAdmin">
    <c:out value="管理员欢迎你"/>
</c:if>

<c:out value="${isAdmin}"/>

</body>
</html>
