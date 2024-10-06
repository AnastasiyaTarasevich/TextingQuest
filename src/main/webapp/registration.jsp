<%--
  Created by IntelliJ IDEA.
  User: sacha_tral
  Date: 04.10.2024
  Time: 12:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Registration</title>

</head>
<body>
<form action="${pageContext.request.contextPath}/registration" method="post">
    <label for="nickname"> NickName:
        <input type="text" name="nickname" id="nickname">
    </label>
    <br>
    <label for="password"> Password:
        <input type="password" name="password" id="password">
    </label>
    <br>
    <br>
    <label for="email"> Email:
        <input type="text" name="email" id="email">
    </label>
    <br>
    <br>


    <input type="submit" value="send">
</form>

<c:if test="${not empty requestScope.errors}">
    <div style="color: red">
        <c:forEach var ="error" items="${requestScope.errors}">
            <span>${error.message}</span>
            <br>
        </c:forEach>
    </div>
</c:if>
</body>
</html>
