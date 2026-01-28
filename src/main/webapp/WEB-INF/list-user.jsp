<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<body>
<jsp:useBean id="user" scope="session"
             type="com.javarush.goncharov.model.User"/>
<div class="container py-4 py-xl-5">
    <div class="row mb-4 mb-lg-5">
        <div class="col-md-8 col-xl-6 text-center mx-auto">
            <h2>Пользователи</h2>
        </div>
    </div>
    <div class="row gy-4 row-cols-2 row-cols-md-4">
        <c:if test="${user.role=='ADMIN'}">
            <c:forEach var="user" items="${requestScope.users}">
                <div class="col">
                    <div class="card border-0 shadow-none">
                        <div class="card-body text-center d-flex flex-column align-items-center p-0"><img
                                class="rounded-circle mb-3 fit-cover" width="130" height="130" src="/images/kwa"
                                loading="eager">
                            <h5 class="fw-bold text-primary card-title mb-0"><strong><a
                                    href="edit-user?id=${user.id}">${user.login}</a> <br> <br></strong></h5>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${user.role!='ADMIN'}">
            <c:forEach var="user" items="${requestScope.users}">
                <div class="col">
                    <div class="card border-0 shadow-none">
                        <div class="card-body text-center d-flex flex-column align-items-center p-0"><img
                                class="rounded-circle mb-3 fit-cover" width="130" height="130" src="/images/kwa"
                                loading="eager">
                            <h5 class="fw-bold text-primary card-title mb-0"><strong>${user.login} <br> <br></strong>
                            </h5>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
<%@include file="parts/footer.jsp" %>

