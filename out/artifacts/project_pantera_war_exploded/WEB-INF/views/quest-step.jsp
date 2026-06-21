<%--
  Created by IntelliJ IDEA.
  User: katrinaleinik
  Date: 6/5/26
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@include file="../components/header.jspf" %>
<div class="quest-step">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-xl-6 col-lg-6">
                <div class="story-box">

                    <p class="fs-5 text-white mb-5 quest-text">
                        ${step.text}
                    </p>
                </div>
                <c:choose>

                    <c:when test="${step.result == 'CONTINUE'}">
                        <div class="d-grid gap-3">
                            <c:forEach items="${step.choices}" var="choice">
                                <a href="${pageContext.request.contextPath}/quest?questId=${step.questId}&stepId=${choice.nextStepId}"
                                   class="btn btn-primary">
                                        ${choice.text}
                                </a>
                            </c:forEach>
                        </div>
                    </c:when>

                    <c:otherwise>
                        <div class="d-grid gap-3">
                            <a href="${pageContext.request.contextPath}/quest/start?id=${step.questId}"
                               class="btn btn-primary">
                                НАЧАТЬ ЗАНОВО
                            </a>

                            <a href="${pageContext.request.contextPath}/quests"
                               class="btn btn-light">
                                К СПИСКУ КВЕСТОВ
                            </a>
                        </div>
                    </c:otherwise>

                </c:choose>


            </div>
        </div>
    </div>
</div>

<%@include file="../components/footer.jspf" %>
