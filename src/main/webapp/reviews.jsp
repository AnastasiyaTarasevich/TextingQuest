<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
  <jsp:include page="links.jsp" />
</head>
<body>
<jsp:include page="header.jsp" />

<div class="container mt-5">
  <h1 class="text-center text-white">Отзывы о квестах</h1>

  <div class="row">
    <c:forEach var="review" items="${reviews}">
      <div class="col-md-4 mb-4">
        <div class="card shadow-lg">
          <div class="card-header">
            <h5 class="card-title">${review.name} (${review.userNickname})</h5>
            <span class="badge badge-warning text-dark">${review.rate} ⭐</span>
          </div>
          <div class="card-body">
            <c:if test="${not empty review.description}">
              <p class="card-text">${review.description}</p>
            </c:if>
          </div>
          <div class="card-footer text-muted">
            <small>Отзыв оставлен на квест: ${review.questName}</small>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>

  <div class="text-center mt-4">
    <form action="quests" method="get">
      <button class="btn btn-warning btn-lg" type="submit">Перейти к странице с квестами</button>
    </form>
  </div>
</div>

</body>

