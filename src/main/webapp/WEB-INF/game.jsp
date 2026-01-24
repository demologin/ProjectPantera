<%@ page contentType="text/html; charset=UTF-8" %>
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
        <div class="card shadow-lg border-0 rounded-4 rpg-card">

            <div class="card-body text-center p-4">

                <h5 class="mb-4 fw-semibold text-primary">
                    📜 Квест: ${sessionScope.questId}
                </h5>

                <div class="alert alert-primary rounded-pill shadow-sm mb-3">
                    🧙 ${sessionScope.currentPlayerLogin}, твой выбор определит судьбу…
                </div>

                <div class="alert alert-warning rounded-pill mb-4 shadow-sm">
                    🎮 Игр: ${player.gamesPlayed}
                    | 🏆 Побед: ${player.wins}
                    | 💀 Поражений: ${player.losses}
                </div>

                <p class="fs-5 fw-semibold mb-4">
                    ${scene.text}
                </p>

                <c:choose>

                    <c:when test="${not gameOver}">
                        <form method="get" action="game">
                            <div class="d-flex flex-wrap justify-content-center gap-3">
                                <c:forEach var="choice" items="${scene.choices}">
                                    <button type="submit"
                                            name="choice"
                                            value="${choice.id}"
                                            class="btn btn-outline-primary rounded-pill px-4 shadow-sm choice-btn">
                                            ${choice.text}
                                    </button>
                                </c:forEach>
                            </div>
                        </form>
                    </c:when>

                    <c:otherwise>
                        <div class="alert ${win ? 'alert-success win-anim' : 'alert-danger lose-anim'} mt-4 shadow-sm">
                            <h5 class="fw-bold mb-1">
                                    ${win ? '🏆 Победа!' : '☠ Поражение'}
                            </h5>
                            <p class="mb-0">
                                    ${win ? 'Приключение завершено.' : 'Судьба была сурова.'}
                            </p>
                        </div>

                        <form method="get" action="restart" class="mt-3">
                            <button class="btn btn-outline-secondary rounded-pill px-4 shadow-sm">
                                Начать заново
                            </button>
                        </form>
                    </c:otherwise>

                </c:choose>

            </div>
        </div>
    </div>

    <footer class="text-center py-3 text-muted small">
        <strong>Quest</strong> · v1.0 · by Andrew Lazareff
    </footer>

</div>

<style>
    .rpg-card {
        max-width: 880px;
        width: 100%;
        background: linear-gradient(180deg, #ffffff, #f8f4ea);
        animation: fadeIn .6s ease-out;
    }

    .choice-btn {
        transition: all .2s ease;
    }
    .choice-btn:hover {
        transform: translateY(-2px);
    }

    /* Победа */
    .win-anim {
        animation: winGlow .8s ease-out;
    }
    @keyframes winGlow {
        from {
            opacity: 0;
            transform: scale(0.95);
            box-shadow: 0 0 0 rgba(25,135,84,0);
        }
        to {
            opacity: 1;
            transform: scale(1);
            box-shadow: 0 0 25px rgba(25,135,84,0.4);
        }
    }

    /* Поражение */
    .lose-anim {
        animation: loseShake .5s ease-in-out;
    }
    @keyframes loseShake {
        0% { transform: translateX(0); }
        20% { transform: translateX(-6px); }
        40% { transform: translateX(6px); }
        60% { transform: translateX(-4px); }
        80% { transform: translateX(4px); }
        100% { transform: translateX(0); }
    }

    @keyframes fadeIn {
        from { opacity: 0; transform: translateY(12px); }
        to   { opacity: 1; transform: translateY(0); }
    }
</style>

</body>
</html>
