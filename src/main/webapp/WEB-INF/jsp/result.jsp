<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<c:import url="/WEB-INF/jsp/common/header.jsp"/>

<div class="container">

    <h2>Результат теста</h2>

    <p>
        <strong>Темы теста:</strong>
        ${topics}
    </p>

    <p>
        <strong>Результат:</strong>
        ${correct} из ${total} правильных ответов
    </p>

    <c:choose>
        <c:when test="${passed}">
            <p class="result-success">
                Поздравляем! Вы успешно прошли тест 🎉
            </p>
            <p class="result-hint">
                Оффер уже летит к вам… ну, по крайней мере моральный.
            </p>
        </c:when>
        <c:otherwise>
            <p class="result-fail">
                К сожалению, тест не пройден
            </p>
            <p class="result-hint">
                Это учебный проект. Можно спокойно попробовать ещё раз.
            </p>
        </c:otherwise>
    </c:choose>

    <div class="actions">
        <a href="${pageContext.request.contextPath}/home">На главную</a>
        <a href="${pageContext.request.contextPath}/test/settings">Пройти новый тест</a>
    </div>

</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp"/>

