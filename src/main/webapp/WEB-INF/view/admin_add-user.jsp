<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<html>
<head>
    <title>
        ADMIN PAGE -- ADD NEW USER
    </title>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
        <style>
            .error {color:red}
        </style>

</head>
<body>

    <form:form action = "saveUser" modelAttribute = "newEmployee" method="POST" accept-charset="utf-8">
    <table>
        <tbody>
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
            <td><label></label></td>
            <td><input type="submit" value="Save" class="save"/></td>
        </tr>
        </tbody>
    </table>

    </form:form>


</body>

</html>