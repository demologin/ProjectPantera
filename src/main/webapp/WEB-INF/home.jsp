<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/static/bootstrap/css/bootstrap.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Hero-Features-icons.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Bold-BS4-Full-Page-Image-Header.css'/>">
<html>
<head>
    <title>Home</title>
</head>
<body>
<div class="masthead">
    <div class="container-fluid h-100">
        <div class="row h-100 justify-content-center align-items-center">
            <div class="col text-center">
                <section>
                    <div style="height: 500px;background: url('/images/background-main.png') center / cover;"></div>
                    <div class="container position-relative" style="top: -200px">
                        <div class="row gy-2 gy-lg-0 row-cols-1 row-cols-md-2 row-cols-lg-3">
                            <c:forEach var="quest" items="${requestScope.quests}">
                                <div class="col">
                                    <div class="card h-100 w-100 box-border">

                                        <div class="card-body">
                                            <h4 class="card-title">${quest.title}</h4>
                                            <p class="card-text text-center">${quest.description}</p>
                                        </div>

                                        <div class="mb-2">
                                            <form method="GET" action="play-game" style="display:inline;">
                                                <input type="hidden" name="questId" value="${quest.id}">
                                                <button type="submit" class="btn btn-sm btn-primary w-75">
                                                    Посмотреть квест
                                                </button>
                                            </form>
                                        </div>

                                        <div class="mb-2">
                                            <form method="GET" action="edit-quest" style="display:inline;">
                                                <input type="hidden" name="questId" value="${quest.id}">
                                                <button type="submit" class="btn btn-sm btn-warning w-75">
                                                    Редактировать квест
                                                </button>
                                            </form>
                                        </div>

                                        <div class="mb-4">
                                            <form method="POST" action="<c:url value='/home'/>" style="display:inline;">
                                                <input type="hidden" name="_method" value="DELETE">
                                                <input type="hidden" name="questId" value="${quest.id}">
                                                <button type="submit" class="btn btn-sm btn-danger w-75"
                                                        onclick="return confirm('Удалить квест ${quest.title}?')">
                                                Удалить квест
                                                </button>
                                            </form>
                                        </div>
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

<%@include file="footer.jsp" %>

</body>
</html>


