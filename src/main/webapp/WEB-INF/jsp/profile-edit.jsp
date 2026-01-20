<%--
  Created by IntelliJ IDEA.
  User: ZybinAV
  Date: 20.01.2026
  Time: 12:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Редактирование профиля</title>
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jspf" %>
<h2>Редактирование профиля</h2>
<c:set var="user" value="${sessionScope.currentUser}" />
    <form method="post" action="${pageContext.request.contextPath}/profile/edit">
        <label>
            Никнейм:<br>
            <input type="text"
                   name="nickname"
                   value="${user.nickname}"
                   required />
        </label>
        <br><br>
        <label>
            О себе:<br>
            <textarea name="about"
                      rows="5"
                      cols="40">${user.about}</textarea>
        </label>
        <br><br>
        <button type="submit">Сохранить</button>
        <a href="${pageContext.request.contextPath}/profile">Отмена</a>
    </form>
<%@ include file="/WEB-INF/jsp/common/footer.jspf" %>

</body>
</html>
