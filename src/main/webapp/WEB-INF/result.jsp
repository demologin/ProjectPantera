<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Результат</title></head>
<body>
    <h2>${sessionScope.message}</h2>
    <form action="game" method="post">
        <input type="submit" value="Сыграть снова">
    </form>
    <p>Количество сыгранных игр: ${sessionScope.gamesPlayed}</p>
</body>
</html>
