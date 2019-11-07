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

    <form:form action = "updateUser" modelAttribute = "employeeSearch" method="GET">
    <table>
        <tbody>
        <tr>
            <td>
            <form:input path="freeText" name="freeText" value="${param.freeText}" placeholder="Enter Text To Search" />
            </td>
        </tr>

        <tr>
            <td><label></label></td>
            <td><input type="submit" value="Search"/></td>
        </tr>
        </tbody>
    </table>

    </form:form>


</body>

</html>