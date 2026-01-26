<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/static/bootstrap/css/bootstrap.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Hero-Features-icons.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Bold-BS4-Full-Page-Image-Header.css'/>">

<html>
<head>
    <title>Title</title>
</head>
<body>
<section class="py-4 py-xl-5">
    <div class="textarea-container">
        <div class="text-white bg-primary border rounded border-0 p-4 py-5 w-100">
            <div class="row h-100 justify-content-center">
                <div class="col-md-10 col-xl-8">
                    <div class="d-flex flex-column align-items-center text-center">
                        <h2 class="text-uppercase fw-bold text-white mb-3">Выберите квест</h2>
                        <c:forEach var="quest" items="${requestScope.quests}">
                            <div class="mb-1">
                                <a href="update-quest?questId=${quest.id}" class="link text-white">${quest.title}</a>
                            </div>
                        </c:forEach>
                        <form method="post" action="<c:url value='/update-quest'/>" class="w-100">
                            <div class="field mb-3">
                                <textarea class="form-control" name="questJson" id="questJson"
                                          style="border: 0; border-bottom: 1px solid #E0E0E0;"
                                          placeholder="">${requestScope.questJson}</textarea>
                                <label class="form-label mb-0" for="questJson"></label>
                            </div>
                            <button class="btn btn-light fs-5 px-4 py-2" type="submit">Обновить квест</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
