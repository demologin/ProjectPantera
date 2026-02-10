<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 2/9/2026
  Time: 9:47 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="head.jsp"%>
<html>
<head>

</head>
<body>

<h1>Привет тут ты можешь написать свой квест</h1>
<h2>в каждом блоке тебе надо:</h2>
<h2>в первом столбце написать вопрос</h2>
<h2>во втором правильный ответ</h2>
<h2>в третьем неправильный ответ</h2>
<h2>в четвертом что будет за не правильный ответ</h2>
<h2>в последнюю если он победил </h2>
<form method="post" action="create">
    <input type="hidden" name="answerId" value="${answer.id}">
    <input type="hidden" name="questionId" value="${question.id}">

    <c:forEach var="i" begin="0" end="${num-1}">
        <div style="border:1px solid #ccc; padding:10px; margin-bottom:15px;">
            <input type="text" name="a${i}">
            <input type="text" name="b${i}">
            <input type="text" name="c${i}">
            <input type="text" name="d${i}">
        </div>
    </c:forEach>
    <input type="text" name="e">
    <button type="submit">OK</button>
</form>


</body>
</html>
