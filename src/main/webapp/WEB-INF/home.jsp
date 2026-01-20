<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
...
<%--<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/style.css'/>">--%>
<html>
<head>
    <title>Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>
<body>
<h2>Quests</h2>

<c:forEach var="quest" items="${requestScope.quests}">
    <a href="quest-page?id=${quest.id}">${quest.title}</a><br/>
</c:forEach>

</body>
</html>
