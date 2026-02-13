<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="step" type="com.javarush.trukhanova.entity.QuestStep"--%>
<html>
<head>
    <title>${step.title}</title>
    <style>
        *, *:before, *:after {
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', sans-serif;
            background: #121212;
            color: #e0e0e0;
            display: flex;
            justify-content: center;
            min-height: 100vh;
            margin: 0;
            padding: 20px;
        }

        .container {
            max-width: 800px;
            width: 95%;
            background: #1e1e1e;
            padding: 30px 40px;
            border-radius: 15px;
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
            background: #1e1e1e;
            padding: 10px 0;
            border-bottom: 2px solid #333;
            margin-bottom: 20px;
        }

        #timer-info {
            display: flex;
            justify-content: space-between;
            font-weight: bold;
            color: #4ca1af;
        }

        .progress-bar-bg {
            width: 100%;
            height: 10px;
            background: #121212;
            border-radius: 5px;
            overflow: hidden;
            margin-top: 5px;
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

        @keyframes pulse {
            0% {
                opacity: 1;
            }
            50% {
                opacity: 0.5;
            }
            100% {
                opacity: 1;
            }
        }

        .btn-choice {
            display: block;
            background: linear-gradient(135deg, #2c3e50, #4ca1af);
            color: white;
            padding: 16px;
            border-radius: 10px;
            text-decoration: none;
            text-align: center;
            margin-bottom: 10px;
            transition: 0.3s;
        }

        .btn-choice:hover {
            transform: scale(1.01);
            filter: brightness(1.2);
        }

        .answer-item {
            opacity: 0;
            transform: translateY(10px);
            transition: 0.5s;
        }
    </style>
</head>
<body>
<div class="container">
    <c:if test="${not empty step.answers}">
        <div id="timer-container">
            <div id="timer-info"><span>Осталось времени</span><span><span id="seconds">20</span> сек.</span></div>
            <div class="progress-bar-bg"><div id="progress-line"></div></div>
        </div>
    </c:if>

    <div class="stats-panel">
        <div style="text-align: right;">
            Игрок: <strong>${sessionScope.player.name}</strong> | Экспедиций: <strong>${sessionScope.player.gamesPlayed}</strong>
        </div>
        <img src="${pageContext.request.contextPath}/${sessionScope.player.avatarPath}" class="player-avatar" alt="Avatar">
    </div>

    <h1>${step.title}</h1>
    <c:if test="${not empty step.imagePath}">
        <div style="text-align: center; margin-bottom: 20px;">
            <img src="${pageContext.request.contextPath}/${step.imagePath}" style="max-width: 100%; border-radius: 10px; border: 2px solid #333;">
        </div>
    </c:if>

    <div class="description">${step.description}</div>
    <hr style="border: 0; border-top: 1px solid #333; margin: 20px 0;">

    <div class="answers">
        <c:forEach var="answer" items="${step.answers}">
            <div class="answer-item">
                <a href="${pageContext.request.contextPath}/logic?id=${answer.nextStepId}" class="btn-choice">${answer.text}</a>
            </div>
        </c:forEach>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        const secondsText = document.getElementById('seconds');
        const progressLine = document.getElementById('progress-line');
        let timerId = null;

        if (secondsText) {
            let timeLeft = 20;
            timerId = setInterval(() => {
                timeLeft--;
                secondsText.innerText = timeLeft;
                progressLine.style.width = (timeLeft / 20 * 100) + "%";
                if (timeLeft <= 5) secondsText.parentElement.classList.add('timer-low');
                if (timeLeft <= 0) {
                    clearInterval(timerId);
                    window.location.href = "${pageContext.request.contextPath}/logic?id=99";
                }
            }, 1000);
        }

        document.querySelectorAll('.btn-choice').forEach(btn => {
            btn.addEventListener('click', () => { if(timerId) clearInterval(timerId); });
        });

        document.querySelectorAll('.answer-item').forEach((el, i) => {
            setTimeout(() => { el.style.opacity = '1'; el.style.transform = 'translateY(0)'; }, i * 150);
        });
    });
</script>
</body>
</html>