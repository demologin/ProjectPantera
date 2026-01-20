<%--
  Created by IntelliJ IDEA.
  User: ZybinAV
  Date: 20.01.2026
  Time: 13:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Выбор аватарки</title>
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jspf" %>
<h3>Загрузить свою аватарку</h3>
<form method="post"
      action="${pageContext.request.contextPath}/profile/avatar/upload"
      enctype="multipart/form-data">
    <input type="file"
           name="avatar"
           accept="image/*"
           required />
    <br><br>
    <button type="submit">Загрузить</button>
</form>
<h2>Выбор аватарки</h2>
<form method="post" action="${pageContext.request.contextPath}/profile/avatar">
    <c:forEach var="avatar" items="${avatars}">
        <label style="display: inline-block; margin: 10px; text-align: center;">
            <input type="radio"
                   name="avatarPath"
                   value="${avatar}"
                   required />
            <br>
            <img src="${pageContext.request.contextPath}${avatar}"
                 width="100"
                 alt="avatar">
        </label>
    </c:forEach>
    <br><br>
    <button type="submit">Сохранить</button>
    <a href="${pageContext.request.contextPath}/profile">Отмена</a>
</form>
<%@ include file="/WEB-INF/jsp/common/footer.jspf" %>
</body>
</html>
