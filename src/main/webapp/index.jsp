<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Космический квест</title></head>
<body>
<h2>Пролог</h2>
<p>Ты стоишь в космическом порту и готов подняться на борт своего корабля. Разве ты не об этом мечтал?</p>
<form action="game" method="post">
    <input type="submit" value="Начать игру">
</form>
<c:if test="${not empty sessionScope.gamesPlayed}">
    <p>Количество сыгранных игр: ${sessionScope.gamesPlayed}</p>
</c:if>
</body>
</html>
