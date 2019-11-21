<%@ include file="header.jspf"%>


    <hr>
<h3>
    <p>
        Welcome , ${nume}!
    </p>
</h3>
    <hr>

    <%--
    <!--            Display username and role-->
    --%>


    <%--
    <!--            Add a link to /leaders and / admin-->
    --%>


    <security:authorize access="hasRole('ADMIN')">
    <p>
        <%--<a href="${pageContext.request.contextPath}/admin">Admin Page</a>--%>
        <button type="button" class="btn btn-success" onclick="window.location.href='/admin'">Admin</button>
    </p>
    </security:authorize>

    <hr>

    <div class="centerDiv">
        <a href="/webCM"><img class="app1 btn btn-warning" src="/resources/competency.jpg"></a>
        <button type="button" class="btn btn-info app2"
                                    onclick="window.location.href='/webCM'">App1</button>
        <button type="button" class="btn btn-info app3"
                                    onclick="window.location.href='/webCM'">App2</button>
        <button type="button" class="btn btn-info app4"
                                    onclick="window.location.href='/webCM'">App3</button>

    </div>
    <%--
    <!--        Add a logout button-->
    --%>

<%@ include file="footer.jspf"%>