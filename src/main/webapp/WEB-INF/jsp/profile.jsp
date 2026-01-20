<%--
  Created by IntelliJ IDEA.
  User: ZybinAV
  Date: 20.01.2026
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="/WEB-INF/jsp/common/header.jsp"/>

<!DOCTYPE html>
<html>
<head>
    <title>Профиль пользователя</title>
</head>
<body>
    <h2>Профиль пользователя</h2>
    <c:set var="user" value="${sessionScope.currentUser}" />
    <p>
        <strong>Никнейм:</strong>
        ${user.nickname}
    </p>
    <p>
        <strong>Email:</strong>
        ${user.email}
    </p>
    <p>
        <strong>О себе:</strong><br>
        <c:choose>
            <c:when test="${empty user.about}">
                <em>Не указано</em>
            </c:when>
            <c:otherwise>
                ${user.about}
            </c:otherwise>
        </c:choose>
    </p>
    <p>
        <strong>Аватар:</strong><br>
        <img src="${pageContext.request.contextPath}${user.avatarPath}"
             alt="Avatar"
             width="120">
    </p>
    <br>
    <a href="${pageContext.request.contextPath}/profile/edit">
        Редактировать профиль
    </a>
    <%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
</body>
</html>
