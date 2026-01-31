<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<body>
<body>
<div class="position-absolute top-50 start-50 translate-middle w-100 px-3" style="max-width: 400px;">
    <div class="col">
        <div class="card border-0 shadow-none">
            <div class="card-body text-center d-flex flex-column align-items-center p-0">
                <form class="form-horizontal" method="post">
                    <p><strong>Удалить сообщение?</strong></p>
                    <br>
                    <div class="form-group">
                        <button id="delete" onclick="return confirm('Вы уверены?')"
                                name="action" value="delete" class="btn btn-danger">Удалить
                        </button>
                        <button id="cancel" name="action" value="cancel" class="btn btn-primary">Отмена</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<%@include file="parts/footer.jsp" %>
