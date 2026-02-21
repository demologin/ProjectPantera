<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Результат игры</title>
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
            border-radius: 20px;
            padding: 50px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            max-width: 600px;
            width: 90%;
            text-align: center;
            position: relative;
            overflow: hidden;
        }
        .victory {
            color: #28a745;
            font-size: 80px;
            margin-bottom: 20px;
            animation: bounce 1s;
        }
        .defeat {
            color: #dc3545;
            font-size: 80px;
            margin-bottom: 20px;
            animation: shake 0.5s;
        }
        @keyframes bounce {
            0%, 20%, 50%, 80%, 100% {transform: translateY(0);}
            40% {transform: translateY(-30px);}
            60% {transform: translateY(-15px);}
        }
        @keyframes shake {
            0%, 100% {transform: translateX(0);}
            10%, 30%, 50%, 70%, 90% {transform: translateX(-10px);}
            20%, 40%, 60%, 80% {transform: translateX(10px);}
        }
        .message {
            font-size: 28px;
            margin-bottom: 30px;
            color: #333;
            font-weight: bold;
        }
        .final-message {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
            font-size: 18px;
            line-height: 1.6;
        }
        .question {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
            font-size: 16px;
            color: #666;
            border-left: 4px solid #764ba2;
        }
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            margin-bottom: 40px;
        }
        .stat-box {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 10px;
        }
        .stat-label {
            color: #666;
            font-size: 14px;
            margin-bottom: 5px;
        }
        .stat-value {
            color: #764ba2;
            font-size: 24px;
            font-weight: bold;
        }
        .buttons {
            display: flex;
            gap: 20px;
            justify-content: center;
        }
        .btn {
            flex: 1;
            padding: 15px 30px;
            border: none;
            border-radius: 10px;
            font-size: 16px;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
            display: inline-block;
            font-weight: bold;
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(102, 126, 234, 0.4);
        }
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background: #5a6268;
            transform: translateY(-2px);
        }
        .btn-success {
            background: #28a745;
            color: white;
        }
        .btn-success:hover {
            background: #218838;
            transform: translateY(-2px);
        }
    </style>
</head>
<body>
<div class="container">
    <c:choose>
        <c:when test="${sessionScope.gameState.victory}">
            <div class="victory">🏆</div>
            <div class="message">ПОБЕДА!</div>
        </c:when>
        <c:otherwise>
            <div class="defeat">💀</div>
            <div class="message">ПОРАЖЕНИЕ</div>
        </c:otherwise>
    </c:choose>

    <div class="final-message">
        <c:out value="${finalMessage}"/>
    </div>

    <div class="question">
        <c:out value="${question.text}"/>
    </div>

    <div class="stats-grid">
        <div class="stat-box">
            <div class="stat-label">Всего игр</div>
            <div class="stat-value">${sessionScope.gameState.gamesPlayed}</div>
        </div>
        <div class="stat-box">
            <div class="stat-label">Победы</div>
            <div class="stat-value">${sessionScope.gameState.wins}</div>
        </div>
        <div class="stat-box">
            <div class="stat-label">Поражения</div>
            <div class="stat-value">${sessionScope.gameState.losses}</div>
        </div>
    </div>

    <div class="buttons">
        <form action="${pageContext.request.contextPath}/restart" method="post" style="flex: 1;">
            <button type="submit" class="btn btn-success">🔄 Играть снова</button>
        </form>
        <a href="${pageContext.request.contextPath}/selectQuest" class="btn btn-primary">🎮 Выбрать другой квест</a>
        <a href="${pageContext.request.contextPath}/start" class="btn btn-secondary">👤 Новый игрок</a>
    </div>
</div>
</body>
</html>