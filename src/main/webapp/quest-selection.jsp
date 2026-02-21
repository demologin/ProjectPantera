<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Выберите приключение</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            margin: 0;
            padding: 20px;
            min-height: 100vh;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
        }
        h1 {
            text-align: center;
            color: white;
            margin-bottom: 40px;
            font-size: 2.5em;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
        }
        .quests-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
            gap: 30px;
            padding: 20px;
        }
        .quest-card {
            background: white;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            transition: transform 0.3s, box-shadow 0.3s;
            cursor: pointer;
        }
        .quest-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 40px rgba(0,0,0,0.3);
        }
        .quest-header {
            height: 150px;
            background-size: cover;
            background-position: center;
            position: relative;
        }
        .quest-header::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            height: 50px;
            background: linear-gradient(to top, white, transparent);
        }
        .quest-content {
            padding: 20px;
        }
        .quest-title {
            font-size: 1.5em;
            color: #333;
            margin-bottom: 10px;
        }
        .quest-meta {
            display: flex;
            gap: 15px;
            margin-bottom: 15px;
            font-size: 0.9em;
        }
        .quest-genre {
            background: #764ba2;
            color: white;
            padding: 3px 10px;
            border-radius: 15px;
        }
        .quest-difficulty {
            display: flex;
            align-items: center;
            gap: 5px;
        }
        .difficulty-stars {
            color: #ffc107;
        }
        .quest-description {
            color: #666;
            line-height: 1.6;
            margin-bottom: 20px;
        }
        .quest-stats {
            display: flex;
            justify-content: space-between;
            align-items: center;
            color: #999;
            font-size: 0.9em;
            border-top: 1px solid #eee;
            padding-top: 15px;
        }
        .play-button {
            background: #764ba2;
            color: white;
            border: none;
            padding: 8px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 0.9em;
            transition: background 0.3s;
        }
        .play-button:hover {
            background: #5a3d7c;
        }
        .player-info {
            background: rgba(255,255,255,0.1);
            color: white;
            padding: 15px;
            border-radius: 10px;
            margin-bottom: 30px;
            text-align: center;
            backdrop-filter: blur(5px);
        }
    </style>
</head>
<body>
<div class="container">
    <div class="player-info">
        <h2>Добро пожаловать, ${sessionScope.gameState.playerName}!</h2>
        <p>Сыграно игр: ${sessionScope.gameState.gamesPlayed} |
            Побед: ${sessionScope.gameState.wins} |
            Поражений: ${sessionScope.gameState.losses}</p>
    </div>

    <h1>Выберите свое приключение</h1>

    <div class="quests-grid">
        <c:forEach var="quest" items="${quests}">
            <div class="quest-card" onclick="selectQuest('${quest.id}')">
                <div class="quest-header" style="background-image: url('${quest.backgroundImage}')">
                    <!-- Background image -->
                </div>
                <div class="quest-content">
                    <h2 class="quest-title">${quest.title}</h2>
                    <div class="quest-meta">
                        <span class="quest-genre">${quest.genre}</span>
                        <span class="quest-difficulty">
                                Сложность:
                                <span class="difficulty-stars">
                                    <c:forEach begin="1" end="5" var="i">
                                        <c:choose>
                                            <c:when test="${i <= quest.difficultyLevel}">★</c:when>
                                            <c:otherwise>☆</c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </span>
                            </span>
                    </div>
                    <p class="quest-description">${quest.description}</p>
                    <div class="quest-stats">
                        <span>👥 Прохождений: ${questStats[quest.id]}</span>
                        <button class="play-button" onclick="selectQuest('${quest.id}')">Играть</button>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<form id="questForm" action="${pageContext.request.contextPath}/selectQuest" method="post" style="display: none;">
    <input type="hidden" name="questId" id="selectedQuestId">
</form>

<script>
    function selectQuest(questId) {
        document.getElementById('selectedQuestId').value = questId;
        document.getElementById('questForm').submit();
    }
</script>
</body>
</html>