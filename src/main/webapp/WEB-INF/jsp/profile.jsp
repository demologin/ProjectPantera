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
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            padding: 8px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        th {
            background-color: #f3f3f3;
        }

        .passed {
            color: green;
            font-weight: bold;
        }

        .failed {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
<h2>Профиль пользователя</h2>
<c:set var="user" value="${sessionScope.currentUser}"/>
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
<a href="${pageContext.request.contextPath}/profile/avatar">
    |
    Изменить аватарку
</a>
<hr>
<h3>История прохождения тестов</h3>
<c:if test="${empty results}">
    <p>Вы еще не проходили тесты.</p>
</c:if>
<c:if test="${not empty results}">
    <table>
        <tr>
            <th>Тема</th>
            <th>Вопросов</th>
            <th>Правильных</th>
            <th>Результат</th>
            <th>Дата</th>
        </tr>
        <c:forEach var="result" items="${results}">
            <tr>
                <td>${result.topicCode}</td>
                <td>${result.totalQuestions}</td>
                <td>${result.correctAnswers}</td>
                <td>
                    <c:choose>
                        <c:when test="${result.passed}">
                            <span class="passed">Пройден</span>
                        </c:when>
                        <c:otherwise>
                            <span class="failed">Не пройден</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${result.finishedAt}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<hr>

<h3>Успешность по темам</h3>

<c:if test="${empty topicStats}">
    <p>Пока нет данных для анализа.</p>
</c:if>

<c:if test="${not empty topicStats}">
    <table>
        <tr>
            <th>Тема</th>
            <th>Попыток</th>
            <th>Успешных</th>
            <th>% успешности</th>
        </tr>

        <c:forEach var="stat" items="${topicStats}">
            <tr>
                <td>${stat.topicDisplayName}</td>
                <td>${stat.total}</td>
                <td>${stat.passed}</td>
                <td>${stat.successRate}%</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
</body>
</html>
