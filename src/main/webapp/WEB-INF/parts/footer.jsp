<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

</main>
<footer class="text-center bg-dark mt-auto">
    <c:if test="${not empty sessionScope.errorMessage}">
        <h5 class="mb-1 alert-danger">${sessionScope.errorMessage}</h5>
    </c:if>
    <div class="container text-white py-4 py-lg-5">
        <ul class="list-inline">
            <li class="list-inline-item me-4"><a class="link-light" href="our-team">Our team</a></li>
            <li class="list-inline-item me-4"><a class="link-light" href="#">Contact us</a></li>
        </ul>
        <p class="text-muted mb-0">Copyright&nbsp; &nbsp;© 2026 JRU Company, Inc. Pantera Group.&nbsp;</p>
    </div>
</footer>
</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>