<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/static/bootstrap/css/bootstrap.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Hero-Features-icons.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Bold-BS4-Full-Page-Image-Header.css'/>">

<body>

<jsp:useBean id="id" class="com.javarush.vasileva.entity.User" scope="request"/>
<jsp:useBean id="login" class="com.javarush.vasileva.entity.User" scope="request"/>
<jsp:useBean id="email" class="com.javarush.vasileva.entity.User" scope="request"/>
<jsp:useBean id="role" class="com.javarush.vasileva.entity.User" scope="request"/>

<div class="container" style="margin-top: 100px">
    <h2>Список пользователей</h2>

    <table class="table table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Имя</th>
            <th>Email</th>
            <th>Роль</th>
            <th class="text-end">Действия</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="user" items="${requestScope.users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.login}</td>
                <td>${user.email}</td>
                <td>${user.role}</td>
                <td class="text-end">
                    <a href="<c:url value='/edit-user?id=${user.id}'/>"
                       class="btn btn-sm btn-warning me-1">
                        Редактировать
                    </a>

                    <form method="POST"
                          action="<c:url value='/user-list'/>"
                          style="display:inline;"
                          onsubmit="return confirm('Удалить пользователя ${user.login}?')">
                        <input type="hidden" name="_method" value="DELETE">
                        <input type="hidden" name="id" value="${user.id}">
                        <button type="submit" class="btn btn-sm btn-danger">
                            Удалить
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a href="<c:url value='/edit-user'/>" class="btn btn-primary">
        Добавить пользователя
    </a>
</div>
</body>


