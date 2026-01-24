<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Главное меню — Квесты</title>
    <link rel="stylesheet" href="../static/style.css">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
    <div class="container">
        <h1>Выберите квест</h1>
        <div id="quest-list">
            <c:forEach var="availableQuest" items="${availableQuests}">
                <button class="btn">
                    <c:out value="${availableQuest}"/>
                </button>
            </c:forEach>
        </div>
        <div id="special-actions"></div>
        <button class="btn btn-create" onclick="window.location='/editor'">Создать квест</button>
    </div>
    <script>
        // Имитация данных от Backend
        const backendData = {
            availableQuests: ["Тень леса", "Забытый замок", "Проклятие пирата"],
            lastUnfinishedQuest: "Забытый замок" // Поставь null, если квеста нет
        };

        const questListContainer = document.getElementById('quest-list');
        const specialActions = document.getElementById('special-actions');

        // Вывод обычных квестов
        // backendData.availableQuests.forEach(name => {
        //     const btn = document.createElement('button');
        //     btn.className = 'btn';
        //     btn.innerText = name;
        //     questListContainer.appendChild(btn);
        // });

        // Кнопка продолжения (появляется только если есть данные)
        if (backendData.lastUnfinishedQuest) {
            const contBtn = document.createElement('button');
            contBtn.className = 'btn btn-continue';
            contBtn.innerText = 'Продолжить квест: ' + backendData.lastUnfinishedQuest;
            specialActions.appendChild(contBtn);
        }
    </script>
</body>
</html>