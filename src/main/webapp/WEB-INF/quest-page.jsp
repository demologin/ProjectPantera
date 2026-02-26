<%--
  Created by IntelliJ IDEA.
  User: ushan
  Date: 26.02.2026
  Time: 1:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="header.jsp" %>

<div class="container">

    <%--
        Определяем CSS-класс карточки в зависимости от статуса шага.
        step.status — это наш enum StepStatus (PLAYING, WIN, LOSE)
        В JSP мы обращаемся к нему через строку для сравнения.
    --%>
    <c:choose>
    <c:when test="${step.status == 'WIN'}">
    <div class="card result-win">
        <div class="result-icon">🏆</div>
        <div class="card-title">МИССИЯ ВЫПОЛНЕНА!</div>
        </c:when>
        <c:when test="${step.status == 'LOSE'}">
        <div class="card result-lose">
            <div class="result-icon">💀</div>
            <div class="card-title">МИССИЯ ПРОВАЛЕНА</div>
            </c:when>
            <c:otherwise>
            <div class="card">
                <div class="card-title">📍 Ситуация</div>
                </c:otherwise>
                </c:choose>

                <%-- Текст текущего шага квеста --%>
                <div class="quest-text">
                    ${step.description}
                </div>

                <%--
                    Если шаг финальный — показываем статистику и кнопку "Играть снова".
                    Если обычный — показываем кнопки с вариантами ответов.
                --%>
                <c:choose>
                    <c:when test="${step.completed}">
                        <%-- Финальный экран: статистика --%>
                        <div class="stats">
                            <p>Пилот: <span>${gameSession.playerName}</span></p>
                            <p>Игр сыграно: <span>${gameSession.gamesPlayed}</span></p>
                            <p>Побед: <span>${gameSession.gamesWon}</span></p>
                        </div>

                        <%--
                            Кнопка "Играть снова" отправляет POST /new-game без параметра playerName.
                            NewGameCommand увидит, что сессия уже есть, и просто сбросит прогресс.
                        --%>
                        <form action="${pageContext.request.contextPath}/new-game" method="post">
                            <button type="submit" class="btn-primary">
                                🔄 НАЧАТЬ ЗАНОВО
                            </button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <%-- Обычный шаг: варианты ответов --%>
                        <div class="options">
                                <%--
                                    Итерируемся по Map<String, Integer> options.
                                    entry.key — текст варианта (то, что видит игрок)
                                    entry.value — id следующего шага (нам он здесь не нужен)

                                    Каждая кнопка отправляет POST /choice с параметром option=текст_кнопки
                                --%>
                            <c:forEach var="entry" items="${step.options}">
                                <form action="${pageContext.request.contextPath}/choice" method="post">
                                    <input type="hidden" name="option" value="${entry.key}">
                                    <button type="submit" class="option-btn">
                                        ▶ ${entry.key}
                                    </button>
                                </form>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>

            </div><%-- конец .card --%>
        </div>

        </body>
        </html>
