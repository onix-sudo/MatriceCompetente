<%@ include file="header.jspf"%>
<%@ include file="navigation.jspf"%>


    <h2>Home Page</h2>
    <hr>
    <p>
        Welcome!
    </p>
    <hr>
    <%--
    <!--            Display username and role-->
    --%>


    <%--
    <!--            Add a link to /leaders and / admin-->
    --%>
    <security:authorize access="hasRole('MANAGER')">
        <p>
            <%--<a href="${pageContext.request.contextPath}/retex/leaders">Manager</a>--%>
             <button type="button" class="btn btn-success" onclick="window.location.href='/retex/leaders'">Manager</button>
        </p>
    </security:authorize>

    <security:authorize access="hasRole('ADMIN')">
    <p>
        <%--<a href="${pageContext.request.contextPath}/admin">Admin Page</a>--%>
        <button type="button" class="btn btn-success" onclick="window.location.href='/admin'">Admin</button>
    </p>
    </security:authorize>

    <hr>

    <div class="centerDiv">
        <button type="button" class="btn btn-info app1"
                                    onclick="window.location.href='/retex'">Retex</button>
        <button type="button" class="btn btn-info app2"
                                    onclick="window.location.href='/retex'">App1</button>
        <button type="button" class="btn btn-info app3"
                                    onclick="window.location.href='/retex'">App2</button>
        <button type="button" class="btn btn-info app4"
                                    onclick="window.location.href='/retex'">App3</button>

    </div>
    <%--
    <!--        Add a logout button-->
    --%>

<%@ include file="footer.jspf"%>