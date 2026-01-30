<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Пролог</title>
    <link rel="stylesheet" href="../static/style.css">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
</head>
<body>
    <a href="main-menu" class="btn btn-back">Вернуться в меню</a>
    <h1 class="quest-title">
        <c:out value="${currentQuest.title}"/>
    </h1>
    <div class="container">
        <h2>Пролог</h2>
        <p id="prologue-text" style="line-height: 1.8; text-align: left; min-height: 100px;"></p>
        <button id="start-btn" class="btn btn-continue" style="visibility: hidden; margin-top: 20px;" onclick="window.location='/start-quest'">Начать</button>
        <div id="raw-text" style="display: none;">${fn:escapeXml(currentQuest.prologue)}</div>
    </div>
    <script>
        const fullText = document.getElementById("raw-text").innerText;
        let charIndex = 0;
        const speed = 20; // Скорость появления букв (мс)

        function typeText() {
            if (charIndex < fullText.length) {
                let char = fullText.charAt(charIndex);
                if (char === '\n') {
                    document.getElementById("prologue-text").innerHTML += "<br>";
                } else {
                    document.getElementById("prologue-text").innerHTML += char;
                }
                charIndex++;
                setTimeout(typeText, speed);
            } else {
                document.getElementById("start-btn").style.visibility = "visible";
            }
        }

        window.onload = typeText;
    </script>
</body>
</html>