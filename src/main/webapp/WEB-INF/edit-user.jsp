<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Hero-Features-icons.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Bold-BS4-Full-Page-Image-Header.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/bootstrap/css/bootstrap.min.css'/>">

<body>
<div class="container" style="margin-top: 100px">
    <form class="form-horizontal" method="post">
        <fieldset>

            <h2>Добавление/редактирование пользователя</h2>

            <div class="form-group">
                <label class="col-md-4 control-label" for="login">Логин</label>
                <div class="col-md-4">
                    <input
                            id="login"
                            name="login"
                            type="text"
                            value="${requestScope.user.login}"
                            placeholder="Логин не менее 3-х символов"
                            class="form-control input-md"
                            required
                            maxlength="15"
                            minlength="3">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label" for="email">Email</label>
                <div class="col-md-4">
                    <input
                            id="email"
                            name="email"
                            type="email"
                            value="${requestScope.user.email}"
                            placeholder="Электронная почта"
                            class="form-control input-md"
                            required>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label" for="password">Пароль</label>
                <div class="col-md-4">
                    <input id="password"
                           name="password"
                           type="password"
                           value="${requestScope.user.password}"
                           placeholder="Пароль не менее 4 символов"
                           class="form-control input-md"
                           required
                           maxlength="15"
                           minlength="4">
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label" for="role">Роль</label>
                <div class="col-md-4">
                    <select id="role" name="role" class="form-control">
                        <c:forEach var="role" items="${applicationScope.roles}">
                            <option value="${role}" ${role==requestScope.user.role?"selected":""}>${role}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group" style="margin-top: 20px">
                <div class="col-md-8">
                    <c:if test="${requestScope.user==null}">
                        <form action="${pageContext.request.contextPath}/edit-user" method="post">
                            <button
                                    type="submit"
                                    id="create"
                                    name="create"
                                    class="btn btn-success"
                            >
                                Create
                            </button>
                        </form>
                    </c:if>
                    <c:if test="${requestScope.user!=null}">
                        <form action="${pageContext.request.contextPath}/edit-user" method="post">
                            <button
                                    type="submit"
                                    id="update"
                                    name="update"
                                    class="btn btn-success"
                            >
                                Update
                            </button>
                        </form>
                    </c:if>
                </div>
            </div>

        </fieldset>
    </form>
</div>
</body>

