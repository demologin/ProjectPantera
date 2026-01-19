<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.javarush.golikov.quest.model.*" %>

<%
    QuestSession qs = (QuestSession) session.getAttribute("quest");
    QuestNode node = (QuestNode) request.getAttribute("node");
%>

<!-- ===== НАЗВАНИЕ КВЕСТА ===== -->
<h2 class="quest-header">
    ${requestScope.questTitle}
</h2>

<div class="quest-box">

    <!-- ===== ИСТОРИЯ ===== -->
    <div class="quest-history">
        <% for (Step step : qs.getHistory()) { %>
        <div class="flow-question old">
            <div><%= step.questionText() %></div>
            <div class="chosen">
                Вы выбрали: <b><%= step.answerText() %></b>
            </div>
        </div>
        <% } %>
    </div>

    <!-- ===== ТЕКУЩИЙ ВОПРОС ===== -->
    <div class="flow-question">
        <%= node.text() %>
    </div>

    <form method="post" action="${pageContext.request.contextPath}/play"
          class="flow-answers">

        <% for (Choice c : node.choices()) { %>
        <button type="submit"
                name="next"
                value="<%= c.next() %>"
                class="flow-answer <%= c.positive() ? "yes" : "no" %>">
            <%= c.text() %>
        </button>

        <input type="hidden" name="answer" value="<%= c.text() %>">
        <% } %>
    </form>

    <!-- ===== КНОПКА ВЫХОДА ИЗ КВЕСТЫ ===== -->
    <form method="post"
          action="${pageContext.request.contextPath}/exitQuest"
          style="margin-top: 30px; text-align: center;">
        <button type="submit" class="flow-answer no">
            Выйти из квеста
        </button>
    </form>

</div>