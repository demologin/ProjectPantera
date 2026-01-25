<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Текстовые квесты</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #1c1c1c; color: #f0f0f0; text-align: center; padding: 50px; }
        h1 { color: #e60000; }
        select, input[type=text] { padding: 10px; margin: 10px; font-size: 16px; }
        input[type=submit] { padding: 10px 20px; font-size: 16px; cursor: pointer; background-color: #e60000; color: white; border: none; border-radius: 5px; }
        .desc { margin: 20px auto; width: 70%; text-align: left; background-color: #2a2a2a; padding: 20px; border-radius: 10px; }
    </style>
</head>
<body>
<h1>Добро пожаловать в мир текстовых квестов!</h1>

<div class="desc">
    <p>Выберите один из трёх увлекательных квестов:</p>
    <ul>
        <li><strong>Черная Орхидея</strong> — нуарный детектив, где вам предстоит раскрыть убийство профессора в загадочной лаборатории.</li>
        <li><strong>Петля времени</strong> — научная фантастика, попытайтесь разорвать временную петлю и спасти коллегу от убийства.</li>
        <li><strong>Король Артур: Проклятый трон</strong> — фэнтези-приключение в Камелоте, спасите короля и остановите тёмного мага.</li>
    </ul>
</div>

<form method="post" action="start">
    <label for="playerName">Введите ваше имя:</label><br>
    <input type="text" id="playerName" name="playerName" required><br>

    <label for="questSelect">Выберите квест:</label><br>
    <select id="questSelect" name="questName" required>
        <option value="Черная Орхидея">Черная Орхидея</option>
        <option value="Петля времени">Петля времени</option>
        <option value="Король Артур: Проклятый трон">Король Артур: Проклятый трон</option>
    </select><br>

    <input type="submit" value="Начать игру">
</form>

<c:if test="${not empty param.playerName}">
    <%-- Сохраняем имя игрока в сессии --%>
    <c:set var="playerName" value="${param.playerName}" scope="session"/>
    <c:set var="questName" value="${param.questName}" scope="session"/>
</c:if>
</body>
</html>
