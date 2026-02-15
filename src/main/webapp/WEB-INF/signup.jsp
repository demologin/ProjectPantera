<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="parts/header.jsp" %>
<div class="container">
    <section class="position-relative py-4 py-xl-5">
        <div class="container">
            <div class="row d-flex justify-content-center">
                <div class="col-md-6 col-xl-4">
                    <div class="card mb-5">
                        <div class="card-body d-flex flex-column align-items-center">
                            <h2>Signup</h2>
                            <form class="text-center" method="post">
                                <p class="w-lg-50">Укажите данные для регистрации</p>
                                <div class="mb-3"><input class="form-control" type="text" name="login" placeholder="Login">
                                </div>
                                <div class="mb-3"><input class="form-control" type="password" name="password"
                                                         placeholder="Password"></div>
                                <div class="mb-3">
                                    <button class="btn btn-primary d-block w-100" type="submit">Signup</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<%@include file="parts/footer.jsp" %>
