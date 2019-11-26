
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<!--<html>-->
<!--<head>-->
<!--    <title>Expleo webCM</title>-->

<!--    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">-->

<!--    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>-->

<!--    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>-->

<!--    <link rel="stylesheet" href="/resources/css/style.css">-->

<!--    <link rel="stylesheet" href="/resources/css/header&footer.css">-->

<!--    <link rel="stylesheet" href="/resources/css/main.css">-->

<!--    <link rel="stylesheet" href="/resources/css/util.css">-->

<!--    <link rel="stylesheet" href="/resources/css/navbar.css">-->

<!--    <link rel="stylesheet" href="/resources/css/split-screen.css">-->

<!--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>-->

<!--    <script type="text/javascript" src="/resources/js/radar.js">-->
<!--    </script>-->

<!--    <script src="https://cdn.plot.ly/plotly-latest.min.js"></script>-->

<!--    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>-->

<!--</head>-->
<!--<body>-->




<br>
<h2>Add Skill</h2>

<p>
<hr>
<b>Nume:</b> ${user.nume} ${user.prenume}
<br>
<b>Numar matricol</b>: ${user.numarMatricol}
<br>
<b>Adresa email:</b>
<security:authentication property="principal.username"/>
<br>
<b>Data angajare:</b> ${user.dataAngajare}
<br>
<b>Functie:</b> ${user.functie}
<br>
<hr>
<b>Skilluri personale:</b>
</p>


<br>


<spring:url var="go" value="/webCM/personalProfile/showFormForAddSkill/search" >
</spring:url>

<form:form id="searchForm" onsubmit="search()">

    <label>Search</label>
    <input type="text" pattern=".{3,}" id="searchTerm" name = "searchTerm" title="Campul trebuie sa contina cel putin 4
    caractere."
           required/>

    <input type="submit" value="Search"/>

</form:form>


<core:if test="${result != null}">
    <table>
        <thead>
            <tr>
                <th>Nume Skill</th>
                <th>Categorie</th>
                <th></th>
            </tr>
        </thead>
        <core:forEach var="tempResult" items="${result}">
            <spring:url var="addSkill" value="/webCM/personalProfile/showFormForAddSkill/search/addSkillToUser">
                <spring:param name="skillId" value="${tempResult.idSkill}"/>
            </spring:url>
            <tr>
                <td>${tempResult.numeSkill}</td>
                <td>${tempResult.categorie}</td>
                <td>
                    <a href="${addSkill}">Add</a>
                </td>
            </tr>
        </core:forEach>
    </table>
</core:if>



<button type="button" class="btn btn-info" onclick="window.location.href='/webCM/personalProfile'">Back</button>

<script>
    function search() {
        $("#div2").load("/webCM/personalProfile/showFormForAddSkill/search?searchTerm=" + $("#searchTerm").val());
        console.log($("#searchTerm").val());

        return false;
    }
</script>

