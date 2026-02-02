<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<body>
<h1><%= "Тестирование по по SOLID" %>
</h1>

<form action="test-page" method="post">
    <p>${sessionScope.question.text}</p>


    <input type="radio" id="option1" name="answer" value="option1" required>
    <label for="option1">${sessionScope.answer.text}</label><br>

    <input type="radio" id="option2" name="answer" value="option2">
    <label for="option2">${sessionScope.answer1.text}</label><br><br>

    <button type="submit">Далее</button>
</form>

<br/>
</body>

<%--user-это элемент массива, users-массив  --%>
<%--<c:forEach var="user" items="${requestScope.users}">
    <a href="edit-user?id=${user.id}">${user.login}</a>
</c:forEach>--%>




<%--
<form action="next.jsp" method="post">
    <p>Какой вариант вы выбираете?</p>

    <input type="radio" id="option1" name="answer" value="option1" required>
    <label for="option1">Вариант 1</label><br>

    <input type="radio" id="option2" name="answer" value="option2">
    <label for="option2">Вариант 2</label><br><br>

    <button type="submit">Далее</button>
</form>--%>
