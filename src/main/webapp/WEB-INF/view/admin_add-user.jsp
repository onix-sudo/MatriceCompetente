<%@ include file="admin_header.jspf" %>

<%-- This page renders the add-user form for admin
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field --%>

<br>
<h3>Add an employee to the database</h3>
<hr>
    <form:form action = "saveUser" modelAttribute = "newEmployee" method="POST" accept-charset="utf-8">
 <table class="table table-striped">
            <thead class="thead-light">
        <tr>
            <th><label> Last name: </label></th>
            <th><form:input path="nume" /></th>
            <th><form:errors path="nume" cssClass="error"/></th>
        </tr>

        <tr>
            <th><label> First name: </label></th>
            <th><form:input path="prenume"  /></th>
            <th><form:errors path="prenume" cssClass="error"/></th>
        </tr>

        <tr>
            <th><label> ID number: </label></th>
            <th><form:input path="numarMatricol" /></th>
            <th><form:errors path="numarMatricol" cssClass="error"/></th>

        </tr>

        <tr>
            <th><label> Email: </label></th>
            <th><form:input path="email" /></th>
            <th><form:errors path="email" cssClass="error"/></th>

        </tr>

        <tr>
            <th><label> Starting date: </label></th>
            <th><form:input path="dataAngajare" placeholder="aaaa-ll-zz" /></th>
            <th><form:errors path="dataAngajare" cssClass="error"/></th>
        </tr>

        <tr>
            <th><label> Position: </label></th>
            <th><form:input path="functie" /></th>
            <th></td>

        </tr>

        <tr>
            <th></th>
            <th><input type="submit" value="Save" class="btn btn-outline-primary"/></th>
            <th></th>

        </tr>
        </thead>
    </table>

    </form:form>

<%@ include file="footer.jspf" %>