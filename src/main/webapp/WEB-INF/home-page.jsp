<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Квесты</title>
</head>
<body>
    <h2>Здесь начинается ваше приключение!"</h2>
    <h3>Выберите квест чтобы продолжить</h3>

    <form method="post" action="/quest-dragon">
        <input type="hidden" name="quest" value="the way of the dragon rider">
        <button type="submit">Как приручить дракона</button>
    </form>

<p>${message}</p>
</body>
</html>
