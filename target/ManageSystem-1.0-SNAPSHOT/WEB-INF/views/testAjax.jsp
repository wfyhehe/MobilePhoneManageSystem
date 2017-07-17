<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/17
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>testAjax</title>
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>

</head>

<body>
<a href="javascript:void(0);" onclick="send(alert);">测试</a>
<div id="div1"></div>
<script>
    function send(callback) {
        var url = "user.json";
        $.getJSON(url, null, function (data) {
            console.log(data);
            callback(data);
        });
    }
</script>
</body>
</html>
