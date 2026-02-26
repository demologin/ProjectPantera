<%--
  Created by IntelliJ IDEA.
  User: ushan
  Date: 26.02.2026
  Time: 1:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>🚀 Операция «Кеплер-7»</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<header>
    <h1>🚀 Операция «Кеплер-7»</h1>
    <%-- Показываем имя игрока и статистику, если сессия есть --%>
    <c:if test="${not empty sessionScope.gameSession}">
        <div class="player-info">
            Пилот: <span>${sessionScope.gameSession.playerName}</span>
            &nbsp;|&nbsp;
            Игр сыграно: <span>${sessionScope.gameSession.gamesPlayed}</span>
            &nbsp;|&nbsp;
            Побед: <span>${sessionScope.gameSession.gamesWon}</span>
        </div>
    </c:if>
</header>
