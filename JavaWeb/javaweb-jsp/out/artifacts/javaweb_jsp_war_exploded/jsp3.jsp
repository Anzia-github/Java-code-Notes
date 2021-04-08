<%--
  Created by IntelliJ IDEA.
  User: 34794
  Date: 2021/3/16
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%--@include会将两个页面合二为一--%>
<%@include file="common/header.jsp"%>
<h1>网页主体</h1>
<%@include file="common/footer.jsp"%>

<hr>

<%--jsp:include：拼接页面，本质还是三个--%>
<%--jsp标签--%>
<jsp:include page="common/header.jsp"/>
</body>
</html>
