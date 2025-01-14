<%@ include file="header.jspf"%>

<%-- This page renders the home page --%>

    <hr>
<h3>
    <p>
        Hello, ${nume}!
    </p>
</h3>
    <hr>

    <security:authorize access="hasRole('ADMIN')">
    <p>
        <button type="button" class="btn btn-outline-success" onclick="window.location.href='/admin'">Admin</button>
    </p>
    </security:authorize>

    <hr>

    <security:authorize access="hasAnyRole('MANAGER', 'EMPLOYEE')">
    <div class="centerDiv">
        <a href="/webCM"><img class="app1 btn btn-warning" src="/resources/competency.jpg"></a>
        <a href="/retex"><img class="app3 btn btn-info" src="/resources/retex.png"></a>
    </div>
    </security:authorize>
<%@ include file="footer.jspf"%>