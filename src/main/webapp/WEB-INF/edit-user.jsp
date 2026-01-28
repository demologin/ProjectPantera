<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<div class="container py-4">
    <form method="post">
        <fieldset>

            <!-- Form Name -->
            <legend class="mb-4">Edit user:</legend>

            <!-- Text input-->
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label" for="login">Login</label>
                <div class="col-sm-6">
                    <input
                            id="login"
                            name="login"
                            type="text"
                            value="${requestScope.user.login}"
                            placeholder="your login"
                            class="form-control"
                            required="">
                    <div class="form-text">min 3 symbols</div>
                </div>
            </div>

            <!-- Password input-->
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label" for="password">Password</label>
                <div class="col-sm-6">
                    <input id="password"
                           name="password"
                           type="password"
                           value="${requestScope.user.password}"
                           placeholder="your password"
                           class="form-control"
                           required="">
                    <div class="form-text">min 8 symb</div>
                </div>
            </div>


            <!-- Select Basic -->
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label" for="role">Role</label>
                <div class="col-sm-6">
                    <select id="role" name="role" class="form-control">
                        <c:forEach var="role" items="${applicationScope.roles}">
                            <option value="${role}" ${role==requestScope.user.role?"selected":""}>${role}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <!-- Button (Double) -->
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label" for="create">Operation</label>
                <div class="col-sm-6">
                    <c:if test="${requestScope.user==null}">
                        <button id="create" name="create" class="btn btn-success">Create</button>
                    </c:if>
                    <c:if test="${requestScope.user!=null}">
                        <button id="update" name="update" class="btn btn-primary">Update</button>
                    </c:if>

                </div>
            </div>

        </fieldset>
    </form>
</div>
<%@include file="parts/footer.jsp" %>

