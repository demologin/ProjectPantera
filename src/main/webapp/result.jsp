<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Финал истории</title>
    <style>
        body { font-family: 'Courier New', monospace; background-color: #0a0a0a; color: #ccc; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .result-box { background-color: #151515; padding: 50px; border: 1px solid #333; text-align: center; max-width: 600px; box-shadow: 0 0 30px #000; }
        .win { color: #00ff00; text-shadow: 0 0 10px #00ff00; font-size: 2em; text-transform: uppercase; }
        .lose { color: #e60000; text-shadow: 0 0 15px #ff0000; font-size: 2.5em; text-transform: uppercase; letter-spacing: 5px; }
        .quote { font-style: italic; margin: 30px 0; color: #666; line-height: 1.6; }
        .nav-links { margin-top: 40px; display: flex; flex-direction: column; gap: 15px; }
        .btn { color: #eee; text-decoration: none; border: 1px solid #444; padding: 12px; transition: 0.3s; text-transform: uppercase; font-size: 14px; }
        .btn:hover { background: #e60000; color: #000; border-color: #e60000; font-weight: bold; }
    </style>
</head>
<body>
<div class="result-box">
    <c:choose>
        <c:when test="${sessionScope.result == 'WIN'}">
            <div class="win">ДЕЛО ЗАКРЫТО</div>
            <p class="quote">"Справедливость восторжествовала, но цена оказалась высока... Город может спать спокойно. Пока что."</p>
        </c:when>
        <c:otherwise>
            <div class="lose">ТУПИК</div>
            <p class="quote">"В этом городе легко исчезнуть. Еще один нераскрытый кейс, который покроется пылью в архиве."</p>
        </c:otherwise>
    </c:choose>

    <div class="nav-links">
        <a href="start?questName=${sessionScope.questName}" class="btn">ПОПРОБОВАТЬ ЕЩЕ РАЗ</a>
        <a href="index.jsp" class="btn" style="opacity: 0.6;">ВЕРНУТЬСЯ В ГЛАВНОЕ МЕНЮ</a>
    </div>
</div>
</body>
</html>