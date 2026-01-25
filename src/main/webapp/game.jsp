

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Чёрная Орхидея</title>
</head>
<body>
<h2>${step.text}</h2>
<form method="post" action="game">
    <c:forEach var="option" items="${step.options}">
        <input type="radio" name="choice" value="${option.key}" required> ${option.key} <br/>
    </c:forEach>
    <br/>
    <input type="submit" value="Дальше">
</form>
</body>
</html>