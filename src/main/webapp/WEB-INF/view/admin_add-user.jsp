<%@ include file="admin_header.jspf" %>

    <form:form action = "saveUser" modelAttribute = "newEmployee" method="POST" accept-charset="utf-8">
    <table>
        <tr>
            <td><label> Nume: </label><td>
            <td><form:input path="nume" /></td>
        </tr>

        <tr>
            <td><label> Prenume: </label><td>
            <td><form:input path="prenume"  /></td>
        </tr>

        <tr>
            <td><label> Numar matricol: </label><td>
            <td><form:input path="numarMatricol" />
            <form:errors path="numarMatricol" cssClass="error"/></td>
        </tr>

        <tr>
            <td><label> Email: </label><td>
            <td><form:input path="email" />
            <form:errors path="email" cssClass="error"/><td>
        </tr>

        <tr>
            <td><label> Data angajare: </label><td>
            <td><form:input path="dataAngajare" placeholder="aaaa-ll-zz" />
            <form:errors path="dataAngajare" cssClass="error"/></td>
        </tr>

        <tr>
            <td><label> Functie: </label><td>
            <td><form:input path="functie" /></td>
        </tr>

        <tr>
            <td><input type="submit" value="Adauga" class="btn btn-success"/></td>
            <td><label></label></td>
        </tr>
    </table>

    </form:form>

<%@ include file="footer.jspf" %>