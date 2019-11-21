<%@ include file="admin_header.jspf" %>

    <form:form action = "addSkill/save" modelAttribute = "newSkill" method="POST" accept-charset="utf-8">
    <table>
        <tr>
            <td><label> Competenta: </label><td>
            <td><form:input path="numeSkill" /></td>
        </tr>

        <tr>
            <td><label> Categorie: </label><td>
            <td><form:input path="categorie"  /></td>
        </tr>

        <tr>
            <td><input type="submit" value="Adauga" class="btn btn-success"/></td>
            <td><label></label></td>
        </tr>
    </table>

    </form:form>


<%@ include file="footer.jspf" %>