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

        <c:forEach var="history" items="${historyClusters}" varStatus="status">
            <script>
                      pushValues("${history.date}",${history.evaluare},"${history.skill.numeSkill}");
                      push();
            </script>


        </c:forEach>

<div id="myDiv">
    <script>
        plot();
    </script>
</div>

</html>

