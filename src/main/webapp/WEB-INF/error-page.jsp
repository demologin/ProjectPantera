<%--
  Created by IntelliJ IDEA.
  User: ushan
  Date: 26.02.2026
  Time: 1:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="header.jsp" %>

<div class="container">
    <div class="card result-lose">
        <div class="error-code">
            <c:choose>
                <c:when test="${pageContext.errorData.statusCode != 0}">
                    ${pageContext.errorData.statusCode}
                </c:when>
                <c:otherwise>Ошибка</c:otherwise>
            </c:choose>
        </div>
        <div class="card-title">Системный сбой</div>
        <div class="quest-text">
            Бортовой компьютер зафиксировал критическую ошибку системы навигации.
            Запрошенный маршрут не существует или временно недоступен.
        </div>
        <a href="${pageContext.request.contextPath}/"
           style="display: block; text-align: center; color: #7eb8f7; margin-top: 20px; text-decoration: none;">
            ← Вернуться на главную
        </a>
    </div>
</div>

</body>
</html>

