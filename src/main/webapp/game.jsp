<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${quest.title} - Игра</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            margin: 0;
            padding: 0;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .container {
            background-color: white;
            border-radius: 15px;
            padding: 40px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
            max-width: 800px;
            width: 90%;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #f0e6ff;
        }
        .quest-info {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 0.9em;
        }
        .player-info {
            background-color: #f0e6ff;
            padding: 10px 20px;
            border-radius: 10px;
            color: #764ba2;
            font-weight: bold;
        }
        .question {
            background-color: #f8f9fa;
            padding: 30px;
            border-radius: 10px;
            margin-bottom: 30px;
            font-size: 1.2em;
            line-height: 1.6;
            color: #333;
            border-left: 4px solid #764ba2;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }
        .options {
            display: flex;
            gap: 20px;
            justify-content: center;
        }
        .option-btn {
            flex: 1;
            background-color: #764ba2;
            color: white;
            border: none;
            padding: 20px 30px;
            border-radius: 10px;
            font-size: 1.1em;
            cursor: pointer;
            transition: all 0.3s;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
        .option-btn:hover {
            background-color: #5a3d7c;
            transform: translateY(-2px);
            box-shadow: 0 6px 12px rgba(0,0,0,0.15);
        }
        .stats {
            margin-top: 30px;
            display: flex;
            justify-content: center;
            gap: 30px;
            color: #666;
            font-size: 0.9em;
        }
        .stat-item {
            text-align: center;
        }
        .stat-value {
            font-weight: bold;
            color: #764ba2;
            font-size: 1.2em;
        }
        .back-button {
            text-align: center;
            margin-top: 20px;
        }
        .back-link {
            color: #999;
            text-decoration: none;
            font-size: 0.9em;
        }
        .back-link:hover {
            color: #764ba2;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <div class="quest-info">
            ${quest.genre} • Сложность:
            <c:forEach begin="1" end="5" var="i">
                <c:choose>
                    <c:when test="${i <= quest.difficultyLevel}">★</c:when>
                    <c:otherwise>☆</c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <div class="player-info">
            ${sessionScope.gameState.playerName} |
            Побед: ${sessionScope.gameState.wins} |
            Поражений: ${sessionScope.gameState.losses}
        </div>
    </div>

    <h2 style="text-align: center; color: #333; margin-bottom: 20px;">${quest.title}</h2>

    <div class="question">
        <c:out value="${question.text}"/>
    </div>

    <form action="${pageContext.request.contextPath}/game" method="post">
        <div class="options">
            <button type="submit" name="choice" value="1" class="option-btn">
                <c:out value="${question.option1}"/>
            </button>
            <button type="submit" name="choice" value="2" class="option-btn">
                <c:out value="${question.option2}"/>
            </button>
        </div>
    </form>

    <div class="stats">
        <div class="stat-item">
            <div class="stat-value">${question.id}</div>
            <div>Текущий шаг</div>
        </div>
        <div class="stat-item">
            <div class="stat-value">${sessionScope.gameState.gamesPlayed}</div>
            <div>Всего игр</div>
        </div>
    </div>

    <div class="back-button">
        <a href="${pageContext.request.contextPath}/selectQuest" class="back-link">← Выбрать другой квест</a>
    </div>
</div>
</body>
</html>