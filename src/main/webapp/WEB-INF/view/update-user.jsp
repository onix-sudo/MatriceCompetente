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

                <link rel="stylesheet"
                      href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

                <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<div id="container">
    <div id="content">
        <p>
        		User: <security:authentication property="principal.username" />
        </p>


    <form:form action = "/updateUser/update" modelAttribute = "updateUser" method="POST" accept-charset="utf-8">
    			<%--need to associate this data with customer id--%>
    			<form:hidden path="id"/>
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
            <td><form:input path="numarMatricol" class="form-control" type="text" readonly="true" />
            <form:errors path="numarMatricol" cssClass="error"/></td>
        </tr>

        <tr>
            <td><label> Email: </label><td>
            <td><form:input path="email" class="form-control" type="text" readonly="true" />
            <form:errors path="email" cssClass="error"/><td>
        </tr>

        <tr>
            <td><label> Data angajare: </label><td>
            <td><form:input path="dataAngajare" placeholder="aaaa-ll-zz" class="form-control" type="text" readonly="true" />
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

        </form:form>


    </div>
                    <tr>
                           <spring:url var="toRedirectForRemove" value="/admin/updateUser/removeManagerRole">
                               <spring:param name="userId" value="${param.userId}" />
                           </spring:url>

                           <spring:url var="toRedirectForAdd" value="/admin/updateUser/addManagerRole">
                               <spring:param name="userId" value="${param.userId}" />
                           </spring:url>

                            <core:choose>
                                <core:when test="${managerCheck}">
                                   <form:form action="${toRedirectForRemove}" method="post">
                                        <button class="btn btn-danger" >Remove manager role</button>
                                   </form:form>
                                 </core:when>

                                 <core:otherwise>
                                   <form:form action="${toRedirectForAdd}" method="post">
                                        <button class="btn btn-success" >Add manager role</button>
                                   </form:form>
                                 </core:otherwise>
                            </core:choose>

                     </tr>


</div>

</body>

</html>