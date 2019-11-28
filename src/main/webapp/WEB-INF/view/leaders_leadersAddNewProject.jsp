<%@ include file="leaders_leadersHeader.jspf"%>
<br>
  <button type="button" class="btn btn-warning"
  onclick="window.location.href='/webCM/leaders/'">Proiecte</button>
<hr>

<form:form action= "addProject" modelAttribute = "newProject" method="POST" accept-charset = "utf-8">
    <table>
        <thead>
        <tr>
            <td><label>Nume proiect:</label></td>
            <td><form:input path="numeProiect"/></td>
            <td><form:errors path="numeProiect" cssClass="error"/></td>
        </tr>

        <tr>
            <td><label>Cod proiect:</label></td>
            <td><form:input path="codProiect"/></td>
            <td><form:errors path="codProiect" cssClass="error"/></td>
        </tr>

        <tr>
            <td><label></label></td>
            <td><input type="submit" value="Save" class="save"/></td>
        </tr>
        </thead>
    </table>

</form:form>


<%@ include file="footer.jspf"%>