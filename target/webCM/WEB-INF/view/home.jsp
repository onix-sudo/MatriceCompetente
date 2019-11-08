<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Home Page</title>

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

            <link rel="stylesheet" href="resources/css/style.css">

            <link rel="stylesheet" href="resources/css/header.css">

            <link rel="stylesheet" href="resources/css/main.css">

            <link rel="stylesheet" href="resources/css/util.css">

            <link rel="stylesheet" href="resources/css/navbar.css">

            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<%@ include file="navigation.jspf"%>

<body>

<div class="fixed-header">
    <div class="container">
        <nav>
            <a href="#">Expleo</a>
            <a href="#">About</a>
        </nav>
    </div>
</div>
<h2>Home Page</h2>
<hr>
<p>
    Welcome!
</p>
<hr>
<%--
<!--            Display username and role-->
--%>



<%--
<!--            Add a link to /leaders and / admin-->
--%>
<security:authorize access="hasRole('MANAGER')">
    <p>
        <a href="${pageContext.request.contextPath}/leaders">Leaders Page</a>
    </p>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
<p>
    <a href="${pageContext.request.contextPath}/admin">Admin Page</a>
</p>
</security:authorize>

<hr>

<div class="centerDiv">
    <button type="button" class="btn btn-info app1"
                                onclick="window.location.href='/retex'">Retex</button>
    <button type="button" class="btn btn-info app2"
                                onclick="window.location.href='/retex'">App1</button>
    <button type="button" class="btn btn-info app3"
                                onclick="window.location.href='/retex'">App2</button>
    <button type="button" class="btn btn-info app4"
                                onclick="window.location.href='/retex'">App3</button>

</div>
<%--
<!--        Add a logout button-->
--%>

<br><br>
<form:form action="${pageContext.request.contextPath}/logout"
           method="POST">

    <input type="submit" value="Logout" class="btn btn-warning"/>
</form:form>

<div class="fixed-footer">
    <div class="container">Copyright &copy; 2016 Your Company</div>
</div>


</body>
</html>