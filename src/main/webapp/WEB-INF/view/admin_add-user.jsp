<%@ include file="admin_header.jspf" %>
<br>
<h3>Adauga un angajat in baza de date</h3>
<hr>
    <form:form action = "saveUser" modelAttribute = "newEmployee" method="POST" accept-charset="utf-8">
 <table class="table table-striped">
            <thead class="thead-light">
        <tr>
            <th><label> Nume: </label></th>
            <th><form:input path="nume" /></th>
            <th><form:errors path="nume" cssClass="error"/></th>
        </tr>

        <tr>
            <th><label> Prenume: </label></th>
            <th><form:input path="prenume"  /></th>
            <th><form:errors path="prenume" cssClass="error"/></th>
        </tr>

        <tr>
            <th><label> Numar matricol: </label></th>
            <th><form:input path="numarMatricol" /></th>
            <th><form:errors path="numarMatricol" cssClass="error"/></th>

        </tr>

        <tr>
            <th><label> Email: </label></th>
            <th><form:input path="email" /></th>
            <th><form:errors path="email" cssClass="error"/></th>

        </tr>

        <tr>
            <th><label> Data angajare: </label></th>
            <th><form:input path="dataAngajare" placeholder="aaaa-ll-zz" /></th>
            <th><form:errors path="dataAngajare" cssClass="error"/></th>
        </tr>

        <tr>
            <th><label> Functie: </label></th>
            <th><form:input path="functie" /></th>
            <th></td>

        </tr>

        <tr>
            <th></th>
            <th><input type="submit" value="Adauga" class="btn btn-outline-primary"/></th>
            <th></th>

        </tr>
        </thead>
    </table>

    </form:form>

<%@ include file="footer.jspf" %>