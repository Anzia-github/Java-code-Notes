<html>
<body>
<h2>Hello World!</h2>

<%--这里提交的路径，需要寻找到项目的路径--%>
<%--${pageContext.request.contextPath} 代表当前的项目--%>
<%@ page pageEncoding="utf-8" %>
<form action="${pageContext.request.contextPath}/login" method="get" >
    <%--如果出现地址解析错误，但是又没发现什么问题，那么问题可能是web.xml的版本太低，不支持el表达式--%>
    用户名：<input type="text" name="username"> <br>
    密码：<input type="password" name="password"> <br>
    <input type="submit">
</form>


</body>
</html>
