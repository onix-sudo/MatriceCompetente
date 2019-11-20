<%@ include file="header.jspf"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>


<spring:url var="go" value="/webCM/leaders/searchPeople/search" >
    </spring:url>

<br><br>

<form:form action="${go}" method="get">

    <label>Search</label>
    <input type="text" pattern=".{4,}" name = "searchTerm" title="Campul trebuie sa contina cel putin 4 caractere." required/>

    <input type="submit" value="search"/>

</form:form>

<br>

<table>
    <thead>
    <tr>
        <th>Nume User</th>
        <th>Functie</th>
        <th></th>
    </tr>
    </thead>
    <core:forEach var="user" items="${users}">
        <tr>
            <td>${user.nume} ${user.prenume}</td>
            <td>${user.functie}</td>
        </tr>
    </core:forEach>
</table>


<%@ include file="footer.jspf"%>