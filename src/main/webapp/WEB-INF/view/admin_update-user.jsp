<%@ include file="admin_header.jspf" %>

<%-- This page renders the update-user form for admin
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field
     The JSTL core tag provides variable support and flow control
     --%>

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
                    <button class="btn btn-danger" >Sterge rolul de manager</button>
               </form:form>
             </c:when>

             <c:otherwise>
               <form:form action="${toRedirectForAdd}" method="post">
                    <button class="btn btn-outline-success" >Adauga rol de manager</button>
               </form:form>
             </c:otherwise>
        </c:choose>
        <hr>

    <form:form action = "update" modelAttribute = "user" method="POST" accept-charset="utf-8">
    			<!-- need to associate this data with customer id -->
    			<form:hidden path="id" />
    <table class="table table-striped">
                <thead class="thead-light">
        <tr>
            <th><label> Nume: </label></th>
            <th><form:input path="nume" /></th>
            <th></th>
        </tr>

        <tr>
            <th><label> Prenume: </label></th>
            <th><form:input path="prenume"  /></th>
            <th></th>
        </tr>

        <tr>
            <th><label> Numar matricol: </label></th>
            <th><form:input path="numarMatricol" class="form-control" type="text" readonly="true" /></th>
            <th><form:errors path="numarMatricol" cssClass="error"/></th>
        </tr>

        <tr>
            <th><label> Email: </label></th>
            <th><form:input path="email" class="form-control" type="text" readonly="true" /></th>
            <th><form:errors path="email" cssClass="error"/></th>
        </tr>

        <tr>
            <th><label> Data angajare: </label></th>
            <th><form:input path="dataAngajare" placeholder="aaaa-ll-zz" class="form-control" type="text" readonly="true" /></th>
            <th><form:errors path="dataAngajare" cssClass="error"/></th>
        </tr>

        <tr>
            <th><label> Rol: </label></th>
            <th><form:input path="functie" /></th>
            <th></th>
        </tr>

        <tr>
            <th></th>
            <th><input type="submit" value="Salveaza" class="btn btn-success"/></th>
            <th></th>
        </tr>
        </thead>
        </table>
        </form:form>
    </div>
</div>

<%@ include file="footer.jspf" %>