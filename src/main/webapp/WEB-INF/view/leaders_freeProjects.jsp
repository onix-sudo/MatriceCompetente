<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>


<c:choose>
<c:when test="${empty result}">
<h3>Niciun proiect disponibil.</h3>
<hr>
</c:when>
<c:otherwise>
<br>
<h3>Proiecte disponibile:</h3>

<hr>

<table class="table table-striped">
    <thead class="thead-dark">
    <tr>
        <th>Nume proiect</th>
        <th>Cod</th>
        <th>Preia</th>
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
                    Preia
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
        console.log("AICI");

        $.ajax({
            type: "GET",
            url: "/webCM/leaders/freeProjects/add?codProiect=" + codProiect,
            success: function(response)
            {
                console.log("SUCCES");
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
