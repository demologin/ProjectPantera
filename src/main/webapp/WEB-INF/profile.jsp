<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/static/bootstrap/css/bootstrap.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Hero-Features-icons.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Bold-BS4-Full-Page-Image-Header.css'/>">

<html>
<head>
    <title>Profile</title>
</head>

<body class="bg-primary-subtle">
<div class="container" style="max-width: 500px; margin-top: 100px">
    <div class="card">
        <div class="card-header">
            <h3>Ваш профиль</h3>
        </div>
        <div class="card-body">
            <dl class="row">
                <dt class="col-4">ID</dt>
                <dd class="col-8">${sessionScope.user.id}</dd>

                <dt class="col-4">Имя</dt>
                <dd class="col-8">${sessionScope.user.login}</dd>

                <dt class="col-4">Email</dt>
                <dd class="col-8">${sessionScope.user.email}</dd>

                <dt class="col-4">Роль</dt>
                <dd class="col-8">${sessionScope.user.role}</dd>
            </dl>

            <div class="d-flex justify-content-center">
                <a href="<c:url value='/logout'/>" class="btn btn-primary w-50">
                    Выйти
                </a>
            </div>
        </div>
    </div>
</div>
</body>>

</html>
