<%--
  Created by IntelliJ IDEA.
  User: 34794
  Date: 2021/3/24
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/statics/js/jquery-3.6.0.js"></script>
    <script>
        $(function () {
            $("#btn").click(function () {
                $.post("${pageContext.request.contextPath}/a2",function (data) {
                    // console.log(data);
                    var html = "";

                    for (let i = 0; i < data.length; i++) {
                        html += "<tr>"
                            + "<td>" + data[i].name + "</td>"
                            + "<td>" + data[i].age + "</td>"
                            + "<td>" + data[i].sex + "</td>"
                            + "</tr>"
                    }

                    $("#content").html(html);

                });
            })
        });
    </script>
</head>
<input type="button" value="加载数据" id="btn">
<body>
<table>
<tr>
    <td>姓名</td>
    <td>年龄</td>
    <td>性别</td>
</tr>
    <tbody id="content"></tbody>
</table>
<tbody>
<%--后台数据--%>
</tbody>

</body>
</html>
