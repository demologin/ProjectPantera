<%--
  Created by IntelliJ IDEA.
  User: ZybinAV
  Date: 20.01.2026
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="/WEB-INF/jsp/common/header.jsp"/>

<!DOCTYPE html>
<html>
<head>
    <title>Пользователи</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        th {
            background-color: #f3f3f3;
        }

        tr:hover {
            background-color: #f9f9f9;
        }

        .blocked {
            color: red;
            font-weight: bold;
        }

        .active {
            color: green;
        }

        form {
            display: inline;
        }
    </style>
</head>
<body>
<h2>Список пользователей</h2>
<table>
    <thead>
    <tr>
        <th>Логин</th>
        <th>Email</th>
        <th>Роль</th>
        <th>Статус</th>
        <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
            <td>
                <c:choose>
                    <c:when test="${user.blocked}">
                        <span class="blocked">Заблокирован</span>
                    </c:when>
                    <c:otherwise>
                        <span class="active">Активен</span>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>

                <!-- Смена роли -->
                <form method="post"
                      action="${pageContext.request.contextPath}/admin/users/role">
                    <input type="hidden" name="userId" value="${user.id}" />
                    <select name="role">
                        <option value="USER">USER</option>
                        <option value="ADMIN">ADMIN</option>
                    </select>
                    <button type="submit">Сменить роль</button>
                </form>

                <!-- Блокировка -->
                <form method="post"
                      action="${pageContext.request.contextPath}/admin/users/block">
                    <input type="hidden" name="userId" value="${user.id}" />
                    <button type="submit">
                        <c:choose>
                            <c:when test="${user.blocked}">
                                Разблокировать
                            </c:when>
                            <c:otherwise>
                                Заблокировать
                            </c:otherwise>
                        </c:choose>
                    </button>
                </form>

            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
</body>
</html>
