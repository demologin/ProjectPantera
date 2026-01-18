<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Квест</title></head>
<body>
<form action="game" method="post">
    <c:choose>
        <c:when test="${sessionScope.state == 'UFO_CHALLENGE'}">
            <p>Ты потерял память. Принять вызов НЛО?</p>
            <input type="radio" name="answer" value="accept">Принять вызов<br>
            <input type="radio" name="answer" value="decline">Отклонить вызов<br>
        </c:when>
        <c:when test="${sessionScope.state == 'BRIDGE_CHOICE'}">
            <p>Ты принял вызов. Поднимаешься на мостик к капитану?</p>
            <input type="radio" name="answer" value="go">Подняться на мостик<br>
            <input type="radio" name="answer" value="refuse">Отказаться<br>
        </c:when>
        <c:when test="${sessionScope.state == 'IDENTITY_CHOICE'}">
            <p>Ты поднялся на мостик. Ты кто?</p>
            <input type="radio" name="answer" value="truth">Рассказать правду<br>
            <input type="radio" name="answer" value="lie">Солгать<br>
        </c:when>
    </c:choose>
    <input type="submit" value="Ответить">
</form>
</body>
</html>
