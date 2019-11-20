<%@ include file="header.jspf"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:url var="go" value="/webCM/leaders/searchPeople/search" >
    </spring:url>

<br><br>

<form:form action="${go}" method="get">

    <label>Search</label>
    <input type="text" pattern=".{4,}" name = "searchTerm" title="Campul trebuie sa contina cel putin 4 caractere." required/>

    <input type="submit" value="search"/>

    <form method = "get" action="${searchPeopleByEvaluation}">
        <td>
            <select name="evaluation" default = ${userSkill.evaluation}>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
            </select>
<!--            <input type=hidden name="idskill" value="${userSkill.skill.idSkill}"/>-->
            <input type='submit' value='Submit' />
        </td>
    </form>

</form:form>

<br>

<table>
    <thead>
    <tr>
        <th>Nume User</th>
        <th>Functie</th>
        <th>Evaluare</th>
        <th></th>
    </tr>
    </thead>

    <core:forEach var="userSkill" items="${usersSkills}">
        <tr>
            <td>${userSkill.user.nume} ${userSkill.user.prenume}</td>
            <td>${userSkill.user.functie}</td>
            <td>${userSkill.evaluation}</td>
        </tr>
    </core:forEach>
</table>


<%@ include file="footer.jspf"%>