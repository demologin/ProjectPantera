<%--
  Created by IntelliJ IDEA.
  User: ZybinAV
  Date: 15.01.2026
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Результат интервью</title>
</head>
<body>
    <h2>Результат интервью</h2>
    <h3>Тема теста: ${topic.displayName}</h3>
    <p>Правильных ответов: ${score} из ${totalQuestions}</p>
    <c:choose>
        <c:when test="${passed}">
            <h3>Поздравляем! Вы успешно прошли интервью 🎉</h3>
            <p>Оффер отправлен на вашу почту</p>
        </c:when>
        <c:otherwise>
            <h3>К сожалению, интервью не пройдено</h3>
            <p>Мы вам перезвоним... когда-нибудь... но это не точно</p>
        </c:otherwise>
    </c:choose>
<a href="${pageContext.request.contextPath}/start">
    Начать заново
</a>
</body>
</html>
