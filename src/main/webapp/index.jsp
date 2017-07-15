<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/12
  Time: 13:12
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
    <title>index</title>
</head>
<body>
Hello there<br />
<a href="home.do">Main</a>
<script type="text/javascript">
    window.location.href = "home.do";		//javascript页面跳转
</script>
</body>
</html>
