<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/static/bootstrap/css/bootstrap.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Hero-Features-icons.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Bold-BS4-Full-Page-Image-Header.css'/>">

<html>
<head>
    <title>Title</title>
</head>
<body class="bg-primary">
<div class="container" style="max-width: 400px; margin-top: 100px;">
  <div class="card">
    <div class="card-header">
      <h3 class="text-center">Вход в систему</h3>
    </div>
    <div class="card-body">
      <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
      </c:if>

      <form action="<c:url value='/login'/>" method="post">
        <div class="mb-3">
          <label for="email" class="form-label">Email</label>
          <input type="email"
                 class="form-control"
                 id="email"
                 name="email"
                 required>
        </div>
        <div class="mb-3">
          <label for="password" class="form-label">Пароль</label>
          <input type="password"
                 class="form-control"
                 id="password"
                 name="password"
                 required>
        </div>
        <button type="submit" class="btn btn-primary w-100">Войти</button>
      </form>

      <hr>
      <div class="text-center">
        <a href="<c:url value='/register'/>" class="btn btn-outline-secondary">
          Зарегистрироваться
        </a>
      </div>
    </div>
  </div>
</div>
</body>
</html>
