<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="step" type="com.javarush.trukhanova.entity.QuestStep"--%>
<%--@elvariable id="player" type="com.javarush.trukhanova.entity.Player"--%>
<html>
<head>
    <title>Финал — ${step.title}</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #121212;
            color: #e0e0e0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }
        .container {
            max-width: 600px;
            width: 90%;
            background: #1e1e1e;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.5);
            text-align: center;
            border: 1px solid #333;
        }
        .final-avatar {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            border: 3px solid #4ca1af;
            margin-bottom: 15px;
            object-fit: cover;
        }
        h1 {
            font-size: 2.2em;
            margin-bottom: 10px;
            text-transform: uppercase;
        }
        .win { color: #2ecc71; }
        .loss { color: #e74c3c; }
        .description {
            font-size: 1.1em;
            color: #ccc;
            margin: 20px 0;
            line-height: 1.6;
        }
        .stats {
            color: #888;
            margin-bottom: 30px;
            font-size: 0.9em;
        }
        .btn-restart {
            display: block;
            width: 100%;
            background: linear-gradient(135deg, #27ae60, #2ecc71);
            color: white;
            padding: 16px;
            border-radius: 10px;
            font-weight: bold;
            border: none;
            cursor: pointer;
            font-size: 1.1em;
            transition: 0.3s;
        }
        .btn-restart:hover { transform: scale(1.02); filter: brightness(1.1); }
    </style>
</head>
<body>
<div class="container">
    <c:set var="isWin" value="${step.title.toLowerCase().contains('победа')}" />
    <h1 class="${isWin ? 'win' : 'loss'}">${step.title}</h1>

    <div class="player-card">
        <c:if test="${not empty sessionScope.player.avatarPath}">
            <img src="${pageContext.request.contextPath}/${sessionScope.player.avatarPath}" alt="Avatar" class="final-avatar">
        </c:if>
        <h2 style="margin: 5px 0;">${sessionScope.player.name}</h2>
        <div class="stats">Завершено экспедиций: <strong style="color: #4ca1af;">${sessionScope.player.gamesPlayed}</strong></div>
    </div>

    <div class="description"><p>${step.description}</p></div>

    <form action="${pageContext.request.contextPath}/index.jsp" method="get">
        <button type="submit" class="btn-restart">К ВВОДУ ДАННЫХ</button>
    </form>
</div>
</body>
</html>