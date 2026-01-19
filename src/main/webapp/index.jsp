<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <!--suppress HtmlDeprecatedTag -->
    <title>Текстовые квесты</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<div class="layout">
    <c:set var="questActive" value="${not empty sessionScope.quest}" />
    <!-- ================= ЛЕВОЕ МЕНЮ ================= -->
    <div class="menu ${questActive ? 'menu-disabled' : ''}">

        <!-- ===== ИНФОРМАЦИЯ О ПОЛЬЗОВАТЕЛЕ ===== -->

        <c:if test="${empty sessionScope.user}">
            <p><b>Пользователь:</b> Гость</p>
        </c:if>

        <c:if test="${not empty sessionScope.user}">
            <p>
                <b>Пользователь:</b>
                    ${sessionScope.user.login}
            </p>
        </c:if>

        <c:if test="${not empty sessionScope.user and sessionScope.user.admin}">
            <p><b>Администратор</b></p>
        </c:if>

        <hr>

        <h3>Меню</h3>

        <a href="${pageContext.request.contextPath}/">Главная</a>
        <a href="${pageContext.request.contextPath}/quests">Выбрать квест</a>
        <a href="statistics">Статистика</a>

        <c:if test="${not empty sessionScope.user and sessionScope.user.admin}">
            <a href="${pageContext.request.contextPath}/admin">Админка</a>
        </c:if>

        <hr>

        <c:if test="${empty sessionScope.user}">
            <a href="${pageContext.request.contextPath}/login">Войти</a>
        </c:if>

        <c:if test="${not empty sessionScope.user}">
            <a href="${pageContext.request.contextPath}/logout">Выйти</a>
        </c:if>

    </div>

    <!-- ================= ЦЕНТРАЛЬНАЯ ОБЛАСТЬ ================= -->
    <div class="content">

        <c:if test="${not empty requestScope.view}">
            <jsp:include page="${requestScope.view}"/>
        </c:if>

        <c:if test="${empty requestScope.view}">
            <h2>Добро пожаловать в текстовые квесты</h2>
            <a href="${pageContext.request.contextPath}/quests">Выбрать квест</a>
        </c:if>

    </div>

</div>

</body>
</html>