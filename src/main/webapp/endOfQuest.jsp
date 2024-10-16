<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <jsp:include page="links.jsp" />
</head>
<body>
<jsp:include page="header.jsp" />

<div class="container text-white mt-5">
    <div class="bg-dark text-light p-4 mb-4 shadow-lg">
        <h1 class="text-center mb-4">Квест завершен!</h1>
        <c:if test="${not empty previousAnswerDescription}">
            <p>${previousAnswerDescription}</p>
        </c:if>
    </div>

    <c:choose>
        <c:when test="${not empty message}">
            <div class="bg-success text-light p-4 shadow-lg mt-5">
                <h2 class="text-center">Спасибо за ваш отзыв!</h2>
                <p class="text-center">${message}</p>
            </div>
        </c:when>

        <c:when test="${not empty errors}">
            <div class="bg-danger text-light p-4 shadow-lg mt-5">
                <h2 class="text-center">Ошибка при добавлении отзыва!</h2>
                <ul>
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </div>
            <jsp:include page="reviewForm.jsp" />
        </c:when>

        <c:otherwise>
            <c:if test="${not empty error}">
                <div class="bg-danger text-light p-4 shadow-lg mt-5">
                    <h2 class="text-center">Ошибка!</h2>
                    <p class="text-center">${error}</p>
                </div>
            </c:if>
            <jsp:include page="reviewForm.jsp" />
        </c:otherwise>
    </c:choose>

    <div class="text-center">
        <form action="quests" method="get">
            <button class="btn-lg btn-warning" type="submit">Перейти к странице с квестами</button>
        </form>
    </div>
</div>
</body>
