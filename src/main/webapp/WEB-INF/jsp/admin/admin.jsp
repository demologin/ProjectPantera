<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<c:import url="/WEB-INF/jsp/common/header.jsp"/>

<div class="container">

    <h2>Панель администратора</h2>

    <p class="admin-note">
        Доступ разрешён только пользователям с ролью <strong>ADMIN</strong>.
    </p>

    <ul class="admin-menu">
        <li>
            <a href="${pageContext.request.contextPath}/admin/users">
                Управление пользователями
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/statistics">
                Статистика тестов
            </a>
        </li>
        <li class="admin-disabled">
            Управление тестами (позже)
        </li>
        <li class="admin-disabled">
            Статистика (позже)
        </li>
    </ul>

</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp"/>

