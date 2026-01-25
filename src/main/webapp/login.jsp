
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход в Квест</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #1c1c1c; color: #f0f0f0; text-align: center; padding: 50px; }
        .auth-container { background-color: #2a2a2a; padding: 30px; border-radius: 10px; display: inline-block; border: 1px solid #e60000; }
        input { margin: 10px; padding: 8px; border-radius: 5px; border: none; }
        .btn { background-color: #e60000; color: white; cursor: pointer; border: none; padding: 10px 20px; border-radius: 5px; font-weight: bold; }
        .error { color: #ff4d4d; margin-bottom: 10px; }
    </style>
</head>
<body>
<h1>Доступ к архивам</h1>
<div class="auth-container">
    <p class="error">${error}</p>
    <form action="auth" method="post">
        <input type="text" name="login" placeholder="Логин" required><br>
        <input type="password" name="password" placeholder="Пароль" required><br>
        <button type="submit" name="action" value="login" class="btn">Войти</button>
        <button type="submit" name="action" value="register" class="btn" style="background-color: #555;">Регистрация</button>
    </form>
</div>
</body>
</html>