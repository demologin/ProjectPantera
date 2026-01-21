<%--
  Created by IntelliJ IDEA.
  User: ZybinAV
  Date: 15.01.2026
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="/WEB-INF/jsp/common/header.jsp"/>

<!DOCTYPE html>
<html>
<head>
    <title>Результат интервью</title>
    <style>
        .passed {
            color: green;
            font-weight: bold;
        }

        .failed {
            color: red;
            font-weight: bold;
        }

        .actions {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<h2>Результат теста</h2>

<p>
    <strong>Темы теста:</strong>
    ${topics}
</p>

<p>
    <strong>Результат:</strong>
    ${correct} из ${total} правильных ответов
</p>

<c:choose>
    <c:when test="${passed}">
        <p class="passed">
            Поздравляем! Вы успешно прошли тест 🎉
        </p>
        <p>
            Оффер уже летит к вам… ну, по крайней мере моральный 😄
        </p>
    </c:when>
    <c:otherwise>
        <p class="failed">
            К сожалению, тест не пройден
        </p>
        <p>
            Но это учебный проект, так что можно просто попробовать ещё раз.
        </p>
    </c:otherwise>
</c:choose>

<div class="actions">
    <a href="${pageContext.request.contextPath}/home">
        На главную
    </a>
    |
    <a href="${pageContext.request.contextPath}/test/settings">
        Пройти новый тест
    </a>
</div>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
</body>
</html>
