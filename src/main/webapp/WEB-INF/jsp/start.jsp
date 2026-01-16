<%--
  Created by IntelliJ IDEA.
  User: ZybinAV
  Date: 14.01.2026
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Начало интервью</title>
</head>
<body>
    <h2>Тренировочное собеседование по Java</h2>
<form method="post" action="${pageContext.request.contextPath}/start">
    <label for="topic">Выберите тему: </label>
    <select name="topic" id="topic" required>
        <c:forEach var="topic" items="${topics}">
            <option value="${topic}">
                ${topic.displayName}
            </option>
        </c:forEach>
    </select>
    <br><br>
    <button type="submit">Начать интервью</button>
</form>
</body>
</html>
