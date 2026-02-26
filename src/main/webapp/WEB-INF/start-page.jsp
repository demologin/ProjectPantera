<%--
  Created by IntelliJ IDEA.
  User: ushan
  Date: 26.02.2026
  Time: 1:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- Подключаем общую шапку (HTML head + header тег) --%>
<%@ include file="header.jsp" %>

<div class="container">
    <div class="card">
        <div class="card-title">📡 Входящий сигнал</div>

        <%-- Предыстория квеста --%>
        <div class="intro-text">
            <p>
                <strong style="color: #7eb8f7;">Год 2157. Дальний космос.</strong>
            </p>
            <p>
                Ты — пилот разведывательного корабля <strong>«Аврора»</strong>, один из лучших
                в Федерации дальних исследований. Твоя текущая миссия — картографирование
                пояса астероидов в секторе Кеплера.
            </p>
            <p>
                Маршрут стандартный, ничего необычного. Ещё шесть часов до точки возврата,
                запасы топлива в норме, связь с базой устойчивая.
            </p>
            <p>
                И вдруг бортовой компьютер фиксирует слабый <strong style="color: #f0c060;">сигнал бедствия</strong>.
                Источник — заброшенная исследовательская станция <strong>«Кеплер-7»</strong>.
                По базе данных — станция была эвакуирована два года назад после аварии реактора.
            </p>
            <p>
                Но кто-то всё ещё там. И они зовут на помощь.
            </p>
            <p style="color: #7eb8f7; margin-top: 20px;">
                ⚠ Твои решения определят, выживут ли они.
            </p>
        </div>

        <%-- Форма ввода имени --%>
        <%-- Отображаем ошибку, если она есть --%>
        <c:if test="${not empty error}">
            <div class="error-message">⚠ ${error}</div>
        </c:if>

        <%--
            POST /new-game — создаёт новую игру.
            Поле playerName будет доступно в сервлете через request.getParameter("playerName")
        --%>
        <form action="${pageContext.request.contextPath}/new-game" method="post">
            <div class="form-group">
                <label for="playerName">Введите позывной пилота</label>
                <input type="text"
                       id="playerName"
                       name="playerName"
                       placeholder="Ваше имя..."
                       maxlength="30"
                       autocomplete="off"
                       required>
            </div>
            <button type="submit" class="btn-primary">
                🚀 НАЧАТЬ МИССИЮ
            </button>
        </form>
    </div>
</div>

</body>
</html>
