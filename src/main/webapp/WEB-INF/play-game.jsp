<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
        <div class="col col-lg-4">
            <img src="images/background-main.png" width="400" height="400" alt="space">
        </div>
        <div class="col-md-6 col-lg-8 offset-lg-0">
            <h1>${requestScope.quest.title}</h1>

            <p class="mb-4">${requestScope.quest.text}</p>

            <c:choose>
                <c:when test="${requestScope.state}">
                        <div class="alert ${requestScope.winning ? 'alert-success' : 'alert-danger'} text-center">
                            <h2 class="mb-4">${requestScope.game.gameState.currentQuestion.text}</h2>
                            <a href="<c:url value='/home'/>" class="btn btn-primary me-2">Главная</a>
                            <a href="<c:url value='/play-game?questId=${requestScope.quest.id}'/>"
                               class="btn btn-outline-primary">Начать заново</a>
                        </div>
                </c:when>

                <c:otherwise>
                    <form method="post" action="<c:url value='/play-game'/>" class="mt-3">
                        <input type="hidden" name="gameId" value="${requestScope.game.id}"/>
                        <input type="hidden" name="questId" value="${requestScope.quest.id}"/>

                        <fieldset>
                            <legend>Текущий вопрос</legend>

                            <div class="form-group row mb-3">
                                <label class="col-md-4 col-form-label">
                                    <b>${requestScope.game.gameState.currentQuestion.text}</b>
                                </label>
                                <div class="col-md-8">
                                    <!-- Варианты ответов -->
                                    <c:forEach var="answer"
                                               items="${requestScope.game.gameState.currentQuestion.answers}">
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
                                Далее
                            </button>
                        </fieldset>
                    </form>
                </c:otherwise>
            </c:choose>

            <c:if test="${not empty sessionScope.error}">
                <div class="alert alert-danger mt-3">${sessionScope.error}</div>
                <% session.removeAttribute("error"); %>
            </c:if>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>
</body>
</html>
