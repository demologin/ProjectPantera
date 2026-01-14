<%--
  Created by IntelliJ IDEA.
  User: ZybinAV
  Date: 14.01.2026
  Time: 22:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Вопрос</title>
</head>
<body>
    <h2>Вопрос ${questionNumber} из ${totalQuestions}</h2>
    <p>${question.questionText}</p>
    <form method="post" action="${pageContext.request.contextPath}/question">
        <c:forEach var="answer" items="${question.answers}" varStatus="status">
            <div>
                <input type="radio"
                name="answerIndex"
                value="${status.index}"
                required />
                ${answer}
            </div>
        </c:forEach>
        <button type="submit">Ответить</button>
    </form>
</body>
</html>
