<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
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

    <form:form action = "searchUser"  method="GET">
    <table>

        <tr>
            <td>
            <form:label>Name:</form:label>
            <form:input type="text" placeholder="Enter Text To Search" />
            </td>
        </tr>

        <tr>
            <td><label></label></td>
            <td><input type="submit" name="search" value="Search"/></td>
        </tr>

    </table>

    </form:form>


</body>

</html>