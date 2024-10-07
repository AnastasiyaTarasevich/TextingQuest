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
    <jsp:include page="links.jsp" />
    <title>Login</title>
</head>
<jsp:include page="header.jsp" />
<body class="text-center">

<form action="${pageContext.request.contextPath}/login" method="post" class="text-white">
    <fieldset>

        <!-- Form Name -->
        <legend style="text-align: center;"><h2><b>Авторизируйтесь в системе</b></h2></legend><br>

        <!-- Text input-->
        <br>
        <div align="center">

            <label class="col-md-4 control-label">Введите логин</label>
            <br>
            <div class="col-md-4 inputGroupContainer">
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                    <input name="username" placeholder="Username" class="form-control"  type="text" minlength="4" required>
                </div>
            </div>

            <!-- Text input-->
            <br>
            <div class="form-group">
                <label class="col-md-4 control-label" > Введите пароль</label>
                <br>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input id="password" name="password" placeholder="Password" class="form-control"  type="password" minlength="8" required>
                    </div>
                </div>
            </div>

            <!-- Text input-->
            <br>

            <span id="password-error" style="color: red;"></span>
            <!-- Button -->
            <div class="form-group">
                <label class="col-md-4 control-label"></label>
                <div class="col-md-4"><br>
                    <input id="submit" type="submit" class="btn btn-dark" value="Войти" >
                </div>
                <br>



            </div>
        </div>
    </fieldset>


<c:if test="${param.error!=null}">
    <div style="color: red">
            <span>Email or password is not correct</span>
            <br>
    </div>
</c:if>


</body>

</html>
