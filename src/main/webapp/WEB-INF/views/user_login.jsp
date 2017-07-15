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
    <title>Main</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <link href="${pageContext.request.contextPath}/css/bootstrap.css"
          type="text/css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/user_login.css?id=<%=Math.random()%>"
          type="text/css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>
    <script src="${pageContext.request.contextPath}/js/vue.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>

</head>
<body>
<div class="container-fluid">
    <div id="container">
        <div class="left-logo"></div>
        <div class="login-form-div">
            <form action="login_process.do" id="form-validation" method="post">
                <div class="form-group">
                    <label for="username">用户名：</label>
                    <input id="username" type="text" name="username"
                           title="用户名" v-model="username"
                           @focus="handleFocus" /><br />
                    <span class="text-warning" v-if="!usernameCheck()">用户名只能由6-18
                    位的字母、数字、下划线组成</span>
                    <span v-else>&nbsp;</span>
                </div>
                <div class="form-group">
                    <label for="password">密　码：</label>
                    <input type="password" id="password" name="password"
                           title="密码" v-model="password"
                           @focus="handleFocus" /><br />
                    <span class="text-warning" v-if="!passwordCheck()">
                    密码长度为6-18位</span>
                    <span v-else>&nbsp;</span>
                </div>
                <div class="form-group">
                    <label for="verificationCode">验证码：</label>
                    <input type="text" id="verificationCode"
                           name="verificationCode" @focus="handleFocus"
                           v-model="verificationCode" title="验证码"
                    />
                    <a class="verification-code">
                        <img v-bind:src="'authImage?id='+vCodeId"
                             @click="changeImg"
                             alt="看不清？换一张" />
                        <span
                                @click="changeImg">看不清？换一张</span>
                    </a><br />
                    <span class="text-warning" v-if="!verificationCodeCheck()">
                    验证码为4位数字或字母</span>
                    <span v-else>&nbsp;</span>
                    <span id="verificationCodeError" class="text-danger"
                          style="display: none;" v-if="!anyFocus">验证码错误！
                    </span>
                    <span id="usernameError" class="text-danger"
                          style="display: none;" v-if="!anyFocus">用户名不存在！
                    </span>
                    <span id="passwordError" class="text-danger"
                          style="display: none;" v-if="!anyFocus">密码错误！</span>
                </div>
                <input class="submit" id="submit" v-bind:disabled="!valid()"
                       type="submit" value="" />
            </form>
        </div>
        <div class="right-pic"></div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/user_login.js?randomId=" +
        <%=Math.random()%>></script>
<script>
    var errorType = "<%=request.getAttribute("error")%>";
    if (errorType === "verificationCode") {
        document.getElementById("verificationCodeError")
            .setAttribute("style", "display: block;");
    } else if (errorType === "username") {
        document.getElementById("usernameError")
            .setAttribute("style", "display: block;");
    } else if (errorType === "password") {
        document.getElementById("passwordError")
            .setAttribute("style", "display: block;");
    }
</script>
</body>
</html>
