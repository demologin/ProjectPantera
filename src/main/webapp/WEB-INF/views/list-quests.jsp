<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@include file="../components/header.jspf" %>

<div class="quest-page">
    <div class="container-fluid pt-5 pb-5 ms-5">

        <h5 class="text-white mb-4">Приключения</h5>

        <div class="row">
            <c:forEach items="${quests}" var="quest">
                <div class="col-md-6 col-lg-4 mb-4">
                    <div class="card text-white bg-primary h-100 quest-card">
                        <div class="card-body">

                            <div class="d-flex justify-content-between align-items-start gap-3 mb-3">
                                <h5 class="card-title mb-0">
                                        ${quest.title}
                                </h5>

                                <c:if test="${completedQuestIds.contains(quest.id)}">
                                    <span class="badge bg-success quest-status">
                                        ПРОЙДЕНО
                                    </span>
                                </c:if>
                            </div>

                            <p class="card-text quest-card-text">
                                    ${quest.description}
                            </p>
                            <div class="quest-actions">
                                <c:choose>
                                    <c:when test="${progressByQuestId[quest.id] != null && !completedQuestIds.contains(quest.id)}">
                                        <div class="d-flex gap-2">
                                            <a href="${pageContext.request.contextPath}/quest?questId=${quest.id}&stepId=${progressByQuestId[quest.id]}"
                                               class="btn btn-light">
                                                ПРОДОЛЖИТЬ
                                            </a>

                                            <a href="${pageContext.request.contextPath}/quest?questId=${quest.id}&stepId=${quest.firstStepId}"
                                               class="btn btn-outline-secondary quest-secondary-btn">
                                                НАЧАТЬ СНАЧАЛА
                                            </a>
                                        </div>
                                    </c:when>

                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/quest?questId=${quest.id}&stepId=${quest.firstStepId}"
                                           class="btn btn-light">
                                            НАЧАТЬ
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

    </div>
</div>

<%@include file="../components/footer.jspf" %>