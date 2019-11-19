<%@ include file="header.jspf"%>

<div class="ownBody">
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
            <%--<a href="${pageContext.request.contextPath}/webCM/leaders">Manager</a>--%>
             <button type="button" class="btn btn-success" onclick="window.location.href='/webCM/leaders'">Manager</button>
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
                                    onclick="window.location.href='/webCM'">webCM</button>
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

</div>
<%@ include file="footer.jspf"%>