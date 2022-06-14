<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- This file renders the projects available
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field
     The JSTL core tag provides variable support and flow control
     --%>

<c:choose>
<c:when test="${empty result}">
<h3>No available project.</h3>
<hr>
</c:when>
<c:otherwise>
<br>
<h3>Available project:</h3>

<hr>

<table class="table table-striped">
    <thead class="thead-dark">
    <tr>
        <th>Project name</th>
        <th>Project ID</th>
        <th>Add</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <c:forEach var="tempResult" items="${result}">

            <spring:url var="addFreeProject" value="/webCM/leaders/freeProjects/add">
                <spring:param name="codProiect" value="${tempResult.codProiect}"/>
            </spring:url>
            <td>${tempResult.numeProiect}</td>
            <td>${tempResult.codProiect}</td>
            <td>
                <button onclick="return preiaProiect('${tempResult.codProiect}')" class="btn btn-outline-primary">
                    Add
                </button>
            </td>

    </tr>
    </tbody>
    </c:forEach>
</table>
</c:otherwise>
</c:choose>

<script>
    function backToManageTeam() {
        $("#tab3").click();
    }

    function preiaProiect(codProiect) {

        $.ajax({
            type: "GET",
            url: "/webCM/leaders/freeProjects/add?codProiect=" + codProiect,
            success: function(response)
            {
                $("#div3").load("/webCM/leaders/freeProjects");
            },
            error: function(response){
                console.log("ERROR");
                $("#div3").load("/webCM/leaders/freeProjects");
            }
        })

        return false;
    }
</script>
