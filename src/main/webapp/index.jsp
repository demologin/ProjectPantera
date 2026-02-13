<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            min-height: 100vh;
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
        h1 {
            color: #ffffff;
            margin-bottom: 20px;
        }
        input[type="text"] {
            width: 100%;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 8px;
            border: 1px solid #444;
            background: #2a2a2a;
            color: white;
            font-size: 1em;
            box-sizing: border-box;
            outline: none;
        }
        input[type="text"]:focus {
            border-color: #27ae60;
        }
        .avatar-label {
            display: block;
            margin-bottom: 10px;
            font-size: 0.8em;
            color: #888;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
        .avatar-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 12px;
            margin-bottom: 30px;
            background: #161616;
            padding: 15px;
            border-radius: 10px;
        }
        .avatar-item {
            cursor: pointer;
            position: relative;
        }
        .avatar-item input {
            display: none;
        }
        .avatar-img {
            width: 100%;
            aspect-ratio: 1 / 1;
            border-radius: 50%;
            border: 3px solid #333;
            transition: all 0.3s ease;
            background: #222;
            object-fit: cover;
        }
        .avatar-item input:checked + .avatar-img {
            border-color: #27ae60;
            box-shadow: 0 0 15px rgba(39, 174, 96, 0.4);
            transform: scale(1.1);
        }
        .btn-start {
            display: block;
            background: linear-gradient(135deg, #27ae60, #2ecc71);
            color: white;
            padding: 15px;
            border-radius: 8px;
            font-weight: bold;
            border: none;
            cursor: pointer;
            width: 100%;
            font-size: 1em;
            transition: 0.3s;
        }
        .btn-start:hover {
            filter: brightness(1.1);
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Начало экспедиции</h1>
    <form action="${pageContext.request.contextPath}/init" method="POST">
        <label for="playerName" class="avatar-label">Имя исследователя</label>
        <input type="text" id="playerName" name="playerName" placeholder="Введите ваше имя..." required>

        <span class="avatar-label">Выберите облик героя</span>
        <div class="avatar-grid">
            <c:forEach var="i" begin="1" end="9">
                <label class="avatar-item">
                    <input type="radio" name="avatar" value="static/images/avatars/${i}.png" ${i == 1 ? 'checked' : ''}>
                    <img src="${pageContext.request.contextPath}/static/images/avatars/${i}.png" class="avatar-img" alt="Аватар ${i}">
                </label>
            </c:forEach>
        </div>
        <button type="submit" class="btn-start">ВОЙТИ В ДЖУНГЛИ</button>
    </form>
</div>
</body>
</html>