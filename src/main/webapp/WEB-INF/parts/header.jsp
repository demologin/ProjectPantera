<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html data-bs-theme="light" lang="ru">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Pantera</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootswatch@5.3.3/dist/superhero/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Lato:wght@300;400;700&amp;display=swap">
    <link rel="stylesheet" href="assets/css/styles.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css">
</head>

<body class="d-flex flex-column min-vh-100">
<main>
    <header>
        <nav class="navbar navbar-expand-md bg-dark py-3" data-bs-theme="dark">
            <div class="container">
                <a class="navbar-brand d-flex align-items-center" href="/">
                    <h2 style="border-color: var(--bs-orange);color: var(--bs-orange);">JavaQuest</h2>
                </a>
                <button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-1"><span
                        class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navcol-1">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item"><a class="nav-link active" href="#"></a></li>
                        <li class="nav-item"><a class="nav-link" href="#"></a></li>
                        <li class="nav-item"><a class="nav-link" href="#"></a></li>
                        <li class="nav-item"><a class="nav-link" href="#"></a></li>
                    </ul>
                    <c:choose>
                        <c:when test="${not empty sessionScope.user}">
                            <ul class="navbar-nav ms-auto">
                                <li class="nav-item"><a class="nav-link active" href=".">Home</a></li>
                                <li class="nav-item"><a class="nav-link" href="statistics">Статистика</a></li>
                                <li class="nav-item"><a class="nav-link" href="list-quests">Квесты</a></li>
                                <c:if test="${sessionScope.user.role=='ADMIN'}">
                                    <li class="nav-item"><a class="nav-link" href="list-users">Пользователи</a></li>
                                </c:if>
                                <c:if test="${sessionScope.user.role=='ADMIN' || sessionScope.user.role=='GAMEDEV'}">
                                    <li class="nav-item"><a class="nav-link" href="create-quest">Создать квест</a></li>
                                </c:if>
                                <c:if test="${sessionScope.user.role=='ADMIN' || sessionScope.user.role=='MODERATOR'}">
                                    <li class="nav-item"><a class="nav-link" href="messages">Сообщения</a></li>
                                </c:if>
                            </ul>
                            <ul class="nav col-md-3 text-end">
                                <li>
                                    <a href="profile" class="btn btn-primary ms-md-2"
                                       style="background: var(--bs-orange);">Profile</a>
                                </li>
                                <li>
                                    <a href="logout" class="btn btn-danger ms-md-2"
                                       style="background: var(--bs-orange);">Logout</a>
                                </li>
                                <li>
                                    <h4>" "</h4>
                                </li>
                                <li>
                                    <h3 style="border-color: var(--bs-orange);color: var(--bs-orange);">${sessionScope.user.login}</h3>
                                </li>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <ul class="nav col-md-3 text-end">
                                <li>
                                    <a href="login" class="btn btn-primary ms-md-2"
                                       style="background: var(--bs-orange);">Login</a>
                                </li>
                                <li>
                                    <a href="signup" class="btn btn-primary ms-md-2"
                                       style="background: var(--bs-orange);">Sign-up</a>
                                </li>
                                <li>
                                    <h4 style="border-color: var(--bs-orange);color: var(--bs-orange);">${sessionScope.user.login}</h4>
                                </li>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </nav>
    </header>