<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="parts/header.jsp"/>
<div class="container">
    <jsp:useBean id="user" scope="session"
                 type="com.javarush.goncharov.model.User"/>

    <div class="px-4 py-5 my-5 text-center">
        <p class="lead mb-4">
        <h1 class="display-3 fw-bold"
            style="height: 41.25px;font-size: 27.88px;color: var(--bs-red);font-weight: bold;">Login: ${user.login}</h1>
        <h1 class="display-3 fw-bold"
            style="height: 41.25px;font-size: 27.88px;color: var(--bs-red);font-weight: bold;">Role: ${user.role}</h1>
        <h1 class="display-3 fw-bold"
            style="height: 41.25px;font-size: 27.88px;color: var(--bs-red);font-weight: bold;">Email: ${user.email}</h1>
        <div class="col-lg-6 mx-auto">
            <form class="form-horizontal" action="profile" method="post">
                <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                    <input type="hidden" name="id" value="${user.id}">
                    <button type="submit" name="action" value="edit" class="btn btn-primary btn-lg px-4 gap-3">Редактировать</button>
                    <c:if test="${sessionScope.user.login!=requestScope.user.login && sessionScope.user.role!='ADMIN'}">
                        <button type="submit" onclick="return confirm('Вы уверены?')" name="action" value="delete"
                                class="btn btn-primary btn-lg px-4 gap-3">Удалить</button>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
<c:import url="parts/footer.jsp"/>

