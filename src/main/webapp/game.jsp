<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>${sessionScope.questName}</title>
    <style>
        body { font-family: 'Courier New', monospace; background-color: #1c1c1c; color: #ccc; display: flex; justify-content: center; min-height: 100vh; margin: 0; padding-top: 50px; }
        .game-container { background-color: #252525; width: 80%; max-width: 800px; padding: 40px; border-radius: 5px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.7); border: 1px solid #333; position: relative; }

        /* Хедер внутри контейнера */
        .header { display: flex; justify-content: space-between; align-items: flex-start; border-bottom: 1px solid #444; padding-bottom: 15px; margin-bottom: 30px; }
        .quest-title { color: #e60000; font-size: 1.2em; font-weight: bold; text-transform: uppercase; letter-spacing: 1px; }
        .header-info { text-align: right; display: flex; flex-direction: column; gap: 8px; }

        .btn-back { color: #888; text-decoration: none; font-size: 11px; border: 1px solid #444; padding: 5px 10px; border-radius: 4px; transition: 0.3s; text-transform: uppercase; }
        .btn-back:hover { color: #fff; border-color: #e60000; background: rgba(230,0,0,0.1); }

        .player-name { font-size: 0.85em; color: #666; }
        .player-name span { color: #e60000; }

        .story-text { font-size: 1.15em; line-height: 1.7; color: #eee; margin-bottom: 35px; min-height: 120px; white-space: pre-wrap; }

        .options { display: flex; flex-direction: column; gap: 15px; }
        .option-label { background: #333; padding: 18px; border-radius: 5px; cursor: pointer; border: 1px solid #444; transition: 0.2s; display: block; font-size: 1.05em; }
        .option-label:hover { background: #3d3d3d; border-color: #e60000; color: white; }
        input[type="radio"] { margin-right: 15px; accent-color: #e60000; }

        .btn-submit { margin-top: 30px; background-color: #e60000; color: white; border: none; padding: 18px; font-size: 16px; cursor: pointer; width: 100%; font-family: 'Courier New', monospace; font-weight: bold; text-transform: uppercase; letter-spacing: 2px; }
        .btn-submit:hover { background-color: #ff3333; box-shadow: 0 0 15px rgba(230,0,0,0.4); }
    </style>
</head>
<body>
<div class="game-container">
    <div class="header">
        <span class="quest-title">КВЕСТ: ${sessionScope.questName}</span>
        <div class="header-info">
            <a href="index.jsp" class="btn-back">Вернуться к выбору</a>
            <div class="player-name">Агент: <span>${sessionScope.user}</span></div>
        </div>
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
        <input type="submit" value="ПРИНЯТЬ РЕШЕНИЕ" class="btn-submit">
    </form>
</div>
</body>
</html>