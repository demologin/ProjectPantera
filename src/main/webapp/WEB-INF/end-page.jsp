<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<body>
<%--<h1><%= "Тестирование по моим заметкам!" %>--%>
</h1>

<br/>
<a href="test-page?id=${sessionScope.question.id}">Ответ не правильный. Тест окончен.</a>
<%--<a href="list-user">List Users</a>--%>
<%--<a href="edit-user?id=${user.id}">${user.login}</a>--%>
<form action="test-page" method="post">
    <button type="submit">Начать заново</button>
</form>
</body>
