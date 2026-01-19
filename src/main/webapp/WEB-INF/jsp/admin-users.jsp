<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <!--suppress HtmlDeprecatedTag -->
    <title>Управление пользователями</title>

    <script src="${pageContext.request.contextPath}/js/admin.js"></script>
</head>
<body>

<h2>Управление пользователями</h2>

<table class="admin-table">
    <tr>
        <th>Логин</th>
        <th>Роль</th>
        <th>Действия</th>
    </tr>

    <c:forEach var="u" items="${requestScope.users}">
        <tr>
            <td>${u.login()}</td>
            <td>${u.role()}</td>
            <td>
                <div class="admin-actions">

                    <!-- ===== ПРОСМОТР ===== -->
                    <div id="view-${u.login()}">
                        <button class="editBtn"
                                type="button"
                                onclick="enableRowEdit('${u.login()}')">
                            Изменить
                        </button>
                    </div>

                    <!-- ===== РЕДАКТИРОВАНИЕ ===== -->
                    <div id="edit-${u.login()}" class="hidden">

                        <form action="admin-user-save" method="post" class="inline-form">
                            <input type="hidden" name="login" value="${u.login()}">

                            <label>
                                Пароль:
                                <input name="password"
                                       data-original=""
                                       oninput="onUserFieldChange('${u.login()}')">
                            </label>

                            <label>
                                Роль:
                                <select name="role"
                                        data-original="${u.role()}"
                                        onchange="onUserFieldChange('${u.login()}')">
                                    <option value="USER" ${u.role() == 'USER' ? 'selected' : ''}>USER</option>
                                    <option value="ADMIN" ${u.role() == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                                </select>
                            </label>

                            <button type="submit"
                                    class="saveBtn hidden">
                                Сохранить
                            </button>
                        </form>

                        <c:if test="${u.role() != 'ADMIN'}">
                            <form action="admin-user-delete"
                                  method="get"
                                  class="inline-form">
                                <input type="hidden" name="login" value="${u.login()}">
                                <button type="submit" class="danger">
                                    Удалить
                                </button>
                            </form>
                        </c:if>

                        <button type="button"
                                onclick="cancelRowEdit('${u.login()}')">
                            Отмена
                        </button>

                    </div>
                </div>
            </td>
        </tr>
    </c:forEach>
</table>

<hr>

<!-- ===== ДОБАВЛЕНИЕ ПОЛЬЗОВАТЕЛЯ ===== -->
<button id="addBtn" type="button" onclick="showAddForm()">
    Добавить нового пользователя
</button>

<div id="addForm" class="hidden" style="margin-top:15px">

    <h3>Новый пользователь</h3>

    <form action="admin-user-save" method="post">
        <label>
            Логин:
            <input name="login" required>
        </label><br><br>

        <label>
            Пароль:
            <input name="password" required>
        </label><br><br>

        <label>
            Роль:
            <select name="role">
                <option value="USER">USER</option>
                <option value="ADMIN">ADMIN</option>
            </select>
        </label><br><br>

        <button type="submit">Сохранить</button>
        <button type="button" onclick="cancelAddForm()">Отмена</button>
    </form>

</div>

<hr>

<a href="admin">← Вернуться в админ-панель</a>

</body>
</html>