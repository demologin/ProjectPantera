<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>

<body>
<div class="container py-4 py-xl-5">
    <div class="row mb-4 mb-lg-5">
        <div class="col-md-8 col-xl-6 text-center mx-auto">
            <h4>Обработанные сообщения полученные через форму обратной связи</h4>
        </div>
    </div>
    <form class="form-horizontal" method="post">
        <c:if test="${sessionScope.user.role=='ADMIN' || sessionScope.user.role=='MODERATOR'}">
            <c:forEach var="message" items="${requestScope.messages}">
                <c:if test="${message.completed}">
                    <div class="row">
                            <%--                <div class="col-md-8 col-xl-6 mx-auto p-4">--%>
                        <div class="d-flex align-items-center align-items-md-start align-items-xl-center">
                            <div class="bs-icon-xl bs-icon-circle bs-icon-primary d-flex flex-shrink-0 justify-content-center align-items-center me-4 d-inline-block bs-icon xl">
                                <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor"
                                     viewBox="0 0 16 16" class="bi bi-bell">
                                    <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zM8 1.918l-.797.161A4.002 4.002 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4.002 4.002 0 0 0-3.203-3.92L8 1.917zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5.002 5.002 0 0 1 13 6c0 .88.32 4.2 1.22 6z"></path>
                                </svg>
                            </div>
                            <div>
                                <input type="hidden" name="id" value="${message.id}">
                                <h4>${message.topic}</h4>
                                <p>${message.message}</p>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox"
                                           id="rememberMe" name="rememberMe"
                                           <c:if test="${message.completed}">checked</c:if>>
                                    <label class="form-check-label" for="rememberMe">Обработано</label>
                                </div>
                                <div class="form-group">
                                    <button id="delete" name="action" value="delete" class="btn btn-danger">Удалить
                                    </button>
                                </div>
                                <p>${message.name} - ${message.email}</p>
                            </div>
                        </div>
                        <hr class="my-5">
                    </div>
                </c:if>
            </c:forEach>
            <div class="form-group">
                <button id="save" name="action" value="GoMessages" class="btn btn-success">Активные сообщения</button>
            </div>
        </c:if>
    </form>
</div>
</body>

<%@include file="parts/footer.jsp" %>
