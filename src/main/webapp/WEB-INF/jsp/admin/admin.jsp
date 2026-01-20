<%--
  Created by IntelliJ IDEA.
  User: ZybinAV
  Date: 20.01.2026
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="/WEB-INF/jsp/common/header.jsp"/>

<!DOCTYPE html>
<html>
<head>
    <title>Администрирование</title>
</head>
<body>
<h2>Панель администратора</h2>
<p>Доступ разрешён только пользователям с ролью ADMIN.</p>
<ul>
    <li>Управление пользователями (скоро)</li>
    <li>Управление тестами (скоро)</li>
    <li>Просмотр статистики (скоро)</li>
</ul>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
</body>
</html>
