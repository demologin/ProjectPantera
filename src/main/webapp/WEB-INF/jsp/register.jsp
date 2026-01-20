<%--
  Created by IntelliJ IDEA.
  User: ZybinAV
  Date: 19.01.2026
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="/WEB-INF/jsp/common/header.jsp"/>

<!DOCTYPE html>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
    <h2>Регистрация</h2>
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/register">
        <label>
            Логин:
            <input type="text" name="username" required />
        </label>
        <br><br>
        <label>
            Пароль:
            <input type="password" name="password" required />
        </label>
        <br><br>
        <label>
            Email:
            <input type="email" name="email" required />
        </label>
        <br><br>
        <button type="submit">Зарегистрироваться</button>
    </form>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
</body>
</html>
