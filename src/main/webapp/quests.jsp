<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <jsp:include page="links.jsp" />
    <title>Список квестов</title>

</head>
<body>
<jsp:include page="header.jsp" />

<h1 class="text-white" align="center">Список квестов</h1>
<div class="album py-5 bg-body-tertiary">
    <div class="container">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3"> <!-- Здесь определяем количество колонок -->
            <c:if test="${not empty quests}">
                <c:forEach var="quest" items="${quests}">
                    <div class="col"> <!-- Каждая карточка квеста будет находиться в отдельной колонке -->
                        <a href="${pageContext.request.contextPath}/quest?questId=${quest.id}" class="card shadow-sm">
                            <img class="bd-placeholder-img card-img-top" width="100%" height="225" src="${pageContext.request.contextPath}/${quest.imagePath}" alt="${quest.title}" role="img" focusable="false">
                            <div class="card-body">
                                <h2 class="card-title">${quest.title}</h2>
                                <p class="card-text">${quest.prologue}</p>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty quests}">
                <p>Нет доступных квестов.</p>
            </c:if>
        </div>
    </div>
</div>
</body>
