<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<body>
<h1><%= "Тестирование по по SOLID" %>
</h1>

<%--<form action="test-page2" method="post">--%>
<form action="test-page" method="post">
    <p>${sessionScope.question.text}</p>
    <ul>
        <c:forEach var="answer" items="${question.answers}">
            <div class="form-check">
                <input class="form-check-input" type="radio" name="answer" value="${answer.id},${answer.nextQuestionId}"
                       id="answer${answer.id}">
                <label class="form-check-label" for="answer${answer.id}">
                        ${answer.text}
                </label>
            </div>
        </c:forEach>
    </ul>

    <button type="submit">Далее</button>
</form>

<br/>
</body>


