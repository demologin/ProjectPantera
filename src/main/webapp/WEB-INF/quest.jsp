<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 2/9/2026
  Time: 8:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="head.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="/quest">
    <h3>Введите имя квеста</h3>
    <input type="text" name="name">

    <h3>сколько будет ступеней?</h3>
    <input type="number" name="num" step="1">

    <button type="submit">OK</button>


</form>

</body>
</html>
