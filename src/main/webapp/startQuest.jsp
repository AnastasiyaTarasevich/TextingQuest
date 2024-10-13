<%--
  Created by IntelliJ IDEA.
  User: sacha_tral
  Date: 10.10.2024
  Time: 13:34
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
        <c:if test="${empty previousAnswerDescription}">
            <h1 class="text-center mb-4">${currentChapter.title}</h1>

            <!-- Описание главы -->
            <p class="description mb-4">${currentChapter.content}</p>
        </c:if>

        <!-- Вопросы -->
        <c:if test="${not empty previousAnswerDescription}">
            <p>${previousAnswerDescription}</p>
        </c:if>
        <div class="text-end">
            <c:if test="${isIntroductoryChapter}">
                <form action="startQuest" method="post">
                    <button class="btn-lg btn-warning" type="submit">Перейти к следующей главе</button>
                </form>
            </c:if>
        </div>


        <c:if test="${!isIntroductoryChapter}">
            <c:if test="${not empty currentQuestion}">
                <h3>Вопрос: ${currentQuestion.question_text}</h3>

                <!-- Форма для отправки ответа -->
                <form action="startQuest" method="post">
                    <div class="d-flex justify-content-between">
                        <c:forEach var="answer" items="${currentQuestion.answers}">
                            <div class="flex-fill">
                                <button class="btn-lg btn-warning w-100" type="submit" name="answerId" value="${answer.id}">
                                        ${answer.answer_text}
                                </button>
                            </div>
                        </c:forEach>
                    </div>

                </form>
            </c:if>
            <c:if test="${empty currentQuestion}">
                <p>Вопросов больше нет в этой главе.</p>
            </c:if>
        </c:if>
    </div>
</div>
</body>

