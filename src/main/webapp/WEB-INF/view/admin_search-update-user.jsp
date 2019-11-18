<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
    <title>
        ADMIN PAGE -- UPDATE USER
    </title>
        <style>
            .error {color:red}
        </style>
</head>
<body>


        <spring:url var="go" value="/admin/updateUser/search" >

        </spring:url>

        <form:form action="${go}" method="get">
            <table>
                <tr>
                    <td><label>Search</label></td>
                    <td><input type="text" minlength="3" name = "searchTerm" title="Campul trebuie sa contina cel putin 2 caractere." required/>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="search"/></td>
                </tr>
            </table>
        </form:form>
<div id="container">
    <div id="content">
        <p>
        		User: <security:authentication property="principal.username" />
        </p>
        <core:if test="${result != null}">
        <table>
            <tr>
                <th>Nume</th>
                <th>Prenume</th>
                <th>Numar Matricol</th>
                <th>Email</th>
                <th>Functie</th>
            </tr>
            <core:forEach var="tempResult" items="${result}">
                <spring:url var="modifyUser" value="/admin/updateUser/modify">
                    <spring:param name="userId" value="${tempResult.id}"/>
                </spring:url>
            <tr>
                <td>${tempResult.nume}</td>
                <td>${tempResult.prenume}</td>
                <td>${tempResult.numarMatricol}</td>
                <td>${tempResult.email}</td>
                <td>${tempResult.functie}</td>

                <td>
                <a href="${modifyUser}">Modify</a>
                </td>
            </tr>
            </core:forEach>

        </table>
        </core:if>


    </div>


</div>

</body>

</html>