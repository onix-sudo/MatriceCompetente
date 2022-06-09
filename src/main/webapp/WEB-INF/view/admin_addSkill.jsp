<%@ include file="admin_header.jspf" %>

<%-- This page renders the add-skill form for admin
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field --%>
<br>
<h2>Add skill to the database</h2>
<hr>
    <form:form action = "addSkill/save" modelAttribute = "newSkill" method="POST" accept-charset="utf-8">
     <table class="table table-striped">
        <thead class="thead-light">
        <tr>
            <th><label> Skill: </label><th>
            <th><form:input path="numeSkill" /></th>
        </tr>

        <tr>
            <th><label> Category: </label><th>
            <th><form:input path="categorie"  /></th>
        </tr>

        <tr>
            <th></th>
            <th></th>
            <th><input type="submit" value="Save" class="btn btn-outline-primary"/></th>
        </tr>
        </thead>

    </table>

    </form:form>


<%@ include file="footer.jspf" %>