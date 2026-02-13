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

        .win {
            color: #2ecc71;
        }

        .loss {
            color: #e74c3c;
        }

        .btn-box {
            display: flex;
            flex-direction: column;
            gap: 10px;
            margin-top: 20px;
        }

        .btn {
            padding: 15px;
            border-radius: 10px;
            text-decoration: none;
            font-weight: bold;
            border: none;
            cursor: pointer;
            transition: 0.3s;
        }

        .btn-again {
            background: #27ae60;
            color: white;
        }

        .btn-new {
            background: #34495e;
            color: white;
        }

        .btn:hover {
            filter: brightness(1.2);
            transform: scale(1.02);
        }
    </style>
</head>
<body>
<div class="container">
    <c:set var="isWin" value="${step.title.toLowerCase().contains('победа')}" />
    <h1 class="${isWin ? 'win' : 'loss'}">${step.title}</h1>

    <img src="${pageContext.request.contextPath}/${sessionScope.player.avatarPath}" class="final-avatar">
    <h2>${sessionScope.player.name}</h2>
    <p>Завершено экспедиций: <strong>${sessionScope.player.gamesPlayed}</strong></p>

    <div style="margin: 20px 0; line-height: 1.6;">${step.description}</div>

    <div class="btn-box">
        <a href="${pageContext.request.contextPath}/logic?id=1" class="btn btn-again">ПОПРОБОВАТЬ СНОВА (ТЕМ ЖЕ ГЕРОЕМ)</a>
        <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-new">ИЗМЕНИТЬ ДАННЫЕ ИМЕНИ / АВАТАРА</a>
    </div>
</div>
</body>
</html>