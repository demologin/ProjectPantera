<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp"%>
<div class="container py-4">
    <h1 class="display-5 mb-3 hero-title"><%= "Hello World!" %></h1>
    <a class="btn btn-primary" href="<c:url value='/list-user'/>">List Users</a>
</div>
<%@include file="parts/footer.jsp"%>
