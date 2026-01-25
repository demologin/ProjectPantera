<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<c:import url="/WEB-INF/jsp/common/header.jsp"/>

<div class="container">

    <h2>Добро пожаловать</h2>

    <p>
        Добро пожаловать в тренажер собеседований по Java.
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

</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp"/>

