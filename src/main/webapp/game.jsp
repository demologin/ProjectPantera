<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${step.title}</title>
    <style>
        body { font-family: Arial, sans-serif; line-height: 1.6; padding: 20px; background-color: #f0f2f5; }
        .container { max-width: 800px; margin: auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .answer-item { margin: 10px 0; padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
        a { text-decoration: none; color: #007bff; font-weight: bold; }
        a:hover { color: #0056b3; }
    </style>
</head>
<body>
<div class="container">
    <div style="text-align: right; color: #666; margin-bottom: 20px; font-size: 0.9em;">
        Игрок: <strong>${sessionScope.player.name}</strong> |
        Игр сыграно: <strong>${sessionScope.player.gamesPlayed}</strong>
    </div>

    <h1>${step.title}</h1>
    <p>${step.description}</p>
    <hr>

    <c:choose>
        <c:when test="${empty step.answers}">
            <h2 style="color: #dc3545;">КОНЕЦ ИГРЫ</h2>
            <a href="logic?id=1">Начать заново</a>
        </c:when>
        <c:otherwise>
            <c:forEach var="answer" items="${step.answers}">
                <div class="answer-item">
                    <a href="logic?id=${answer.nextStepId}">${answer.text}</a>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>