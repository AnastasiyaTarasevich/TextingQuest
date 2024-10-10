<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <jsp:include page="links.jsp" />
    <title>${quest.title}</title>
</head>
<body>
<jsp:include page="header.jsp" />

<div class="container text-white mt-5">
    <!-- Основной блок квеста -->
    <div class=" bg-dark text-light p-4 mb-4 shadow-lg">
        <h1 class="text-center mb-4">${quest.title}</h1>

        <!-- Изображение -->
        <div class="text-center">
            <img src="${pageContext.request.contextPath}/${quest.imagePath}"
                 alt="${quest.title}"
                 class="img-fluid rounded mb-4 quest-image" />
        </div>

        <!-- Пролог -->
        <p class="lead prologue fst-italic">${quest.prologue}</p>

        <!-- Описание квеста -->
        <p class="description">${quest.description}</p>

        <!-- Кнопка "Начать квест" -->
        <div class="text-center mt-5">
            <a href="startQuest?questId=${quest.id}" class="btn btn-lg btn-warning text-uppercase px-5 py-3">
                Начать квест
            </a>
        </div>
    </div>
</div>


</body>
