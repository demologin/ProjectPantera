<%--
  Created by IntelliJ IDEA.
  User: ZybinAV
  Date: 19.01.2026
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Вход</title>
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jspf" %>
    <h2>Вход</h2>
    <c:if test="${not empty error}">
        <p style="color: red">${error}</p>
    </c:if>
<form method="post" action="${pageContext.request.contextPath}/login">
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
    <button type="submit">Войти</button>
</form>
<%@ include file="/WEB-INF/jsp/common/footer.jspf" %>
</body>
</html>
