<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- This file renders the login page
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field
     The JSTL core tag provides variable support and flow control
     --%>

<!doctype html>
<html lang="en">

<head>

    <title>Login Page</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<%--
    <!-- Reference Bootstrap files -->
--%>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>

<style>
body {
    background: url( /resources/background.png) no-repeat center center fixed;
    background-size: 100% 85%;
    background-color: #022051;
   }
</style>


<body>

<div class = "container">

    <div id="loginbox" style="margin-top: 310px; margin-left:310px" class="mainbox col-md-4 col-md-offset-1 col-sm-10 col-sm-offset-1">

        <div class="panel panel-info">

            <div class="panel-heading">
                <div class="panel-title">Authentication</div>
            </div>

            <div style="padding-top: 30px" class="panel-body">

<%--
                <!-- Login Form -->
--%>
                <form:form action="${pageContext.request.contextPath}/authenticateTheUser"
                           method="POST" class="form-horizontal">

<%--
                    <!-- Place for messages: error, alert etc ... -->
--%>
                    <div class="form-group">
                        <div class="col-xs-15">
                            <div>

<%--
                                <!--        Check for login error-->
--%>

                                <c:if test="${param.error != null}">
                                    <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                        Invalid username and password.
                                    </div>
                                </c:if>

<%--
                                <!--        Check for logout error-->
--%>

                                <c:if test="${param.logout != null}">

                                    <div class="alert alert-success col-xs-offset-1 col-xs-10">
                                        You have been successfully logged out.
                                    </div>

                                </c:if>
                            </div>
                        </div>
                    </div>

<%--
                    <!-- User name -->
--%>
                    <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>

                        <input type="text" name="username" placeholder="Email address" class="form-control">
                    </div>

<%--
                    <!-- Password -->
--%>
                    <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>

                        <input type="password" name="password" placeholder="Password" class="form-control">
                    </div>

<%--
                    <!-- Login/Submit Button -->
--%>
                    <div style="float: left; padding-left: 20px" class="form-group">
                        <div class="col-sm-6 controls">
                            <button type="submit" class="btn btn-primary">Sign in</button>
                        </div>
                    </div>
                </form:form>

                    <div style="float: right; padding-right: 5px" class="form-group">
                        <div class="col-sm-6 controls">
                            <button type="submit" class="btn btn-light" onclick="window.location.href='/forgotPassword'">Forgot password</button>
                        </div>
                    </div>

            </div>

        </div>

    </div>

</div>

</body>
</html>