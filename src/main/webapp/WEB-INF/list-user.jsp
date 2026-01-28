<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp"%>
<div class="container py-4">
    <h1 class="h4 mb-3 page-title">Users</h1>
    <ul class="list-group">
        <c:forEach var="user" items="${requestScope.users}">
            <li class="list-group-item">
                <a class="link-primary text-decoration-none" href="edit-user?id=${user.id}">${user.login}</a>
            </li>
        </c:forEach>
    </ul>
</div>
<%@include file="parts/footer.jsp"%>

