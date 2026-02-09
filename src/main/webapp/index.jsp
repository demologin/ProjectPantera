<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Начало квеста</title>
    <style>
        body { font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; background: #f0f2f5; margin: 0; }
        .login-card { background: white; padding: 40px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); text-align: center; }
        input[type="text"] { padding: 10px; width: 250px; margin-bottom: 20px; border: 1px solid #ccc; border-radius: 4px; }
        button { padding: 10px 20px; background: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background: #0056b3; }
    </style>
</head>
<body>
<div class="login-card">
    <h1>Добро пожаловать в квест!</h1>
    <p>Введите ваше имя, чтобы начать путешествие:</p>
    <form action="start" method="POST">
        <input type="text" name="playerName" placeholder="Ваше имя" required>
        <br>
        <button type="submit">Начать игру</button>
    </form>
</div>
</body>
</html>