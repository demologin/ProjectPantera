<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<c:import url="/WEB-INF/jsp/common/header.jsp"/>

<div class="container">

    <h2>Вход</h2>

    <c:if test="${not empty error}">
        <p class="form-error">${error}</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/login">

        <div class="form-group">
            <label>
                Логин
                <input type="text" name="username" required>
            </label>
        </div>

        <div class="form-group">
            <label>
                Пароль
                <input type="password" name="password" required>
            </label>
        </div>

        <button type="submit">Войти</button>
    </form>

</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp"/>

