<%@ include file="admin_header.jspf" %>

<div id="container">
    <div id="content">
        <p>
        		User: <security:authentication property="principal.username" />
        </p>

                                  <hr>
                                   <spring:url var="toRedirectForRemove" value="/admin/updateUser/removeManagerRole">
                                       <spring:param name="userId" value="${param.userId}" />
                                   </spring:url>

                                   <spring:url var="toRedirectForAdd" value="/admin/updateUser/addManagerRole">
                                       <spring:param name="userId" value="${param.userId}" />
                                   </spring:url>

                                    <c:choose>
                                        <c:when test="${managerCheck}">
                                           <form:form action="${toRedirectForRemove}" method="post">
                                                <button class="btn btn-danger" >Remove manager role</button>
                                           </form:form>
                                         </c:when>

                                         <c:otherwise>
                                           <form:form action="${toRedirectForAdd}" method="post">
                                                <button class="btn btn-success" >Add manager role</button>
                                           </form:form>
                                         </c:otherwise>
                                    </c:choose>
                                    <hr>

    <form:form action = "update" modelAttribute = "user" method="POST" accept-charset="utf-8">
    			<!-- need to associate this data with customer id -->
    			<form:hidden path="id" />
    <table>
        <tr>
            <th><label> Nume: </label>
            <form:input path="nume" /></th>
        </tr>

        <tr>
            <th><label> Prenume: </label>
            <form:input path="prenume"  /></th>
        </tr>

        <tr>
            <th><label> Numar matricol: </label>
            <form:input path="numarMatricol" class="form-control" type="text" readonly="true" />
            <form:errors path="numarMatricol" cssClass="error"/></th>
        </tr>

        <tr>
            <th><label> Email: </label>
            <form:input path="email" class="form-control" type="text" readonly="true" />
            <form:errors path="email" cssClass="error"/><t>
        </tr>

        <tr>
            <th><label> Data angajare: </label>
            <form:input path="dataAngajare" placeholder="aaaa-ll-zz" class="form-control" type="text" readonly="true" />
            <form:errors path="dataAngajare" cssClass="error"/></th>
        </tr>

        <tr>
            <th><label> Functie: </label>
            <form:input path="functie" /></th>
        </tr>

        <tr>

            <th><input type="submit" value="Salveaza" class="btn btn-success"/></th>
        </tr>
        </table>
        </form:form>
    </div>
</div>

<%@ include file="footer.jspf" %>