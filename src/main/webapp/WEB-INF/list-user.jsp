<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp"%>
<div class="container py-4">
    <h1 class="h4 mb-3 page-title">Users</h1>
    <ul class="list-group">
        <c:forEach var="user" items="${requestScope.users}">
            <li class="list-group-item">
                <div class="user-row">
                    <img class="user-avatar" src="<c:url value='/user-images/${user.image}'/>" alt="${user.login}">
                    <a class="link-primary text-decoration-none" href="<c:url value='/edit-user?id=${user.id}'/>">${user.login}</a>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>
<%@include file="parts/footer.jsp"%>

