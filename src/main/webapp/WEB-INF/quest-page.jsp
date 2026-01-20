<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/style.css'/>">
<html>
<head>
    <title>New Quest</title>
<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"--%>
<%--          integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">--%>

<%--    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css">--%>
</head>
<body>
<h1>${requestScope.quest.title}</h1>
<p class="quest-text"> ${requestScope.quest.description}</p>
<button>Начать</button>
<a href="${pageContext.request.contextPath}/">
    <button>Назад к выбору квеста</button>
</a>


</body>
</html>
