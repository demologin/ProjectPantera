<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header style="display: flex; justify-content: space-between; align-items: center;">
    <div>
        <a href="${pageContext.request.contextPath}/home">
            <strong>Java Interview Trainer</strong>
        </a>

        |

        <a href="${pageContext.request.contextPath}/home">Главная</a>
        <c:if test="${not empty currentUser}">
            |
            <a href="${pageContext.request.contextPath}/profile">Профиль</a>
            |
            <a href="${pageContext.request.contextPath}/test/settings">Тесты</a>
        </c:if>
        <c:if test="${not empty currentUser && currentUser.role == 'ADMIN'}">
            |
            <a href="${pageContext.request.contextPath}/admin">Админка</a>
        </c:if>
    </div>
    <div>
        <c:if test="${not empty currentUser}">
            <form method="post"
                  action="${pageContext.request.contextPath}/logout"
                  style="display: inline;">
                <span>${currentUser.nickname}</span>
                <button type="submit">Выйти</button>
            </form>
        </c:if>
        <c:if test="${empty currentUser}">
            <a href="${pageContext.request.contextPath}/login">Войти</a>
            |
            <a href="${pageContext.request.contextPath}/register">Регистрация</a>
        </c:if>

    </div>
</header>

<hr>