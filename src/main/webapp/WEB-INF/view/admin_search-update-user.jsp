<%@ include file="admin_header.jspf" %>

<%-- This page renders the search-user form for admin
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field
     The JSTL core tag provides variable support and flow control
     --%>

<br>
<h3>Modifica angajat</h3>
<hr>
<spring:url var="go" value="/admin/updateUser/search" > </spring:url>
<form:form action="${go}" method="get">
    <table class="table table-striped">
           <thead class="thead-light">
        <tr>
            <th><label>Cauta angajat</label></th>
            <th><input type="text" pattern=".{3,}" name = "searchTerm" title="Campul trebuie sa contina cel putin 4 caractere." required/>
        </tr>

        <tr>
            <th></th>
            <th><input type="submit" value="Cauta" class="btn btn-outline-primary"/></th>
        </tr>
        </thead>
    </table>
</form:form>

<div id="container">
    <div id="content">
        <p>
        		User: <security:authentication property="principal.username" />
        </p>
        <c:if test="${result != null}">
         <table class="table table-striped">
                <thead class="thead-dark">
            <tr>
                <th>Nume</th>
                <th>Prenume</th>
                <th>Numar Matricol</th>
                <th>Email</th>
                <th>Functie</th>
                <th></th>
            </tr>
            </thead>
            <c:forEach var="tempResult" items="${result}">
                <spring:url var="modifyUser" value="/admin/updateUser/modify">
                    <spring:param name="userId" value="${tempResult.id}"/>
                </spring:url>
            <tr>
                <td>${tempResult.nume}</td>
                <td>${tempResult.prenume}</td>
                <td>${tempResult.numarMatricol}</td>
                <td>${tempResult.email}</td>
                <td>${tempResult.functie}</td>

                <td>
                 <button type="button" class="btn btn btn-outline-primary" onclick="window.location.href='${modifyUser}'">Modifica</button>

                </td>
            </tr>
            </c:forEach>

        </table>
        </c:if>


    </div>


</div>

<%@ include file="footer.jspf" %>