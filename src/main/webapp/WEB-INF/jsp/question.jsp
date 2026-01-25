<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<c:import url="/WEB-INF/jsp/common/header.jsp"/>

<div class="container">

    <div class="test-topics">
        <strong>Темы:</strong>
        <ul>
            <c:forEach var="topic" items="${topics}">
                <li>${topic.displayName}</li>
            </c:forEach>
        </ul>
    </div>

    <div class="question-header">
        <h2>Вопрос ${questionNumber} из ${totalQuestions}</h2>
    </div>

    <div class="question-body">
        <p class="question-text">
            ${question.questionText}
        </p>
    </div>

    <form method="post" action="${pageContext.request.contextPath}/question" class="answer-form">

        <c:forEach var="answer" items="${question.answers}" varStatus="status">
            <label class="answer-option">
                <input type="radio"
                       name="answerIndex"
                       value="${status.index}"
                       required />
                <span>${answer}</span>
            </label>
        </c:forEach>

        <button type="submit">Ответить</button>
    </form>

</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp"/>

