<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>

<%--<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/style.css'/>">--%>
<link rel="stylesheet" type="text/css" href="<c:url value='/static/bootstrap/css/bootstrap.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Hero-Features-icons.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Bold-BS4-Full-Page-Image-Header.css'/>">
<html>
<head>
    <title>New Quest</title>
</head>
<body>

<h1>${requestScope.quest.title}</h1>

<p class="quest-text"> ${requestScope.quest.text}</p>

<a href="${pageContext.request.contextPath}/play-game?id=${requestScope.quest.id}">
    <button>Начать</button>
</a>

<a href="${pageContext.request.contextPath}/">
    <button>Назад к выбору квеста</button>
</a>

</body>
</html>
