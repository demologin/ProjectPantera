<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp"%>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootswatch@5.3.3/dist/superhero/bootstrap.min.css">
</head>
<body>
<c:forEach var="user" items="${requestScope.users}">
    <a href="edit-user?id=${user.id}">${user.login}</a>
</c:forEach>

</body>

