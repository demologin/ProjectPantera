<<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quest</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body style="background: radial-gradient(circle at top, #f1e9d2, #d8cfc0);">

<div class="min-vh-100 d-flex flex-column">

    <div class="d-flex justify-content-center pt-5 flex-grow-1">
        <div class="card shadow-lg border-0 rounded-4"
             style="max-width: 880px; width: 100%; background: linear-gradient(180deg, #ffffff, #f8f4ea);">

            <div class="card-body text-center p-4">

                <h3 class="mb-4 fw-bold text-primary">
                    🧙 Выбор героя
                </h3>

                <!-- ❗ ОШИБКА -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">
                            ${error}
                    </div>
                </c:if>

                <!-- ===== СУЩЕСТВУЮЩИЙ ГЕРОЙ ===== -->
                <c:if test="${not empty players}">
                    <h5 class="fw-semibold mb-3">Существующий герой</h5>

                    <form method="post" action="${pageContext.request.contextPath}/login">

                        <select name="login" class="form-select mb-3 text-center" required>
                            <option value="" disabled selected>Выберите героя</option>
                            <c:forEach var="p" items="${players}">
                                <option value="${p.login}">
                                        ${p.login} — 🎮 ${p.gamesPlayed} | 🏆 ${p.wins} | 💀 ${p.losses}
                                </option>
                            </c:forEach>
                        </select>

                        <input type="password"
                               name="password"
                               class="form-control mb-3 text-center"
                               placeholder="Пароль"
                               required>

                        <select name="questId" class="form-select mb-4 text-center" required>
                            <option value="" disabled selected>Выберите квест</option>
                            <c:forEach var="q" items="${quests}">
                                <option value="${q}">${q}</option>
                            </c:forEach>
                        </select>

                        <button class="btn btn-primary rounded-pill px-4 shadow-sm"
                                name="action"
                                value="login">
                            Начать приключение
                        </button>
                    </form>

                    <hr class="my-4">
                </c:if>

                <!-- ===== НОВЫЙ ГЕРОЙ ===== -->
                <h5 class="fw-semibold mb-3">Новый герой</h5>

                <form method="post" action="${pageContext.request.contextPath}/login">

                    <input class="form-control mb-3 text-center"
                           name="login"
                           placeholder="Имя героя"
                           required>

                    <input type="password"
                           name="password"
                           class="form-control mb-3 text-center"
                           placeholder="Пароль"
                           required>

                    <select name="questId" class="form-select mb-4 text-center" required>
                        <c:forEach var="q" items="${quests}">
                            <option value="${q}">${q}</option>
                        </c:forEach>
                    </select>

                    <button class="btn btn-success rounded-pill px-4 shadow-sm"
                            name="action"
                            value="register">
                        Создать героя
                    </button>
                </form>

            </div>
        </div>
    </div>

    <footer class="text-center py-3 text-muted small">
        🧭 <strong>Quest</strong> · v1.0 · by Andrew Lazareff
    </footer>

</div>

</body>
</html>
