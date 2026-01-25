<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>${sessionScope.questName}</title>
    <style>
        body { font-family: 'Courier New', monospace; background-color: #1c1c1c; color: #ccc; display: flex; justify-content: center; min-height: 100vh; margin: 0; padding-top: 50px; }
        .game-container { background-color: #252525; width: 80%; max-width: 800px; padding: 40px; border-radius: 5px; box-shadow: 0 0 15px rgba(0,0,0,0.7); border: 1px solid #333; }

        .header { border-bottom: 1px solid #444; padding-bottom: 10px; margin-bottom: 20px; display: flex; justify-content: space-between; align-items: center; }
        .quest-title { color: #e60000; font-size: 1.2em; font-weight: bold; text-transform: uppercase; }
        .player-name { font-size: 0.9em; color: #666; }

        .story-text { font-size: 1.1em; line-height: 1.6; color: #eee; margin-bottom: 30px; min-height: 100px; }

        .options { display: flex; flex-direction: column; gap: 15px; }
        .option-label {
            background: #333; padding: 15px; border-radius: 5px; cursor: pointer; border: 1px solid #444; transition: 0.2s; display: block;
        }
        .option-label:hover { background: #444; border-color: #e60000; color: white; }
        input[type="radio"] { margin-right: 15px; accent-color: #e60000; }

        .btn-submit { margin-top: 20px; background-color: #e60000; color: white; border: none; padding: 15px; font-size: 16px; cursor: pointer; width: 100%; font-family: 'Courier New', monospace; font-weight: bold; text-transform: uppercase; }
        .btn-submit:hover { background-color: #ff3333; }
    </style>
</head>
<body>
<div class="game-container">
    <div class="header">
        <span class="quest-title">КВЕСТ: ${sessionScope.questName}</span>
        <span class="player-name">Игрок: ${sessionScope.user}</span>
    </div>

    <div class="story-text">
        ${step.text}
    </div>

    <form method="post" action="game">
        <div class="options">
            <c:forEach var="option" items="${step.options}">
                <label class="option-label">
                    <input type="radio" name="choice" value="${option.key}" required>
                        ${option.key}
                </label>
            </c:forEach>
        </div>
        <input type="submit" value="Принять решение" class="btn-submit">
    </form>
</div>
</body>
</html>