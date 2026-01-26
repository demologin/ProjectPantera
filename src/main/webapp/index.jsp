<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Выбор приключения</title>
    <style>
        body { font-family: 'Courier New', monospace; background-color: #1c1c1c; color: #f0f0f0; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .container { background-color: #2a2a2a; padding: 40px; border-radius: 10px; border: 1px solid #444; box-shadow: 0 0 20px rgba(0,0,0,0.5); text-align: center; max-width: 600px; }
        h1 { color: #e60000; text-shadow: 2px 2px 4px #000; }
        select { padding: 10px; font-size: 16px; background: #333; color: white; border: 1px solid #555; border-radius: 5px; width: 100%; margin-bottom: 20px; }
        .btn { background-color: #e60000; color: white; border: none; padding: 15px 30px; font-size: 18px; cursor: pointer; border-radius: 5px; transition: 0.3s; width: 100%; }
        .btn:hover { background-color: #ff3333; }
        .user-info { margin-bottom: 20px; font-size: 18px; color: #aaa; }
        .logout { font-size: 12px; color: #666; text-decoration: none; display: block; margin-top: 15px; }
        .desc-box { text-align: left; background: #222; padding: 15px; margin-bottom: 20px; border-left: 3px solid #e60000; font-size: 0.9em; color: #ccc;}
    </style>
    <script>
        // Небольшой скрипт для смены описания при выборе квеста
        function updateDescription() {
            var select = document.getElementById("questSelect");
            var desc = document.getElementById("questDescription");
            var selectedValue = select.value;

            if (selectedValue === "Черная Орхидея") {
                desc.innerText = "Нуарный детектив. Вам предстоит раскрыть загадочное убийство профессора, балансируя между корпоративными интригами и безумными учеными.";
            } else if (selectedValue === "Петля времени") {
                desc.innerText = "Научная фантастика. Лаборатория, мертвый коллега и бесконечно повторяющийся день. Сможете ли вы разорвать цикл?";
            } else if (selectedValue === "Король Артур: Проклятый трон") {
                desc.innerText = "Фэнтези. Камелот в опасности. Магия, мечи и древнее зло. Спасите короля или погрузите королевство во тьму.";
            }
        }
    </script>
</head>
<body>

<%-- Проверка: если не залогинен, кидаем на логин --%>
<c:if test="${empty sessionScope.user}">
    <c:redirect url="login.jsp"/>
</c:if>

<div class="container">
    <h1>Добро пожаловать в Архив</h1>
    <div class="user-info">Агент: <strong>${sessionScope.user}</strong></div>

    <form method="post" action="start">
        <label for="questSelect" style="display:block; text-align:left; margin-bottom:5px;">Выберите миссию:</label>
        <select id="questSelect" name="questName" required onchange="updateDescription()">
            <option value="Черная Орхидея">Черная Орхидея</option>
            <option value="Петля времени">Петля времени</option>
            <option value="Король Артур: Проклятый трон">Король Артур: Проклятый трон</option>
        </select>

        <div id="questDescription" class="desc-box">
            Нуарный детектив. Вам предстоит раскрыть загадочное убийство профессора, балансируя между корпоративными интригами и безумными учеными.
        </div>

        <input type="submit" value="НАЧАТЬ МИССИЮ" class="btn">
    </form>

    <a href="login.jsp" class="logout">Сменить пользователя</a>
</div>
</body>
</html>