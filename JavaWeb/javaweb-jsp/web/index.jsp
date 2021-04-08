<%--
  Created by IntelliJ IDEA.
  User: 34794
  Date: 2021/3/16
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>

  <%--JSP表达式
  作用：用来将程序的输出，输出到客户端
  <%= 变量或者表达式%>
  --%>
  <%= new java.util.Date()%>

  <%--JSP脚本片段--%>
  <%
    int sum = 0;
    for (int i = 1; i <= 100; i++) {
      sum += i;
    }
    out.println("<h1>Sum = " + sum + "</h1>");
  %>

  <%--脚本片段的再实现--%>
  <%
    int x = 10;
    out.println(x);
  %>
  <p>这是一个JSP文档</p>
  <%
    int y = 20;
    out.println(y);
  %>
  <hr>

  <%
    for (int i = 0; i < 5; i++) {

  %>
  <h1>Hello,World! <%= i%> </h1>
  <%
    }
  %>


  <%!
    static {
      System.out.println("Loadint Servlet!");
    }

    private int globalVar = 0;

    public void kuang() {
      System.out.println("进入了方法！");
    }
  %>

  </body>
</html>
