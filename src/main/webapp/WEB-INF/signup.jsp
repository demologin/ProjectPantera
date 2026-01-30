<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="parts/header.jsp" %>
<div class="container">
    <section class="position-relative py-4 py-xl-5">
        <div class="container">
            <div class="row d-flex justify-content-center">
                <div class="col-md-6 col-xl-4">
                    <div class="card mb-5">
                        <div class="card-body d-flex flex-column align-items-center">
                            <h2>Signup</h2>
                            <form class="text-center" method="post" action="<c:url value='/signup'/>" enctype="multipart/form-data">
                                <p class="text-muted">Click to upload a photo</p>
                                <div class="form-group">
                                    <label for="image">
                                        <img id="previewId" src="<c:url value='/images/no-image.png'/>" width="250"
                                             alt="No image">
                                    </label>
                                    <input onchange="PreviewImage('image','previewId');" id="image" name="image"
                                           style="visibility:hidden;" class="input-file" type="file" accept="image/*">
                                    <script type="text/javascript">
                                        function PreviewImage(inputFileId, imageId) {
                                            let oFReader = new FileReader();
                                            oFReader.readAsDataURL(document.getElementById(inputFileId).files[0]);
                                            oFReader.onload = function (oFREvent) {
                                                document.getElementById(imageId).src = oFREvent.target.result;
                                            };
                                        }
                                    </script>
                                </div>
                                <p class="w-lg-50">Enter your registration details</p>
                                <div class="mb-3">
                                    <input class="form-control" type="text" name="login" placeholder="Login" required>
                                </div>
                                <div class="mb-3">
                                    <input class="form-control" type="password" name="password" placeholder="Password" required>
                                </div>
                                <div class="mb-3">
                                    <select id="gender" name="gender" class="form-control" required>
                                        <c:forEach var="gender" items="${applicationScope.genders}">
                                            <option value="${gender}">${gender}</option>
                                        </c:forEach>
                                    </select>
                                </div>
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
