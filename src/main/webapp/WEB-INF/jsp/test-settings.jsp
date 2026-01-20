<%--
  Created by IntelliJ IDEA.
  User: ZybinAV
  Date: 18.01.2026
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Настройки теста</title>
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jspf" %>
    <h2>Настройки теста</h2>
    <p>
        Выберите тему, по которой вы хотите пройти тестирование.
        В каждом тесте будет случайный набор вопросов.
    </p>
    <form method="post" action="${pageContext.request.contextPath}/start">
        <h3>Темы теста</h3>
        <c:forEach var="topic" items="${topics}">
            <label>
                <input type="checkbox"
                       name="topics"
                       value="${topic}" />
                       ${topic.displayName}
            </label>
            <br>
        </c:forEach>
        <h3>Количество вопросов</h3>
        <label>
            <input type="radio" name="questionCount" value="10" required/> 10
        </label>
        <label>
            <input type="radio" name="questionCount" value="20" required/> 20
        </label>
        <label>
            <input type="radio" name="questionCount" value="30" required/> 30
        </label>
        <br><br>
        <button type="submit">Начать тестирование</button>
       <%-- <div>
            <c:forEach var="topic" items="${topics}">
                <button
                    type="submit"
                    name="topic"
                    value="${topic}"
                    style="display: block; margin: 10px 0"
                    >
                    ${topic.displayName}
                </button>
            </c:forEach>
        </div>--%>
    </form>
<%@ include file="/WEB-INF/jsp/common/footer.jspf" %>
</body>
</html>
