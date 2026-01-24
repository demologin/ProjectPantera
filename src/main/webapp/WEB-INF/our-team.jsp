<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<body>
<div class="container py-4 py-xl-5">
    <div class="row mb-4 mb-lg-5">
        <div class="col-md-8 col-xl-6 text-center mx-auto">
            <h2>Our Team</h2>
            <p class="w-lg-50">Команда работающая над разработкой сайта</p>
        </div>
    </div>
    <div class="row gy-4 row-cols-2 row-cols-md-4">
        <div class="col">
            <div class="card border-0 shadow-none">
                <div class="card-body text-center d-flex flex-column align-items-center p-0"><img class="rounded-circle mb-3 fit-cover" width="130" height="130" src="images/analytic" loading="eager">
                    <h5 class="fw-bold text-primary card-title mb-0"><strong>Дмитрий Гончаров</strong></h5>
                    <p class="text-muted card-text mb-2">Аналитик</p>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card border-0 shadow-none">
                <div class="card-body text-center d-flex flex-column align-items-center p-0"><img class="rounded-circle mb-3 fit-cover" width="130" height="130" src="images/developer.png" loading="eager">
                    <h5 class="fw-bold text-primary card-title mb-0"><strong>Дмитрий Гончаров</strong></h5>
                    <p class="text-muted card-text mb-2">Разработчик</p>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card border-0 shadow-none">
                <div class="card-body text-center d-flex flex-column align-items-center p-0"><img class="rounded-circle mb-3 fit-cover" width="130" height="130" src="images/architecor.png" loading="eager">
                    <h5 class="fw-bold text-primary card-title mb-0"><strong>Дмитрий Гончаров</strong></h5>
                    <p class="text-muted card-text mb-2">Архитектор</p>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card border-0 shadow-none">
                <div class="card-body text-center d-flex flex-column align-items-center p-0"><img class="rounded-circle mb-3 fit-cover" width="130" height="130" src="images/qa.jpg" loading="eager">
                    <h5 class="fw-bold text-primary card-title mb-0"><strong>Дмитрий Гончаров</strong></h5>
                    <p class="text-muted card-text mb-2">Владелец продукта</p>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
<%@include file="parts/footer.jsp" %>