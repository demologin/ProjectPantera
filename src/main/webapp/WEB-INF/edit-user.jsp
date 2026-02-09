<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<div class="container py-4">
    <form method="post" enctype="multipart/form-data">
        <fieldset>

            <!-- Form Name -->
            <legend class="mb-4">Edit user</legend>

            <!-- Avatar preview -->
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">Avatar</label>
                <div class="col-sm-6">
                    <img class="user-avatar" id="avatarPreview" src="<c:url value='/user-images/${requestScope.user.image}'/>"
                         alt="${requestScope.user.login}">
                </div>
            </div>

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
                    <div class="form-text">min 3 characters</div>
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
                    <div class="form-text">min 8 characters</div>
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

            <!-- Select Gender -->
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label" for="gender">Gender</label>
                <div class="col-sm-6">
                    <select id="gender" name="gender" class="form-control">
                        <c:forEach var="gender" items="${applicationScope.genders}">
                            <option value="${gender}"
                                    ${gender==requestScope.user.gender?"selected":""}>
                                ${gender}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <!-- Avatar upload -->
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label" for="image">Choose file</label>
                <div class="col-sm-6">
                    <input id="image" name="image" type="file" class="form-control" accept="image/*">
                    <div class="form-text">Upload a profile image (png, jpg, etc.)</div>
                </div>
            </div>

            <!-- Button (Double) -->
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label" for="create">Action</label>
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
<script>
    const imageInput = document.getElementById("image");
    const avatarPreview = document.getElementById("avatarPreview");
    if (imageInput && avatarPreview) {
        imageInput.addEventListener("change", (event) => {
            const [file] = event.target.files || [];
            if (file) {
                const url = URL.createObjectURL(file);
                avatarPreview.src = url;
            }
        });
    }
</script>
<%@include file="parts/footer.jsp" %>

