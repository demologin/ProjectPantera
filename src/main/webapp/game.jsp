<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${step.title}</title>
    <style>
        /* Глобальный сброс */
        *, *:before, *:after {
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
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
            height: auto;
            min-height: min-content;
        }

        #timer-container {
            position: -webkit-sticky;
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
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .progress-bar-bg {
            width: 100%;
            height: 10px;
            background: #121212;
            border-radius: 5px;
            overflow: hidden;
            box-shadow: inset 0 1px 3px rgba(0,0,0,0.5);
        }

        #progress-line {
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, #4ca1af, #e74c3c);
            border-radius: 5px;
            transition: width 1s linear;
        }

        .timer-low {
            color: #ff4d4d !important;
            animation: pulse 1s infinite;
        }

        @keyframes pulse {
            0% { opacity: 1; }
            50% { opacity: 0.5; }
            100% { opacity: 1; }
        }

        /* Панель игрока */
        .stats-panel {
            text-align: right;
            color: #888;
            margin-bottom: 20px;
            font-size: 0.85em;
            text-transform: uppercase;
            letter-spacing: 1px;
            border-bottom: 1px solid #333;
            padding-bottom: 10px;
        }

        .stats-panel strong {
            color: #4ca1af;
        }

        h1 {
            color: #ffffff;
            margin-top: 0;
            font-size: 1.8em;
            text-align: center;
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
            background: #000;
        }

        .quest-image {
            width: 100%;
            height: auto;
            display: block;
            transition: transform 0.5s ease;
        }

        .quest-image:hover {
            transform: scale(1.03);
        }

        .answers {
            display: flex;
            flex-direction: column;
            gap: 15px;
            margin-top: auto;
            padding-top: 20px;
        }

        .answer-item {
            opacity: 0;
            transform: translateY(10px);
            transition: opacity 0.5s ease, transform 0.5s ease;
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
            box-shadow: 0 4px 15px rgba(0,0,0,0.2);
            border: 1px solid rgba(255,255,255,0.1);
            transition: all 0.3s ease;
        }

        .btn-choice:hover {
            background: linear-gradient(135deg, #3e5871, #5dbbc8);
            transform: scale(1.01);
            box-shadow: 0 6px 20px rgba(76, 161, 175, 0.3);
        }

        .btn-restart {
            background: linear-gradient(135deg, #8e2e2e, #c0392b);
        }

        hr {
            border: 0;
            border-top: 1px solid #333;
            margin: 20px 0;
        }
    </style>
</head>
<body>

<div class="container">
    <c:if test="${not empty step.answers}">
        <div id="timer-container">
            <div id="timer-info">
                <span>Время на принятие решения</span>
                <span><span id="seconds">20</span> сек.</span>
            </div>
            <div class="progress-bar-bg">
                <div id="progress-line"></div>
            </div>
        </div>
    </c:if>

    <div class="stats-panel">
        Игрок: <strong>${sessionScope.player.name}</strong> |
        Экспедиций: <strong>${sessionScope.player.gamesPlayed}</strong>
    </div>

    <h1>${step.title}</h1>

    <c:if test="${not empty step.imagePath}">
        <div class="quest-image-container">
            <img src="${pageContext.request.contextPath}/${step.imagePath}"
                 class="quest-image"
                 alt="${step.title}">
        </div>
    </c:if>

    <div class="description">
        <p>${step.description}</p>
    </div>

    <hr>

    <div class="answers">
        <c:choose>
            <c:when test="${empty step.answers}">
                <div style="text-align: center;">
                    <h2 style="color: #e74c3c; letter-spacing: 2px; margin-bottom: 20px;">ФИНАЛ</h2>
                    <div class="answer-item" style="opacity: 1; transform: none;">
                        <a href="index.jsp" class="btn-choice btn-restart">ПОКИНУТЬ ДЖУНГЛИ</a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="answer" items="${step.answers}">
                    <div class="answer-item">
                        <a href="logic?id=${answer.nextStepId}" class="btn-choice">
                                ${answer.text}
                        </a>
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
        const timerInfo = document.getElementById('timer-info');

        if (secondsText) {
            let timeLeft = 20;
            const totalTime = 20;

            const timerId = setInterval(() => {
                timeLeft--;
                if (secondsText) secondsText.innerText = timeLeft;

                let widthPercent = (timeLeft / totalTime) * 100;
                if (progressLine) progressLine.style.width = widthPercent + "%";

                if (timeLeft <= 5) {
                    if (timerInfo) timerInfo.classList.add('timer-low');
                    if (progressLine) progressLine.style.background = "#ff4d4d";
                }

                if (timeLeft <= 0) {
                    clearInterval(timerId);

                    window.location.href = "logic?id=99";
                }
            }, 1000);
        }

        const items = document.querySelectorAll('.answer-item');
        items.forEach((item, index) => {
            setTimeout(() => {
                item.style.opacity = '1';
                item.style.transform = 'translateY(0)';
            }, index * 150);
        });

        const links = document.querySelectorAll('.btn-choice');
        links.forEach(link => {
            link.addEventListener('click', function() {
                this.style.transform = 'scale(0.98)';
                document.querySelector('.container').style.opacity = '0.8';
            });
        });
    });
</script>

</body>
</html>