
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/static/bootstrap/css/bootstrap.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Hero-Features-icons.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Bold-BS4-Full-Page-Image-Header.css'/>">

<html>
<head>
    <title>Statistics</title>
</head>
<body class="bg-primary-subtle">
<div class="container" style="height: 100vh; margin-top: 100px">
    <h2>Статистика игрока</h2>

    <c:if test="${not empty requestScope.stats}">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Игрок: ${requestScope.stats.user.login}</h5>
                <p class="card-text">
                    <strong>Пройдено квестов:</strong> ${requestScope.stats.completedQuests}<br>
                </p>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>
