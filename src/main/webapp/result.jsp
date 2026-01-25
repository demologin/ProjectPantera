<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Результат</title>
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.result == 'WIN'}">
        <h2>Поздравляем! Вы раскрыли дело!</h2>
    </c:when>
    <c:otherwise>
        <h2>Вы проиграли. Попробуйте снова.</h2>
    </c:otherwise>
</c:choose>
<a href="game?restart=true">Начать заново</a>
</body>
</html>