<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Bold-BS4-Full-Page-Image-Header.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Hero-Features-icons.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/bootstrap/css/bootstrap.min.css'/>">
<html>
<head>
    <title>${requestScope.quest.title}</title>
</head>
<body>
<div class="container m-4" style="height: 100vh;">
    <div class="row" style="margin-top: 100px;">
        <div class="col col-lg-4"><img class="" src="images/background-main.png" width="400" height="400" alt="space"></div>
        <div class="col-md-6 col-lg-8 offset-lg-0">
            <h1>${requestScope.quest.title}</h1>

            <p class="mb-4">${requestScope.quest.text}</p>
            <c:if test="${empty requestScope.question and empty requestScope.gameOver}">
                <div class="button-group">
                    <div>
                        <a href="${pageContext.request.contextPath}/play-game?questId=${requestScope.quest.id}&questionId=${requestScope.quest.startQuestionId}"
                           class="btn btn-primary fs-5 me-2 px-4 py-2">Начать</a>
                    </div>
                    <div>
                        <a href="${pageContext.request.contextPath}/home"
                           class="btn btn-outline-primary fs-5 px-4 py-2">Назад к выбору квеста</a>
                    </div>
                </div>

            </c:if>

            <c:if test="${not empty requestScope.gameOver}">
                <div class="alert alert-success text-center">
                    <h3>Игра завершена! Спасибо за участие.</h3>
                    <a href="<c:url value='/home'/>" class="btn btn-primary">Вернуться на главную</a>
                </div>
            </c:if>

            <c:if test="${empty requestScope.gameOver and not empty requestScope.question}">
                <c:choose>
                    <c:when test="${requestScope.noAnswers}">
                        <div class="question-text" style="font-size: 20px; font-weight: bold; color: #0b5ed7">
                            <b>${requestScope.question.text}</b>
                        </div>

                        <div class="button-group">
                            <form method="GET" action="<c:url value='/play-game'/>" style="display:inline; margin-bottom: 10px">
                                <input type="hidden" name="questId" value="${requestScope.quest.id}"/>
                                <button type="submit" class="btn btn-primary fs-5 me-2 px-4 py-2">
                                    Начать заново
                                </button>
                            </form>

                            <form method="GET" action="<c:url value='/home'/>">
                                <button type="submit" class="btn btn-outline-primary fs-5 px-4 py-2">
                                    Выбрать новый квест
                                </button>
                            </form>
                        </div>
                    </c:when>

                    <c:otherwise>
                        <form method="POST" action="<c:url value='/play-game'/>" class="mt-3">
                            <input type="hidden" name="questId" value="${requestScope.quest.id}"/>
                            <fieldset>
                                <legend>Вопрос</legend>

                                <div class="form-group row mb-3">
                                    <label class="col-md-4 col-form-label">
                                        <b>${requestScope.question.text}</b>
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
                                                <label class="form-check-label mb-3" for="answer-${answer.id}">
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
            <c:if test="${not empty sessionScope.error}">
                <div>${sessionScope.error}</div>
            </c:if>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>

</body>
</html>
