<%@ include file="header.jspf"%>

<%-- This page renders the home page --%>

    <hr>
<h3>
    <p>
        Salutare, ${nume}!
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
        <button type="button" class="btn btn-info app2"
                                    onclick="window.location.href='/webCM'">App1</button>
        <button type="button" class="btn btn-info app3"
                                    onclick="window.location.href='/webCM'">App2</button>
        <button type="button" class="btn btn-info app4"
                                    onclick="window.location.href='/webCM'">App3</button>
    </div>
    </security:authorize>
<%@ include file="footer.jspf"%>