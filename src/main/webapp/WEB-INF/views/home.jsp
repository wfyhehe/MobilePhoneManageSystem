<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/12
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>" />
    <title>Welcome,${sessionScope.username}</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <link href="${pageContext.request.contextPath}/css/bootstrap.css"
          type="text/css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/home.css?id=<%=Math.random()%>"
          type="text/css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>
    <script src="${pageContext.request.contextPath}/js/vue.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="menu" id="menu">
        <button v-for="(v, k) in menuItems" id="k">{{v.name}}</button>
        <div class="user">欢迎！
            <a href="javascript:void(0);">SuperAdmin
                ${sessionScope.username}</a></div>
    </div>
    <div class="main">
        <div class="content">
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/home.js?randomId=" +
        <%=Math.random()%>></script>
</body>
</html>
