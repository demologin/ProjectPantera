
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@include file="../components/header.jspf" %>
<div class="quest-page">
    <div class="container-fluid pt-5 pb-5 ms-5">

        <h5 class="text-white mb-4">Приключения</h5>

        <div class="row">
            <c:forEach items="${quests}" var="quest">
                <div class="col-md-6 col-lg-4 mb-4">
                    <div class="card text-white bg-primary h-100">

                        <div class="card-body">
                            <h5 class="card-title">${quest.title}</h5>
                            <p class="card-text">${quest.description}</p>

                            <a href="${pageContext.request.contextPath}/quest?questId=${quest.id}&stepId=${quest.firstStepId}"
                               class="btn btn-light">
                                НАЧАТЬ
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

    </div>
</div>
<%@include file="../components/footer.jspf" %>