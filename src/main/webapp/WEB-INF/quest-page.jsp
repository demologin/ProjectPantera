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
<div class="masthead">
    <div class="container-fluid h-100">
        <div class="row h-100 justify-content-center align-items-center">
            <div class="col">
                <section class="py-4 py-xl-5">
                    <div class="container h-100">
                        <div class="row h-100">
                            <div class="col-md-10 col-xl-8 text-center d-flex d-sm-flex d-md-flex justify-content-center align-items-center justify-content-md-start align-items-md-center justify-content-xl-center mx-auto">
                                <div class="border border-1 rounded-5 border-black p-5">
                                    <h2 class="text-uppercase fw-bold mb-3">${requestScope.quest.title}</h2>
                                    <p class="mb-4">${requestScope.quest.text}</p>
                                    <a href="${pageContext.request.contextPath}/play-game?id=${requestScope.quest.id}">
                                        <button class="btn btn-primary fs-5 me-2 px-4 py-2" type="button">Начать</button>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/home">
                                        <button class="btn btn-outline-primary fs-5 px-4 py-2" type="button">Назад к выбору квеста</button>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </div>
</div>

</body>
</html>
