<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/style.css'/>">
<html>
<head>
    <title>Play Game</title>
</head>
<body>
<h1>${requestScope.quest.title}</h1>

<c:if test="${not empty requestScope.gameOver}">
    <div class="alert alert-success text-center">
        <h3>Игра завершена! Спасибо за участие.</h3>
        <a href="<c:url value='/home'/>" class="btn btn-primary">Вернуться на главную</a>
    </div>
</c:if>

<c:if test="${empty requestScope.gameOver and not empty requestScope.question}">
    <c:choose>
        <c:when test="${requestScope.noAnswers}">
            <div class="question-text">
                    ${requestScope.question.text}
            </div>

            <div class="button-group">
                <form method="GET" action="<c:url value='/play-game'/>" style="display:inline;">
                    <input type="hidden" name="id" value="${requestScope.quest.id}"/>
                    <button type="submit" class="btn btn-warning">
                        Начать заново
                    </button>
                </form>

                <form method="GET" action="<c:url value='/home'/>">
                    <button type="submit" class="btn btn-secondary">
                        Выбрать новый квест
                    </button>
                </form>

<%--                <a href="<c:url value='/home'/>" class="btn btn-secondary">--%>
<%--                    Выбрать новый квест--%>
<%--                </a>--%>
            </div>
        </c:when>

        <c:otherwise>
            <form method="POST" action="<c:url value='/play-game'/>" class="mt-3">
                <input type="hidden" name="questId" value="${requestScope.quest.id}"/>
                <fieldset>
                    <legend>Вопрос № ${requestScope.question.label}</legend>

                    <div class="form-group row mb-3">
                        <label class="col-md-4 col-form-label">
                                ${requestScope.question.text}
                        </label>
                        <div class="col-md-8">
                            <c:forEach var="answer" items="${requestScope.answers}">
                                <div class="form-check">
                                    <input class="form-check-input"
                                           type="radio"
                                           name="selectedAnswerId"
                                           id="answer-${answer.id}"
                                           value="${answer.id}"
                                           required>
                                    <label class="form-check-label" for="answer-${answer.id}">
                                            ${answer.text}
                                    </label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-success">
                        Выбрать
                    </button>
                </fieldset>
            </form>
        </c:otherwise>
    </c:choose>
</c:if>

<c:if test="${empty requestScope.question and empty requestScope.gameOver}">
    <div class="alert alert-danger">
        Вопрос не найден. Проверьте ID.
    </div>
    <a href="<c:url value='/home'/>" class="btn btn-secondary">Вернуться</a>
</c:if>

</body>
</html>
