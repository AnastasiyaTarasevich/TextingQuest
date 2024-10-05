<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="index.css">
    <title>Questomania</title>
</head>
<body>
<div class="container">
    <header class="d-flex flex-wrap align-items-center justify-content-between py-3 mb-4 border-bottom">
        <!-- Logo section -->
        <div class="col-2">
            <a href="${pageContext.request.contextPath}/" class="d-inline-flex link-body-emphasis text-decoration-none">
                <img src="${pageContext.request.contextPath}/resources/photo_2024-10-04_12-03-14.jpg" alt="Logo" width="100" height="60">
            </a>
        </div>

        <!-- Links section (centered) -->
        <div class="col-8 d-flex justify-content-center">
            <ul class="nav mb-2 justify-content-center mb-md-0">
                <li><a href="#" class="nav-link px-2 link-secondary">Главная страница</a></li>
                <li><a href="#" class="nav-link px-2">Квесты</a></li>
                <li><a href="#" class="nav-link px-2">Отзывы</a></li>
                <li><a href="#" class="nav-link px-2">Персональные достижения</a></li>
            </ul>
        </div>

        <!-- Buttons section (aligned to the right) -->
        <div class="col-2 text-end">
            <button type="button" class="btn btn-outline-primary me-2">Login</button>
            <a href="${pageContext.request.contextPath}/registration.jsp"><button type="button" class="btn btn-primary">Sign-up</button></a>
        </div>
    </header>
</div>
<br/>
</body>
</html>
