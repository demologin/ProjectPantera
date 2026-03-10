<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quest Game</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .answer-btn {
            width: 100%;
            margin-bottom: 10px;
            text-align: left;
        }
    </style>
</head>
<body>
<my:constants/>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">

            <!-- Статистика игр -->
            <div class="card mb-3">
                <div class="card-body text-center">
                    <h6 class="mb-0">📊 Сыграно игр: <strong>${requestScope[GAMES_PLAYED_ATTR]}</strong></h6>
                </div>
            </div>

            <!-- Основной контент -->
            <div class="card mb-4">
                <div class="card-body">

                    <!-- Результат игры (победа/поражение) -->
                    <c:if test="${not empty requestScope[WIN_ATTR]}">
                        <div class="alert ${requestScope[WIN_ATTR] == 'true' ? 'alert-success' : 'alert-danger'} mb-4">
                            <h5 class="alert-heading">
                                <c:if test="${requestScope[WIN_ATTR] == 'true'}">🎉 Победа!</c:if>
                                <c:if test="${requestScope[WIN_ATTR] == 'false'}">💀 Поражение</c:if>
                            </h5>
                            <p>${requestScope[CURRENT_QUESTION_ATTR].question}</p>
                        </div>
                    </c:if>

                    <!-- Активный вопрос -->
                    <c:if test="${empty requestScope[WIN_ATTR]}">
                    <h4 class="card-title mb-4">${requestScope[CURRENT_QUESTION_ATTR].question}</h4>
                    </c:if>

                    <!-- Варианты ответов -->
                    <c:if test="${empty requestScope[WIN_ATTR]}">
                        <form action="quest" method="post">
                            <input type="hidden" name="${requestScope[ACTION_PARAM]}" value="answer">

                            <c:forEach var="entry" items="${requestScope[CURRENT_QUESTION_ATTR].answers}">
                                <button type="submit"
                                        name="${requestScope[ANSWER_ID_PARAM]}"
                                        value="${entry.value.id}"
                                        class="btn btn-outline-primary answer-btn">
                                    ${entry.value.text}
                                </button>
                            </c:forEach>
                        </form>
                    </c:if>

                    <!-- Кнопки управления -->
                    <div class="row mt-4">
                        <div class="col-md-6">
                            <form action="quest" method="post">
                                <input type="hidden" name="${requestScope[ACTION_PARAM]}" value="restart">
                                <button type="submit" class="btn btn-warning w-100">🔄 Заново</button>
                            </form>
                        </div>
                        <div class="col-md-6">
                            <form action="quest" method="post">
                                <input type="hidden" name="${requestScope[ACTION_PARAM]}" value="exit">
                                <button type="submit" class="btn btn-secondary w-100">🚪 Выйти</button>
                            </form>
                        </div>
                    </div>

                </div>
            </div>

            <!-- Возврат на главную -->
            <div class="text-center">
                <a href="hello" class="btn btn-outline-dark">← Вернуться</a>
            </div>

        </div>
    </div>
</div>
</body>
</html>