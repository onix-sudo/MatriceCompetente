<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Retex</title>

            <link rel="stylesheet"
                  href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
<h2>Home Page</h2>
<hr>
<p>
    Welcome!
</p>
<hr>
<%--
<!--            Display username and role-->
--%>
<p>
    User:
    <security:authentication property="principal.username"/>
    <br><br>
    Role(s):
    <security:authentication property="principal.authorities"/>
</p>

<hr>
<%--
<!--            Add a link to /leaders and / admin-->
--%>


<hr>


 <button type="button" class="btn btn-success" onclick="window.location.href='/retex/employee'">Angajat</button>
 <button type="button" class="btn btn-success" onclick="window.location.href='/retex/leaders'">Manager</button>
<%--
<!--        Add a logout button-->
--%>



</body>
</html>