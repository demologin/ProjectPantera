<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Результат</title>
</head>
<body>
<c:choose>
<c:when test="${sessionScope.result == 'WIN'}">
<h2 style="color: green;">Победа!</h2>
</c:when>
<c:otherwise>
<h2 style="color: red;">Поражение...</h2>
</c:otherwise>
</c:choose>
<p>Игр сыграно: ${sessionScope.gamesCount}</p>
<a href="restart">Попробовать еще раз</a>