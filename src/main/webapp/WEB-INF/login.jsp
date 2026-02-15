<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<section class="position-relative py-4 py-xl-5">
    <div class="container">
        <div class="row d-flex justify-content-center">
            <div class="col-md-6 col-xl-4">
                <div class="card mb-5">
                    <div class="card-body d-flex flex-column align-items-center">
                        <h2>Login</h2>
                        <form class="text-center" method="post" action="login">
                            <p class="w-lg-50">Укажите данные для входа</p>
                            <div class="mb-3"><input class="form-control" type="text" name="login" placeholder="Login" value="Carl"></div>
                            <div class="mb-3"><input class="form-control" type="password" name="password" placeholder="Password" value="admin"></div>
                            <div class="mb-3"><button class="btn btn-primary d-block w-100" type="submit">Login</button></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="parts/footer.jsp" %>
