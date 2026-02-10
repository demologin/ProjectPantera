<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${step.title}</title>
    <style>
        /* Глобальный сброс: паддинги больше не ломают ширину */
        *, *:before, *:after {
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background: #121212;
            color: #e0e0e0;
            display: flex;
            justify-content: center;
            min-height: 100vh;
        }

        .container {
            max-width: 800px;
            width: 95%; /* Чуть больше гибкости для мобильных */
            background: #1e1e1e;
            padding: 30px 40px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.5);
            border: 1px solid #333;
            margin: auto; /* Центрирует контейнер по вертикали и горизонтали */

            /* Резиновая высота */
            display: flex;
            flex-direction: column;
            height: auto;
            min-height: min-content;
        }

        .stats-panel {
            text-align: right;
            color: #888;
            margin-bottom: 20px;
            font-size: 0.85em;
            text-transform: uppercase;
            letter-spacing: 1px;
            border-bottom: 1px solid #333;
            padding-bottom: 10px;
            flex-shrink: 0; /* Чтобы панель не сжималась */
        }

        .stats-panel strong {
            color: #4ca1af;
        }

        h1 {
            color: #ffffff;
            margin-top: 0;
            font-size: 1.8em;
            text-align: center;
        }

        .description {
            font-size: 1.1em;
            margin: 20px 0;
            color: #ccc;
            text-align: left; /* Убрал justify, чтобы не было дыр в тексте */
        }

        .quest-image-container {
            width: 100%;
            text-align: center;
            margin-bottom: 25px;
            border-radius: 10px;
            overflow: hidden;
            border: 2px solid #333;
            background: #000;
            flex-shrink: 0;
        }

        .quest-image {
            width: 100%; /* Картинка всегда на всю ширину рамки */
            height: auto;
            display: block;
            transition: transform 0.5s ease;
        }

        .quest-image:hover {
            transform: scale(1.03);
        }

        .answers {
            display: flex;
            flex-direction: column;
            gap: 15px;
            margin-top: auto; /* Прижимает блок ответов к низу, если контента мало */
            padding-top: 20px;
            padding-bottom: 10px;
        }

        .answer-item {
            opacity: 0;
            transform: translateY(10px);
            width: 100%;
        }

        .btn-choice {
            display: block;
            width: 100%;
            background: linear-gradient(135deg, #2c3e50, #4ca1af);
            color: white;
            padding: 16px 20px;
            border-radius: 10px;
            text-decoration: none;
            font-weight: 600;
            text-align: center;
            box-shadow: 0 4px 15px rgba(0,0,0,0.2);
            border: 1px solid rgba(255,255,255,0.1);
            transition: all 0.3s ease;
        }

        .btn-choice:hover {
            background: linear-gradient(135deg, #3e5871, #5dbbc8);
            transform: scale(1.01);
            box-shadow: 0 6px 20px rgba(76, 161, 175, 0.3);
        }

        .btn-restart {
            background: linear-gradient(135deg, #8e2e2e, #c0392b);
        }

        .btn-restart:hover {
            background: linear-gradient(135deg, #a93232, #e74c3c);
        }

        hr {
            border: 0;
            border-top: 1px solid #333;
            margin: 20px 0;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="stats-panel">
        Игрок: <strong>${sessionScope.player.name}</strong> |
        Экспедиций: <strong>${sessionScope.player.gamesPlayed}</strong>
    </div>

    <h1>${step.title}</h1>

    <c:if test="${not empty step.imagePath}">
        <div class="quest-image-container">
            <img src="${pageContext.request.contextPath}/${step.imagePath}"
                 class="quest-image"
                 alt="${step.title}">
        </div>
    </c:if>

    <div class="description">
        <p>${step.description}</p>
    </div>

    <hr>

    <div class="answers">
        <c:choose>
            <c:when test="${empty step.answers}">
                <div style="text-align: center;">
                    <h2 style="color: #e74c3c; letter-spacing: 2px;">ФИНАЛ</h2>
                    <div class="answer-item" style="opacity: 1; transform: none;">
                        <a href="index.jsp" class="btn-choice btn-restart">ПОКИНУТЬ ДЖУНГЛИ</a>
                    </div>
                </div>
            </c:when>

            <c:otherwise>
                <c:forEach var="answer" items="${step.answers}">
                    <div class="answer-item">
                        <a href="logic?id=${answer.nextStepId}" class="btn-choice">
                                ${answer.text}
                        </a>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        const items = document.querySelectorAll('.answer-item');

        items.forEach((item, index) => {
            setTimeout(() => {
                item.style.opacity = '1';
                item.style.transform = 'translateY(0)';
            }, index * 150);
        });

        const links = document.querySelectorAll('.btn-choice');
        links.forEach(link => {
            link.addEventListener('click', function() {
                // Добавляем эффект нажатия
                this.style.transform = 'scale(0.98)';
                document.querySelector('.container').style.opacity = '0.8';
            });
        });
    });
</script>

</body>
</html>