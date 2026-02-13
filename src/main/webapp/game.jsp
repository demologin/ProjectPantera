<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="step" type="com.javarush.trukhanova.entity.QuestStep"--%>
<%--@elvariable id="player" type="com.javarush.trukhanova.entity.Player"--%>
<html>
<head>
    <title>${step.title}</title>
    <style>
        *, *:before, *:after {
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Roboto, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background: #121212;
            color: #e0e0e0;
            display: flex;
            justify-content: center;
            min-height: 100vh;
        }
        .container {
            max-width: 800px;
            width: 95%;
            background: #1e1e1e;
            padding: 30px 40px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.5);
            border: 1px solid #333;
            margin: auto;
            display: flex;
            flex-direction: column;
        }
        .stats-panel {
            display: flex;
            align-items: center;
            justify-content: flex-end;
            gap: 15px;
            color: #888;
            margin-bottom: 20px;
            font-size: 0.85em;
            text-transform: uppercase;
            border-bottom: 1px solid #333;
            padding-bottom: 15px;
        }
        .player-avatar {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            border: 2px solid #4ca1af;
            object-fit: cover;
        }
        #timer-container {
            position: sticky;
            top: 0;
            z-index: 999;
            background-color: #1e1e1e;
            padding: 10px 0;
            margin-bottom: 20px;
            border-bottom: 2px solid #333;
        }
        #timer-info {
            display: flex;
            justify-content: space-between;
            font-size: 0.9em;
            margin-bottom: 8px;
            color: #4ca1af;
            font-weight: bold;
        }
        .progress-bar-bg {
            width: 100%;
            height: 10px;
            background: #121212;
            border-radius: 5px;
            overflow: hidden;
        }
        #progress-line {
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, #4ca1af, #e74c3c);
            transition: width 1s linear;
        }
        .timer-low {
            color: #ff4d4d !important;
            animation: pulse 1s infinite;
        }
        @keyframes pulse { 0% { opacity: 1; } 50% { opacity: 0.5; } 100% { opacity: 1; } }
        h1 {
            color: #ffffff;
            text-align: center;
            margin-top: 0;
        }
        .description {
            font-size: 1.1em;
            margin: 20px 0;
            color: #ccc;
        }
        .quest-image-container {
            width: 100%;
            text-align: center;
            margin-bottom: 25px;
            border-radius: 10px;
            overflow: hidden;
            border: 2px solid #333;
        }
        .quest-image {
            width: 100%;
            height: auto;
            display: block;
            transition: 0.5s;
        }
        .quest-image:hover {
            transform: scale(1.03);
        }
        .answers {
            display: flex;
            flex-direction: column;
            gap: 15px;
            margin-top: auto;
        }
        .answer-item {
            opacity: 0;
            transform: translateY(10px);
            transition: 0.5s;
        }
        .btn-choice {
            display: block;
            width: 100%;
            background: linear-gradient(135deg, #2c3e50, #4ca1af);
            color: white;
            padding: 16px 20px;
            border-radius: 10px;
            text-decoration: none;
            font-weight: 600;
            text-align: center;
            transition: 0.3s;
        }
        .btn-choice:hover {
            transform: scale(1.01);
            box-shadow: 0 6px 20px rgba(76, 161, 175, 0.3);
        }
        .btn-restart {
            background: linear-gradient(135deg, #8e2e2e, #c0392b);
        }
    </style>
</head>
<body>
<div class="container">
    <c:if test="${not empty step.answers}">
        <div id="timer-container">
            <div id="timer-info"><span>Время на решение</span><span><span id="seconds">20</span> сек.</span></div>
            <div class="progress-bar-bg"><div id="progress-line"></div></div>
        </div>
    </c:if>

    <div class="stats-panel">
        <div style="text-align: right;">
            <div>Игрок: <strong>${sessionScope.player.name}</strong></div>
            <div>Экспедиций: <strong>${sessionScope.player.gamesPlayed}</strong></div>
        </div>
        <c:if test="${not empty sessionScope.player.avatarPath}">
            <img src="${pageContext.request.contextPath}/${sessionScope.player.avatarPath}" class="player-avatar" alt="Avatar">
        </c:if>
    </div>

    <h1>${step.title}</h1>

    <c:if test="${not empty step.imagePath}">
        <div class="quest-image-container">
            <img src="${pageContext.request.contextPath}/${step.imagePath}" class="quest-image" alt="Quest Image">
        </div>
    </c:if>

    <div class="description"><p>${step.description}</p></div>
    <hr style="border: 0; border-top: 1px solid #333; margin: 20px 0;">

    <div class="answers">
        <c:choose>
            <c:when test="${empty step.answers}">
                <div style="text-align: center;">
                    <h2 style="color: #e74c3c; letter-spacing: 2px;">КОНЕЦ ПУТИ</h2>
                    <div class="answer-item" style="opacity: 1; transform: none;">
                        <a href="${pageContext.request.contextPath}/index.jsp" class="btn-choice btn-restart">ВЕРНУТЬСЯ В МЕНЮ</a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="answer" items="${step.answers}">
                    <div class="answer-item">
                        <a href="${pageContext.request.contextPath}/logic?id=${answer.nextStepId}" class="btn-choice">${answer.text}</a>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        const secondsText = document.getElementById('seconds');
        const progressLine = document.getElementById('progress-line');
        if (secondsText) {
            let timeLeft = 20;
            const timerId = setInterval(() => {
                timeLeft--;
                secondsText.innerText = timeLeft;
                progressLine.style.width = (timeLeft / 20 * 100) + "%";
                if (timeLeft <= 5) progressLine.style.background = "#ff4d4d";
                if (timeLeft <= 0) {
                    clearInterval(timerId);
                    window.location.href = "${pageContext.request.contextPath}/logic?id=99";
                }
            }, 1000);
        }
        document.querySelectorAll('.answer-item').forEach((item, i) => {
            setTimeout(() => { item.style.opacity = '1'; item.style.transform = 'translateY(0)'; }, i * 150);
        });
    });
</script>
</body>
</html>