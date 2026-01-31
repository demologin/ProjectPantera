<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <nav class="navbar navbar-expand-lg fixed-top bg-dark navbar-dark">
        <div class="container"><a class="navbar-brand"
                                  href="${pageContext.request.contextPath}/"><strong>QUESTS</strong></a>
            <button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-1" type="button"><span
                    class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse" id="navcol-1">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link <c:if test="${pageContext.request.requestURI.contains('/home')}">active</c:if>"
                           href="${pageContext.request.contextPath}/home">Главная
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link <c:if test="${pageContext.request.requestURI.contains('/stats')}">active</c:if>"
                           href="${pageContext.request.contextPath}/stats">Статистика
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link <c:if test="${pageContext.request.requestURI.contains('/edit-quest')}">active</c:if>"
                           href="${pageContext.request.contextPath}/edit-quest">Создать/Редактировать квест
                        </a>
                    </li>

                    <c:if test="${empty sessionScope.user}">
                        <li class="nav-item">
                            <a class="nav-link <c:if test="${pageContext.request.requestURI.contains('/login')}">active</c:if>"
                               href="${pageContext.request.contextPath}/login">Вход
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link <c:if test="${pageContext.request.requestURI.contains('/register')}">active</c:if>"
                               href="${pageContext.request.contextPath}/register">Регистрация
                            </a>
                        </li>
                    </c:if>

                    <c:if test="${not empty sessionScope.user}">
                        <li>
                            <c:if test="${sessionScope.user.isAdmin()}">
                                <a class="nav-link <c:if test="${pageContext.request.requestURI.contains('/user-list')}">active</c:if>"
                                   href="${pageContext.request.contextPath}/user-list">Список пользователей
                                </a>
                            </c:if>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link <c:if test="${pageContext.request.requestURI.contains('/profile')}">active</c:if>"
                               href="${pageContext.request.contextPath}/profile"><b style="color: sandybrown">${sessionScope.user.login}</b>
                            </a>
                        </li>
                    </c:if>

                </ul>
            </div>
        </div>
    </nav>
</div>
</body>
</html>
