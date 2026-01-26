<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>



<c:import url="/WEB-INF/jsp/common/header.jsp"/>

<div class="container">

    <h2>Профиль пользователя</h2>

    <c:set var="user" value="${sessionScope.currentUser}"/>

    <div class="profile-section">
        <p><strong>Никнейм:</strong> ${user.nickname}</p>
        <p><strong>Email:</strong> ${user.email}</p>

        <p>
            <strong>О себе:</strong><br>
            <c:choose>
                <c:when test="${empty user.about}">
                    <em>Не указано</em>
                </c:when>
                <c:otherwise>
                    ${user.about}
                </c:otherwise>
            </c:choose>
        </p>

        <p>
            <strong>Аватар:</strong><br>
            <img class="avatar"
                 src="${pageContext.request.contextPath}${user.avatarPath}"
                 alt="Avatar">
        </p>

        <div class="profile-actions">
            <a href="${pageContext.request.contextPath}/profile/edit">Редактировать профиль</a>
            <a href="${pageContext.request.contextPath}/profile/avatar">Изменить аватар</a>
        </div>
    </div>

    <div class="profile-section">
        <h3>История прохождения тестов</h3>

        <c:if test="${empty results}">
            <p>Вы еще не проходили тесты.</p>
        </c:if>

        <c:if test="${not empty results}">
            <table class="data-table">
                <tr>
                    <th>Тема</th>
                    <th>Вопросов</th>
                    <th>Правильных</th>
                    <th>Результат</th>
                    <th>Дата</th>
                </tr>
                <c:forEach var="result" items="${results}">
                    <tr>
                        <td>${result.topicDisplayName}</td>
                        <td>${result.totalQuestions}</td>
                        <td>${result.correctAnswers}</td>
                        <td>
                            <c:choose>
                                <c:when test="${result.passed}">
                                    <span class="status-success">Пройден</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="status-fail">Не пройден</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${result.formattedFinishedAt}</td>                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>

    <div class="profile-section">
        <h3>Успешность по темам</h3>

        <c:if test="${empty topicStats}">
            <p>Пока нет данных для анализа.</p>
        </c:if>

        <c:if test="${not empty topicStats}">
            <table class="data-table">
                <tr>
                    <th>Тема</th>
                    <th>Попыток</th>
                    <th>Успешных</th>
                    <th>% успешности</th>
                </tr>
                <c:forEach var="stat" items="${topicStats}">
                    <tr>
                        <td>${stat.topicDisplayName}</td>
                        <td>${stat.total}</td>
                        <td>${stat.passed}</td>
                        <td>${stat.successRate}%</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>

    <div class="profile-section">
        <h3>Успешность тестов</h3>

        <c:if test="${empty testStats}">
            <p>Нет данных по тестам.</p>
        </c:if>

        <c:if test="${not empty testStats}">
            <table class="data-table">
                <tr>
                    <th>Тест</th>
                    <th>Попыток</th>
                    <th>Успешных</th>
                    <th>% успешности</th>
                </tr>
                <c:forEach var="stat" items="${testStats}">
                    <tr>
                        <td>${stat.testName}</td>
                        <td>${stat.total}</td>
                        <td>${stat.passed}</td>
                        <td>${stat.successRate}%</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>

</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp"/>

