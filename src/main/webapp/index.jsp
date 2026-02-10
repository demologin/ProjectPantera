<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Экспедиция в джунгли</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #121212;
            color: #e0e0e0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            max-width: 500px;
            width: 90%;
            background: #1e1e1e;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.5);
            text-align: center;
            border: 1px solid #333;
        }
        h1 { color: #ffffff; margin-bottom: 30px; }
        input[type="text"] {
            width: 100%;
            padding: 15px;
            margin-bottom: 25px;
            border-radius: 8px;
            border: 1px solid #444;
            background: #2a2a2a;
            color: white;
            font-size: 1em;
            box-sizing: border-box;
        }
        .btn-start {
            display: block;
            background: linear-gradient(135deg, #27ae60, #2ecc71);
            color: white;
            padding: 15px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
            border: none;
            cursor: pointer;
            width: 100%;
        }
        .btn-start:hover { background: linear-gradient(135deg, #2ecc71, #27ae60); }
    </style>
</head>
<body>
<div class="container">
    <h1>Начало экспедиции</h1>
    <form action="init" method="POST">
        <input type="text" name="playerName" placeholder="Введите ваше имя..." required>
        <button type="submit" class="btn-start">ВОЙТИ В ДЖУНГЛИ</button>
    </form>
</div>
</body>
</html>