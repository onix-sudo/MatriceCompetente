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
            <a href="${pageContext.request.contextPath}/leaders">Leaders Page</a>
        </p>
    </security:authorize>

    <security:authorize access="hasRole('ADMIN')">
    <p>
        <a href="${pageContext.request.contextPath}/admin">Admin Page</a>
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

    <br><br>
    <form:form action="${pageContext.request.contextPath}/logout"
               method="POST">

        <input type="submit" value="Logout" class="btn btn-warning"/>
    </form:form>

    <div class="fixed-footer">
        <div class="container">Copyright &copy; 2016 Your Company</div>
    </div>


</body>
</html>