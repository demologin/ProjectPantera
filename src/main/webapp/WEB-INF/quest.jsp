<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Игра</title>
    <link rel="stylesheet" href="../static/style.css">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
</head>
<body>
    <a href="main-menu" class="btn btn-back">Вернуться в меню</a>
    <div class="container">
        <p>
            <c:out value="${fn:escapeXml(currentQuest.text)}"/>
        </p>
        <c:forEach var="option" items="${currentQuest.options}">
            <button class="btn" onclick="window.location='/next-stage?nextNodeId=${option.nextNodeId}'">
                <c:out value="${fn:escapeXml(option.text)}"/>
            </button>
        </c:forEach>
        <c:if test="${currentQuest.hasPreviousStage()}">
            <button class="btn" style="margin-top: 40px; background: none; border: 1px dashed #666;" onclick="window.location='/previous-stage'">
                Вернуться на предыдущий этап (-1 свеча)
            </button>
        </c:if>
		<div class="candles-info">
            🕯️ Свечи:
            <span>
                <c:out value="${currentQuest.candleCount}"/>
            </span>
        </div>
    </div>
    <script>
        // let candles = 3;
        //
        // function updateUI() {
        //     const candleCountSpan = document.getElementById('candle-count');
        //     const undoBtn = document.getElementById('undo-btn');
        //
        //     candleCountSpan.innerText = candles;
        //
        //     // Если свечей 0, скрываем кнопку возврата
        //     if (candles <= 0) {
        //         undoBtn.style.display = 'none';
        //     }
        // }
        //
        // document.getElementById('undo-btn').onclick = () => {
        //     if (candles > 0) {
        //         candles--;
        //         updateUI();
        //         // Здесь должна быть логика возврата к предыдущему состоянию
        //     }
        // };
        //
        // updateUI();
    </script>
</body>
</html>