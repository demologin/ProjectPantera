<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="result-box">

    <h2 class="${requestScope.result == 'win' ? 'result-win' : 'result-lose'}">
        <c:choose>
            <c:when test="${requestScope.result == 'win'}">
                Победа!
            </c:when>
            <c:otherwise>
                Поражение
            </c:otherwise>
        </c:choose>
    </h2>

    <p>
        <c:choose>
            <c:when test="${requestScope.result == 'win'}">
                Квест успешно завершён
            </c:when>
            <c:otherwise>
                Квест завершён досрочно
            </c:otherwise>
        </c:choose>
    </p>

    <p>Возврат к выбору квеста...</p>

</div>

<script>
    setTimeout(() => window.location.href = "quests", 2500);
</script>