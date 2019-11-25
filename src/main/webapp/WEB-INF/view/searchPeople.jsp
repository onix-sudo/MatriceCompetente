
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:url var="go" value="/webCM/leaders/searchPeople/search" >
    </spring:url>

<br>

<form:form action="${go}" method="get">
<h4>Introduceti numele competentei si evaluarea minima</h4>
<br>
    <label>Search</label>
    <input type="text" pattern=".{4,}" name = "searchTerm" title="Campul trebuie sa contina cel putin 4 caractere." required/>

    <form method = "get" action="${searchPeopleByEvaluation}">
        <td>
            <select name="evaluation" default = ${userSkill.evaluation}>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
            </select>
            <input type='submit' value='Submit' />
        </td>
    </form>

</form:form>

<br>

<core:if test="${usersSkills != null}">
<table>
    <thead>
    <tr>
        <th>Nume User</th>
        <th>Functie</th>
        <th>Competenta</th>
        <th>Categorie</th>
        <th>Evaluare</th>
    </tr>
    </thead>

    <core:forEach var="userSkill" items="${usersSkills}">
        <tr>
            <td>${userSkill.user.nume} ${userSkill.user.prenume}</td>
            <td>${userSkill.user.functie}</td>
            <td>${userSkill.skill.numeSkill}</td>
            <td>${userSkill.skill.categorie}</td>
            <td>${userSkill.evaluation}</td>
            <td> </td>
        </tr>
    </core:forEach>
</table>
</core:if>

<br>
<button type="button" class="btn btn-info" onclick="window.location.href='/webCM/leaders'">Back</button>

