<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page pageEncoding="UTF-8"%>

<html>
<head>
    <title>ADMIN PAGE</title>

    <%--
        <!-- Reference Bootstrap files -->
    --%>
        <link rel="stylesheet"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<h2>Nothing here for the moment.</h2>


 <button type="button" class="btn btn-success" onclick="window.location.href='/admin/addUser'">Adauga angajat</button>
 <button type="button" class="btn btn-success" onclick="window.location.href='/admin/updateUser'">Modifica angajat</button>
 <button type="button" class="btn btn-success" onclick="window.location.href='/admin/tomcatManager'">TomcatManager</button>

<hr>

<form:form action="${pageContext.request.contextPath}/logout"
           method="POST">

    <input type="submit" value="Logout"/>
</form:form>

</body>
</html>