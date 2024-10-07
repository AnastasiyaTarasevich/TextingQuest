<%--
  Created by IntelliJ IDEA.
  User: sacha_tral
  Date: 07.10.2024
  Time: 22:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <li><a href="#" class="nav-link px-2">Квесты</a></li>
        <li><a href="#" class="nav-link px-2">Отзывы</a></li>
        <li><a href="#" class="nav-link px-2">Персональные достижения</a></li>
      </ul>
    </div>

    <div class="col-2 text-end">
      <a href="${pageContext.request.contextPath}/login"> <button type="button" class="btn btn-outline-dark me-2 text-white">Login</button></a>
      <a href="${pageContext.request.contextPath}/registration"><button type="button" class="btn btn-dark">Sign-up</button></a>
    </div>
  </header>
</div>
