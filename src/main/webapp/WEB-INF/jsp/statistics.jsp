<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h2>Статистика прохождения квестов</h2>

<table class="admin-table">
    <tr>
        <th>Пользователь</th>
        <th>Квест</th>
        <th>Победы</th>
        <th>Поражения</th>
    </tr>

    <c:forEach var="s" items="${requestScope.stats}">
        <tr>
            <td>${s.login}</td>
            <td>${s.questId}</td>
            <td class="win">${s.wins}</td>
            <td class="lose">${s.loses}</td>
        </tr>
    </c:forEach>
</table>

<!-- ===== ЕСЛИ СТАТИСТИКИ НЕТ ===== -->
<c:if test="${requestScope.stats.size() == 0}">
    <p style="text-align:center; font-style:italic; margin-top:20px;">
        Не сыграно ни одного квеста
    </p>
</c:if>