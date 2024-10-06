<%--
  Created by IntelliJ IDEA.
  User: sacha_tral
  Date: 06.10.2024
  Time: 16:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<br>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="nickname"> NickName:
        <input type="text" name="nickname" id="nickname">
    </label>
    <br>
    <br>
    <label for="password"> Password:
    <input type="password" name="password" id="password" required>
</label>
<br>
<br>

    <button type="submit"> Login</button>
    <a href ="${pageContext.request.contextPath}/registration">
        <button type="button">Sign-up</button>
    </a>
</form>

<c:if test="${param.error!=null}">
    <div style="color: red">

            <span>Email or password is not correct</span>
            <br>

    </div>
</c:if>
</body>
</html>
