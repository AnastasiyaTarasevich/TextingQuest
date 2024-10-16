<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container">
  <header class="d-flex flex-wrap align-items-center justify-content-between py-3 mb-4 border-bottom">
    <div class="col-2">
      <a href="${pageContext.request.contextPath}/" class="d-inline-flex link-body-emphasis text-decoration-none">
        <img src="${pageContext.request.contextPath}/resources/photo_2024-10-04_12-03-14.jpg" alt="Logo" width="100" height="60">
      </a>
    </div>

    <div class="col-8 d-flex justify-content-center">
      <ul class="nav mb-2 justify-content-center mb-md-0">
        <li><a href="${pageContext.request.contextPath}/" class="nav-link px-2 link-secondary">Главная страница</a></li>
        <li><a href="${pageContext.request.contextPath}/quests" class="nav-link px-2">Квесты</a></li>
        <li><a href="${pageContext.request.contextPath}/reviews" class="nav-link px-2">Отзывы</a></li>
      </ul>
    </div>

    <div class="col-2 text-end">
      <!-- Проверка наличия атрибута "user" в сессии -->
      <c:choose>
        <c:when test="${not empty sessionScope.user}">
          <!-- Если пользователь авторизован, показываем кнопку логаут -->
          <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit" class="btn btn-outline-dark me-2 text-white">Logout</button>
          </form>
        </c:when>
        <c:otherwise>
          <!-- Если пользователь не авторизован, показываем кнопки логина и регистрации -->
          <a href="${pageContext.request.contextPath}/login">
            <button type="button" class="btn btn-outline-dark me-2 text-white">Login</button>
          </a>
          <a href="${pageContext.request.contextPath}/registration">
            <button type="button" class="btn btn-dark">Sign-up</button>
          </a>
        </c:otherwise>
      </c:choose>
    </div>
  </header>
</div>
