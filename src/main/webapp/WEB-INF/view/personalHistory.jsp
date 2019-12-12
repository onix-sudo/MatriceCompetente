<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<script type="text/javascript" src="/resources/js/radar.js"></script>
<script type="text/javascript" src="/resources/js/radarForTeam.js"></script>
<script type="text/javascript" src="/resources/js/plotly-latest.min.js"></script>
<script type="text/javascript" src="/resources/js/lineChart.js"></script>


<html>
        <button type="button" onclick="showSkills()" class="btn btn-info">Back</button>

        <c:forEach var="history" items="${histories}" varStatus="status">
            <c:forEach var="eval" items="${history.evaluations}">
            <script>
                pushEval("${eval}");
            </script>
            </c:forEach>
            <c:forEach var="date" items="${history.date}">
            <script>
                pushData("${date}");
            </script>
            </c:forEach>
            <script>
                push("${history.skill.numeSkill}");
                <%--push();--%>
            </script>
        </c:forEach>
<%--            <script>
                      pushValues("${history.date}",${history.evaluare},"${history.skill.numeSkill}");
                      push();
            </script>--%>

<div id="myDiv">
    <script>
        plot();
    </script>
</div>

</html>

