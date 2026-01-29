<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/static/bootstrap/css/bootstrap.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Hero-Features-icons.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Bold-BS4-Full-Page-Image-Header.css'/>">

<html>
<head>
    <title>${requestScope.quest != null ? 'Редактирование квеста' : 'Создание квеста'}</title>
</head>
<body>
<section class="py-4 py-xl-5">
    <div class="textarea-container">
        <div class="text bg-primary-subtle border rounded border-0 p-4 py-5 w-100">
            <div class="row h-100 justify-content-center">
                <div class="col-md-10 col-xl-8">
                    <div class="d-flex flex-column align-items-center text-center">

                        <h2 class="text-uppercase fw-bold text mb-3">
                            ${requestScope.edit ? 'Редактирование квеста' : 'Новый квест'}
                        </h2>

                        <p>Для редактирования нажмите на карточку квеста "Редактировать квест" или выберите квест из списка ниже</p>

                        <div class="mb-4">
                            <c:forEach var="quest" items="${requestScope.quests}">
                                <div class="mb-1">
                                    <a href="edit-quest?questId=${quest.id}" class="link text">${quest.title}</a>
                                </div>
                            </c:forEach>
                        </div>

                        <form method="post" action="<c:url value='/edit-quest'/>" class="w-100">
                            <div class="field mb-3">
                                <textarea class="form-control" name="questJson" id="questJson"
                                          style="border: 0; border-bottom: 1px solid #E0E0E0;"
                                          placeholder="Введите JSON квеста">${requestScope.questJson}</textarea>
                                <label class="form-label mb-0" for="questJson"></label>
                            </div>

                            <button class="btn btn-light fs-5 px-4 py-2" type="submit">
                                ${requestScope.edit ? 'Обновить квест' : 'Создать квест'}
                            </button>

                            <c:if test="${not empty requestScope.error}">
                                <div class="alert alert-danger mt-3">${requestScope.error}</div>
                            </c:if>
                            <c:if test="${not empty requestScope.info}">
                                <div class="alert alert-success mt-3">${requestScope.info}</div>
                            </c:if>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<%@include file="footer.jsp" %>

</body>
</html>

