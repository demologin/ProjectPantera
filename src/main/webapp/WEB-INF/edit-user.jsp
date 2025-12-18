<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit ${requestScope.user.login}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">

</head>
<body>
<div class="container">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
            crossorigin="anonymous"></script>

    <form class="form-horizontal">
        <fieldset>

            <!-- Form Name -->
            <legend>Edit user ${requestScope.user.login}</legend>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="login">Login</label>
                <div class="col-md-4">
                    <input id="login" value="${requestScope.user.login}"
                           name="login" type="text" placeholder="placeholder"
                           class="form-control input-md">
                    <span class="help-block">help</span>
                </div>
            </div>

            <!-- Password input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="password">Password</label>
                <div class="col-md-4">
                    <input id="password" value="${requestScope.user.password}"
                           name="password" type="password" placeholder="need not empty"
                           class="form-control input-md" required="">
                    <span class="help-block">3...32 symbols</span>
                </div>
            </div>

            <!-- Select Basic -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="role">Role</label>
                <div class="col-md-4">
                    <select id="role" name="role" class="form-control">
                        <option value="ADMIN">ADMIN</option>
                        <option value="USER">USER</option>
                        <option value="GUEST">GUEST</option>
                    </select>
                </div>
            </div>

            <!-- Button (Double) -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="create">Action</label>
                <div class="col-md-8">
                    <button id="create" name="create" class="btn btn-success">Create</button>
                    <button id="update" name="update" class="btn btn-info">Update</button>
                </div>
            </div>

        </fieldset>
    </form>
</div>
</body>
</html>
