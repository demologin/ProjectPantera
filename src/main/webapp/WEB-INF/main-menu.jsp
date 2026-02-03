<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Главное меню — Квесты</title>
    <link rel="stylesheet" href="../static/style.css">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
    <div class="container">
        <h1>Выберите квест</h1>
        <c:forEach var="availableQuest" items="${availableQuests}" varStatus="status">
            <button class="btn" onclick="window.location='/new-quest?questIndex=${status.index}'">
                <c:out value="${availableQuest.title}"/>
            </button>
        </c:forEach>
        <c:if test="${not empty currentQuest}">
            <c:if test="${!currentQuest.isDone()}">
                <button class="btn btn-continue" onclick="window.location='/continue-quest'">
                    Продолжить квест:
                    <c:out value="${currentQuest.title}"/>
                </button>
            </c:if>
        </c:if>
        <button class="btn btn-create" onclick="window.location='/editor'">Создать квест</button>
        <div class="statistics">
            <hr style="border: 0; border-top: 1px solid #4a4a4a; margin: 20px 0;">
            <div class="candles-info">Статистика игрока</div>
            <div class="stats-grid">
                <p>Игр всего: <strong><c:out value="${statistics.gamesCount}"/></strong></p>
                <p>Побед: <span style="color: #4caf50;"><strong><c:out value="${statistics.victoriesCount}"/></strong></span></p>
                <p>Поражений: <span style="color: #e57373;"><strong><c:out value="${statistics.defeatsCount}"/></strong></span></p>
            </div>
        </div>
    </div>
</body>
</html>