<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <head>
        <title>Zombie Apocalypse: 2028</title>
        <link href="https://fonts.googleapis.com/css2?family=Creepster&family=Special+Elite&display=swap" rel="stylesheet">
        <style>
            .stats-container {
                background: rgba(0, 0, 0, 0.3);
                padding: 15px;
                border-radius: 5px;
                margin-top: 20px;
            }

            table th {
                text-transform: uppercase;
                font-size: 0.8rem;
                letter-spacing: 1px;
            }

            table td {
                font-size: 1rem;
            }
            body {
                font-family: 'Special Elite', cursive;
                background: radial-gradient(circle, #2c0000 0%, #000000 100%);
                background-attachment: fixed;
                color: #ecf0f1;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
                margin: 0;
                overflow-x: hidden;
            }

            /* Эффект помех на фоне */
            body::before {
                content: "";
                position: fixed;
                top: 0; left: 0; width: 100%; height: 100%;
                background: url('https://www.transparenttextures.com/patterns/stardust.png');
                opacity: 0.1;
                pointer-events: none;
            }

            .card {
                background: rgba(44, 62, 80, 0.85);
                width: 90%;
                max-width: 600px;
                padding: 40px;
                border-radius: 5px;
                border: 2px solid #c0392b;
                box-shadow: 0 0 30px rgba(192, 57, 43, 0.3);
                backdrop-filter: blur(5px);
                animation: fadeIn 1.5s ease-out;
            }

            h1 {
                font-family: 'Creepster', cursive;
                font-size: 3.5rem;
                color: #e74c3c;
                text-shadow: 3px 3px 5px black;
                margin-bottom: 20px;
                letter-spacing: 5px;
            }

            h2 { font-size: 1.4rem; line-height: 1.6; color: #bdc3c7; }
            p { font-size: 1.1rem; line-height: 1.5; margin: 20px 0; }

            .btn {
                display: block;
                width: 100%;
                margin: 15px 0;
                padding: 15px;
                background: #c0392b;
                color: white;
                text-decoration: none;
                text-transform: uppercase;
                font-weight: bold;
                border: none;
                border-radius: 0;
                transition: all 0.3s;
                cursor: pointer;
                box-sizing: border-box;
                border-left: 5px solid transparent;
            }

            .btn:hover {
                background: #e74c3c;
                border-left: 10px solid #fff;
                transform: translateX(5px);
                box-shadow: -5px 0 15px rgba(231, 76, 60, 0.5);
            }

            .btn-win {
                background: #27ae60;
                border-color: #2ecc71;
            }

            .btn-win:hover { background: #2ecc71; }

            input[type="text"] {
                width: 100%;
                padding: 12px;
                margin-bottom: 10px;
                background: #1a252f;
                border: 1px solid #e74c3c;
                color: white;
                font-family: 'Special Elite', cursive;
                text-align: center;
            }

            @keyframes fadeIn {
                from { opacity: 0; transform: translateY(20px); }
                to { opacity: 1; transform: translateY(0); }
            }

            /* Индикатор шага */
            .step-indicator {
                font-size: 0.8rem;
                color: #7f8c8d;
                text-transform: uppercase;
                margin-bottom: 10px;
            }
        </style>
    </head>
</head>
<body>
<div class="card">
    <c:choose>
        <%-- Шаг 0: Ввод имени --%>
        <c:when test="${state.step == 0}">
            <h1>*** ЗОМБИ АПОКАЛИПСИС ***</h1>
            <h2>В 2028 году после очередной пандемии люди начали превращаться в зомби, ты один из немногих кому повезло.
                Единственный шанс на спасение, это найти укрытие в старом заброшенном бункере, но до него стоит еще
                дойти сквозь серые безлюдные улицы населенные зомби...</h2>
            <p>Введи свое имя выживший, что бы начать путь к бункеру! :</p>
            <form action="quest" method="get">
                <input type="hidden" name="action" value="start">
                <input type="text" name="name" required>
                <button type="submit" class="btn">Начать игру</button>
            </form>
        </c:when>

        <%-- Шаг 1: Движение по городу --%>
        <c:when test="${state.step == 1}">
            <h2>Привет выживший, ${state.playerName}!</h2>
            <p>"Ты видишь открытое пространство в городе, как будешь двигаться?"</p>
            <a href="quest?action=answer&choice=trust" class="btn">"Пойду вдоль домов, буду действовать скрытно"</a>
            <a href="quest?action=answer&choice=run" class="btn">"Пойду прямо по открытому пространству, ведь ни кого
                нет в округе"</a>
        </c:when>
        <%-- Шаг 2: Нападение зомби --%>
        <c:when test="${state.step == 2}">
            <p>"Ты остановился в одном из домов для небольшой передышки и на тебя бежит зомби"</p>
            <a href="quest?action=answer&choice=truth" class="btn">"Убежать и спрятаться"</a>
            <a href="quest?action=answer&choice=lie" class="btn">"Вступить в драку"</a>
        </c:when>

        <%-- Шаг 3: Отдых --%>
        <c:when test="${state.step == 3}">
            <p>"Тебе удалось спастись от зомби, но пока ты прятался стало уже темно"</p>
            <a href="quest?action=answer&choice=truth" class="btn">"Найти место для ночевки"</a>
            <a href="quest?action=answer&choice=lie" class="btn">"Двигаться в сумерках. Ведь так будет безопаснее"</a>
        </c:when>

        <c:when test="${state.step == 4}">
            <p>"Ты проснулся и продолжил движение в бункер. Через некоторое время ты дошел до него. Охранник на воротах
                спрашивает: *Стой! Ты не заражен? Я вижу на тебе следы крови.*"</p>
            <a href="quest?action=answer&choice=truth" class="btn">"Я поранился пока шел сюда спасаясь от зомби, но я полностью чист"</a>
            <a href="quest?action=answer&choice=lie" class="btn">"Ты что кровь ни когда не видел? тут пораниться дело плевое"</a>
        </c:when>

        <%-- Шаг 4: Разговор с охранником --%>
        <c:when test="${state.step == 4}">
            <p>"Ты дошел до входа в бункер. Охранник целится в тебя из винтовки:
                *Стой! Ты не заражен? Я вижу на тебе следы крови.*"</p>
            <a href="quest?action=answer&choice=truth" class="btn">"Я поранился, пока шел сюда, но я полностью чист"</a>
            <a href="quest?action=answer&choice=lie" class="btn">"Ты что, кровь никогда не видел? Тут пораниться — дело плевое"</a>
        </c:when>

        <%-- Шаг 5: Проверка на человечность (Финал перед входом) --%>
        <c:when test="${state.step == 5}">
            <p>"Охранник опускает ствол, но не убирает палец со спускового крючка.
                *Ладно... Но правила одни для всех. Брось рюкзак и пройди через сканер.
                Если он запищит и на тебе буду следы укусов — я стреляю без предупреждения.*"</p>
            <a href="quest?action=answer&choice=scan" class="btn">"Согласиться на проверку"</a>
            <a href="quest?action=answer&choice=fight" class="btn">"Послать охранника"</a>
        </c:when>

        <%-- Финал: Победа (Шаг 6) --%>
        <c:when test="${state.step == 6}">
            <h2 style="color: #2ecc71">ПОБЕДА!</h2>
            <p>Сканер горит зеленым. Ты с облегчением выдыхаешь... Тяжелая стальная дверь со скрипом закрывается за твоей спиной.
                Снаружи слышны вопли тех, кто остался в темноте, но здесь есть еда, вода и кров. Ты спасен.
                Вы в безопасности, ${state.playerName}.</p>
            <p>Всего игр пройдено: ${state.gamesPlayed + 1}</p>
            <a href="quest?action=restart" class="btn btn-win">Начать новую историю</a>
        </c:when>

        <%-- Финал: Смерть --%>
        <c:when test="${state.step == -1}">
            <h2 style="color: #e74c3c">ВЫ ПОГИБЛИ</h2>
            <p>${state.deathReason}</p>
            <p>Всего попыток: ${state.gamesPlayed + 1}</p>
            <a href="quest?action=restart" class="btn">Попробовать еще раз</a>
        </c:when>
    </c:choose>
    <%-- Секция Глобальной Статистики --%>
    <hr style="border: 1px solid #c0392b; margin: 30px 0;">

    <div class="stats-container">
        <h3 style="font-family: 'Creepster', cursive; color: #e74c3c; font-size: 2rem;">Архив Выживших</h3>

        <table style="width: 100%; border-collapse: collapse; margin-top: 15px; font-family: 'Special Elite', cursive;">
            <thead>
            <tr style="border-bottom: 2px solid #c0392b; color: #bdc3c7;">
                <th style="padding: 10px; text-align: left;">Имя</th>
                <th style="padding: 10px;">Победы</th>
                <th style="padding: 10px;">Смерти</th>
                <th style="padding: 10px;">Всего</th>
            </tr>
            </thead>
            <tbody>
            <%-- Проходим циклом по нашей карте из ServletContext --%>
            <c:forEach var="entry" items="${applicationScope.globalStats}">
                <tr style="border-bottom: 1px dotted #34495e;">
                    <td style="padding: 10px; text-align: left; color: #fff; font-weight: bold;">
                            ${entry.key}
                    </td>
                    <td style="padding: 10px; color: #2ecc71;">
                            ${entry.value.wins}
                    </td>
                    <td style="padding: 10px; color: #e74c3c;">
                            ${entry.value.losses}
                    </td>
                    <td style="padding: 10px; color: #bdc3c7;">
                            ${entry.value.total}
                    </td>
                </tr>
            </c:forEach>

            <%-- Если статистика еще пуста --%>
            <c:if test="${empty applicationScope.globalStats}">
                <tr>
                    <td colspan="4" style="padding: 20px; color: #7f8c8d;">
                        Данные о выживших отсутствуют... пока что.
                    </td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>

</div>
</body>
</html>