<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добро пожаловать в мир квестов</title>
</head>
<body>
    <h1>Здесь начинается ваше приключение!"</h1>

    <form method="post" action="/quest-dragon">
        <input type="hidden" name="quest" value="the way of the dragon rider">
        <button type="submit">Начать квест</button>
    </form>

<p>${message}</p>
</body>
</html>
