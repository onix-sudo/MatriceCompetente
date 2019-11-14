<%@ include file="leadersHeader.jspf"%>


<form:form action= "addProject" modelAttribute = "newProject" method="POST" accept-charset = "utf-8">
    <table>
        <thead>
        <tr>
            <td><label>Nume proiect:</label></td>
            <td><form:input path="numeProiect"/></td>
        </tr>

        <tr>
            <td><label>Cod proiect:</label></td>
            <td><form:input path="codProiect"/></td>
        </tr>

        <tr>
            <td><label></label></td>
            <td><input type="submit" value="Save" class="save"/></td>
        </tr>
        </thead>
    </table>

</form:form>


<%@ include file="footer.jspf"%>