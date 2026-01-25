<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<c:import url="/WEB-INF/jsp/common/header.jsp"/>

<div class="container">

    <h2>Настройки теста</h2>

    <p>
        Выберите темы и количество вопросов.
        Вопросы подбираются случайным образом.
    </p>

    <form method="post" action="${pageContext.request.contextPath}/start">

        <div class="form-group">
            <h3>Темы теста</h3>
            <c:forEach var="topic" items="${topics}">
                <label>
                    <input type="checkbox"
                           name="topics"
                           value="${topic}">
                        ${topic.displayName}
                </label>
            </c:forEach>
        </div>

        <div class="form-group">
            <h3>Количество вопросов</h3>
            <label>
                <input type="radio" name="questionCount" value="10" required>
                10
            </label>
            <label>
                <input type="radio" name="questionCount" value="20">
                20
            </label>
            <label>
                <input type="radio" name="questionCount" value="30">
                30
            </label>
        </div>

        <button type="submit">Начать тестирование</button>

    </form>

</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp"/>

