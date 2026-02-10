<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Space Quest</title>
</head>
<body>
<%-- Берем имя из сессии --%>

<h3>${question.text}</h3>


<%-- Если игра НЕ закончена, рисуем кнопки ответов --%>
<form method="post" action="play">
    <input type="hidden" name="questId" value="${questId}">
    <input type="hidden" name="questionId" value="${question.id}">

    <c:forEach items="${question.answers}" var="answer">
        <button type="submit" name="answerId" value="${answer.id}">
                ${answer.text}
        </button>
    </c:forEach>
</form>


<%-- Если это финал (победа или поражение) --%>

</body>
</html>