<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<h2>Вход</h2>

<% if (request.getAttribute("error") != null) { %>
<p style="color:red;">Неверный логин или пароль</p>
<% } %>

<form method="post" action="login">
    <label>
        Логин:<br>
        <input name="login" required>
    </label><br><br>

    <label>
        Пароль:<br>
        <input type="password" name="password" required>
    </label><br><br>

    <button type="submit">Войти</button>
</form>