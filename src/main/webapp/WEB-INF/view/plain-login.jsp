<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- This file renders the plain login page
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field
     The JSTL core tag provides variable support and flow control
     --%>

<html>
<head>
    <title>Custom Login Page</title>
    <style>
        .error {
            color:red}
    </style>
</head>

<body>

<h3>My Custom Login Page</h3>

    <form:form action="${pageContext.request.contextPath}/authenticateTheUser"
                method="POST">

        <c:if test="${param.error != null}">

            <i class="error">Sorry! Invalid username/password.</i>
        </c:if>

        <p>
            User name: <input type="text" name="username">
        </p>

        <p>
            Password: <input type="password" name="password">
        </p>

        <input type="submit" value="Login">

    </form:form>


</body>

</html>