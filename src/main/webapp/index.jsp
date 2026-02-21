<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Поиск сокровищ в замке</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            margin: 0;
            padding: 0;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .container {
            background-color: white;
            border-radius: 10px;
            padding: 40px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.1);
            max-width: 600px;
            width: 90%;
        }
        h1 {
            color: #333;
            margin-bottom: 20px;
            text-align: center;
        }
        .story {
            background-color: #f5f5f5;
            border-left: 4px solid #764ba2;
            padding: 20px;
            margin-bottom: 30px;
            line-height: 1.6;
            color: #555;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #555;
            font-weight: bold;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box;
        }
        button {
            background-color: #764ba2;
            color: white;
            border: none;
            padding: 12px 30px;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
            display: block;
            width: 100%;
        }
        button:hover {
            background-color: #5a3d7c;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Поиск сокровищ в заброшенном замке</h1>

    <div class="story">
        <h3>Легенда</h3>
        <p>Глубоко в лесах Трансильвании стоит древний замок графа Дракулы.
            Говорят, что в его подземельях спрятаны несметные сокровища, но
            каждый, кто пытался их найти, исчезал бесследно...</p>

        <p>Сегодня вы решили бросить вызов судьбе и отправиться на поиски
            сокровищ. Сможете ли вы пережить эту ночь и найти золото, или
            станете еще одной жертвой проклятого замка?</p>

        <p><strong>Приготовьтесь к приключениям! Каждое ваше решение
            может стать решающим.</strong></p>
    </div>

    <form action="${pageContext.request.contextPath}/start" method="post">
        <div class="form-group">
            <label for="playerName">Как вас зовут, искатель приключений?</label>
            <input type="text" id="playerName" name="playerName" required
                   placeholder="Введите ваше имя">
        </div>
        <button type="submit">Начать игру</button>
    </form>
</div>
</body>
</html>