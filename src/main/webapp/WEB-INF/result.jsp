<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Результат</title>
    <link rel="stylesheet" href="../static/style.css">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
</head>
<body>
    <a href="main-menu" class="btn btn-back">Вернуться в меню</a>
    <div class="container">
        <h1>
            <c:choose>
                <c:when test="${currentQuest.isVictory()}">
                    You WIN!
                </c:when>
                <c:otherwise>
                    You LOST!
                </c:otherwise>
            </c:choose>
        </h1>
        <p style="font-style: italic; margin: 20px 0;">
            <c:out value="${fn:escapeXml(currentQuest.text)}"/>
        </p>
        <button class="btn btn-continue" onclick="window.location='/start-quest'">Начать заново</button>
    </div>
</body>
</html>