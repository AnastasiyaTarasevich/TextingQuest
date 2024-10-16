<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <jsp:include page="links.jsp" />
</head>
<body>
<jsp:include page="header.jsp" />
<div class="container text-white mt-5">
    <div class="bg-dark text-light p-4 mb-4 shadow-lg">
        <c:if test="${isNewChapter}">
            <h1 class="text-center mb-4">${newChapter.title}</h1>
            <p id="chapter-description" class="description mb-4">${newChapter.content}</p>
        </c:if>
        <c:if test="${empty previousAnswerDescription}">
            <h1 class="text-center mb-4">${currentChapter.title}</h1>
            <p id="chapter-description" class="description mb-4">${currentChapter.content}</p>
        </c:if>

        <c:if test="${not empty previousAnswerDescription}">
            <p id="previous-answer-description">${previousAnswerDescription}</p>
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
<script>
    document.addEventListener("DOMContentLoaded", function() {
        function formatTextWithQuotes(text) {
            // Регулярное выражение для поиска цитат
            const regex = /([\p{L} ]+): «([^»]+)»/gu;
            let formattedText = '';
            let lastEnd = 0;

            // Используем метод matchAll для получения всех совпадений
            const matches = text.matchAll(regex);

            for (const match of matches) {
                // Добавляем текст до начала цитаты
                formattedText += text.slice(lastEnd, match.index);
                const character = match[1].trim(); // Удаляем лишние пробелы
                const quote = match[2].trim(); // Удаляем лишние пробелы
                formattedText += `<p style="margin: 10px 0; font-style: italic;">`+character+`: `+quote+`</p>`;
                lastEnd = match.index + match[0].length;
            }
            // Добавляем оставшуюся часть текста после последней цитаты
            formattedText += text.slice(lastEnd);

            return formattedText;
        }

        const chapterDescription = document.getElementById("chapter-description");
        const previousAnswerDescription = document.getElementById("previous-answer-description");

        // Проверяем, что элементы существуют и выводим их содержимое
        console.log('Chapter Description:', chapterDescription ? chapterDescription.innerHTML : 'Not found');
        console.log('Previous Answer Description:', previousAnswerDescription ? previousAnswerDescription.innerHTML : 'Not found');

        if (chapterDescription) {
            chapterDescription.innerHTML = formatTextWithQuotes(chapterDescription.innerHTML);
            console.log('Formatted Chapter Description:', chapterDescription.innerHTML);
        }

        if (previousAnswerDescription) {
            previousAnswerDescription.innerHTML = formatTextWithQuotes(previousAnswerDescription.innerHTML);
            console.log('Formatted Previous Answer Description:', previousAnswerDescription.innerHTML);
        }
    });
</script>
