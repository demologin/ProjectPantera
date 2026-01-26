<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>

<%--<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/style.css'/>">--%>
<link rel="stylesheet" type="text/css" href="<c:url value='/static/bootstrap/css/bootstrap.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Hero-Features-icons.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Bold-BS4-Full-Page-Image-Header.css'/>">
<html>
<head>
    <title>Home</title>
    <%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"--%>
    <%--          integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">--%>
</head>
<body>
<div class="masthead">
    <div class="container-fluid h-100">
        <div class="row h-100 justify-content-center align-items-center">
            <div class="col text-center">
                <section>
                    <div style="height: 500px;background: url('/images/background-main.png') center / cover;"></div>
                    <div class="container position-relative" style="top: -100px">
                        <div class="row gy-5 gy-lg-0 row-cols-1 row-cols-md-2 row-cols-lg-3">
                            <c:forEach var="quest" items="${requestScope.quests}">
                                <div class="col">
                                    <div class="card h-100 box-border">
                                        <div class="card-body p-4 pt-5">
                                            <h4 class="card-title">${quest.title}</h4>
                                            <p class="card-text text-center">${quest.description}</p>
                                        </div>
                                        <div class="card-footer p-4 py-3"><a href="play-game?id=${quest.id}">Посмотреть
                                            квест &nbsp;<svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em"
                                                             fill="currentColor" viewBox="0 0 16 16"
                                                             class="bi bi-arrow-right">
                                                <path fill-rule="evenodd"
                                                      d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8"></path>
                                            </svg>
                                        </a></div>
                                        <div class="card-footer p-4 py-3"><a href="edit-quest?questId=${quest.id}">Редактировать
                                            квест &nbsp;<svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em"
                                                             fill="currentColor" viewBox="0 0 16 16"
                                                             class="bi bi-arrow-right">
                                                <path fill-rule="evenodd"
                                                      d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8"></path>
                                            </svg>
                                        </a></div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </div>
</div>
</body>
</html>
