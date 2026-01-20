<%--
  Created by IntelliJ IDEA.
  User: ZybinAV
  Date: 18.01.2026
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Главная</title>
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jspf" %>


        <h2>Добро пожаловать</h2>
        <p>
            Добро пожаловать в тренажер собеседований по Java
        </p>
        <p>
            Это учебное веб-приложение для подготовки к техническим собеседованиям.
            Здесь вы можете:
        </p>
        <ul>
            <li>проходить тесты по ключевым темам Java</li>
            <li>получать случайный набор вопросов при каждом запуске</li>
            <li>оценивать уровень своей подготовки</li>
            <li>тренироваться в формате, близком к реальному интервью</li>
        </ul>

        <form method="get" action="${pageContext.request.contextPath}/test/settings">
            <button type="submit">Перейти к настройке теста</button>
        </form>

    <hr>
<%@ include file="/WEB-INF/jsp/common/footer.jspf" %>
</body>
</html>
