<%--
  Created by IntelliJ IDEA.
  User: sacha_tral
  Date: 13.10.2024
  Time: 22:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <jsp:include page="links.jsp" />

</head>
<body>
<jsp:include page="header.jsp" />

<div class="container text-white mt-5">
    <!-- Заголовок текущей главы -->
    <div class=" bg-dark text-light p-4 mb-4 shadow-lg">
        <!-- Вопросы -->

        <h1 class="text-center mb-4">Квест завершен!</h1>
        <c:if test="${not empty previousAnswerDescription}">
            <p>${previousAnswerDescription}</p>
        </c:if>
        <form action="quests" method="get">
            <button class="btn-lg btn-warning" type="submit">Перейти к странице с квестами</button>
        </form>

    </div>
</div>
</body>