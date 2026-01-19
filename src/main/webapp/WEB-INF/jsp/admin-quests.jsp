<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <!--suppress HtmlDeprecatedTag -->
    <title>Управление квестами</title>

    <script src="${pageContext.request.contextPath}/js/admin.js"></script>
</head>
<body>

<h2>Управление квестами</h2>

<table class="admin-table">
    <tr>
        <th>ID</th>
        <th>Название</th>
        <th>Действия</th>
    </tr>

    <c:forEach var="q" items="${requestScope.quests}">
        <tr>
            <td>${q.id}</td>
            <td>${q.title}</td>
            <td>
                <div class="admin-actions">

                    <!-- ===== ПРОСМОТР ===== -->
                    <div id="view-${q.id}">
                        <button class="editBtn"
                                type="button"
                                onclick="enableRowEdit('${q.id}')">
                            Изменить
                        </button>
                    </div>

                    <!-- ===== РЕДАКТИРОВАНИЕ ===== -->
                    <div id="edit-${q.id}" class="hidden">

                        <form action="admin-quest-save"
                              method="post"
                              enctype="multipart/form-data"
                              class="inline-form">

                            <input type="hidden" name="id" value="${q.id}">
                            <input type="hidden" name="title" value="${q.title}">
                            <input type="file" name="file" required>
                            <button type="submit">Заменить файл</button>
                        </form>

                        <form action="admin-quest-delete"
                              method="get"
                              class="inline-form">

                            <input type="hidden" name="id" value="${q.id}">
                            <button type="submit" class="danger">Удалить</button>
                        </form>

                        <button type="button"
                                onclick="cancelRowEdit('${q.id}')">
                            Отмена
                        </button>

                    </div>
                </div>
            </td>
        </tr>
    </c:forEach>
</table>

<hr>

<!-- ===== ДОБАВЛЕНИЕ КВЕСТА ===== -->
<button id="addBtn" type="button" onclick="showAddForm()">
    Добавить новый квест
</button>

<div id="addForm" class="hidden margin-top">

    <h3>Новый квест</h3>

    <form action="admin-quest-save"
          method="post"
          enctype="multipart/form-data">

        <label>
            ID:
            <input name="id" required>
        </label><br><br>

        <label>
            Название:
            <input name="title" required>
        </label><br><br>

        <label>
            TXT файл:
            <input type="file" name="file" required>
        </label><br><br>

        <button type="submit">Сохранить</button>
        <button type="button" onclick="cancelAddForm()">Отмена</button>
    </form>

</div>

<hr>

<a href="admin">← Вернуться в админ-панель</a>

</body>
</html>