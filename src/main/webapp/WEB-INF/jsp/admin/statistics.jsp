<%--
  Created by IntelliJ IDEA.
  User: ZybinAV
  Date: 21.01.2026
  Time: 12:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="/WEB-INF/jsp/common/header.jsp"/>

<!DOCTYPE html>
<html>
<head>
    <title>Статистика тестов</title>
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
    </style>
</head>
<body>
<h2>Статистика тестов</h2>
<p>
    <strong>Всего тестов:</strong> ${totalTests}<br>
    <strong>Успешно пройдено:</strong> ${passedTests}
</p>
<h3>По пользователям</h3>
<table>
    <tr>
        <th>Пользователь</th>
        <th>Всего тестов</th>
        <th>Пройдено</th>
    </tr>
    <c:forEach var="stat" items="${userStats}">
        <tr>
            <td>${stat.username}</td>
            <td>${stat.total}</td>
            <td>${stat.passed}</td>
        </tr>
    </c:forEach>
</table>
<h3>По темам</h3>
<table>
    <tr>
        <th>Тема</th>
        <th>Попыток</th>
        <th>Успешно</th>
        <th>% успеха</th>
    </tr>
    <c:forEach var="stat" items="${topicStats}">
        <tr>
            <td>${stat.topicCode}</td>
            <td>${stat.total}</td>
            <td>${stat.passed}</td>
            <td>${stat.successRate}</td>
        </tr>
    </c:forEach>
</table>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
</body>
</html>
