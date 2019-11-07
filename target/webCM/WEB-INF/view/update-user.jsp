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
            Name:
            <form:input type="text"  name="search" placeholder="Enter Text To Search" />
            </td>
        </tr>

        <tr>

            <td><input type="submit" value="Search"/></td>
        </tr>

    </table>

    </form:form>


</body>

</html>