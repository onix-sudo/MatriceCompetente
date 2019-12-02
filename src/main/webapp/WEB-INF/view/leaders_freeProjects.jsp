<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<button onclick="return backToManageTeam()" class="btn btn-primary">
    Back
</button>

<h2>Proiecte disponibile:</h2>


<table>
    <tr>
        <th>Nume proiect</th>
        <th>Cod</th>
        <th>Preia</th>
    </tr>
    <tr>
        <c:forEach var="tempResult" items="${result}">

            <spring:url var="addFreeProject" value="/webCM/leaders/freeProjects/add">
                <spring:param name="codProiect" value="${tempResult.codProiect}"/>
            </spring:url>
            <td>${tempResult.numeProiect}</td>
            <td>${tempResult.codProiect}</td>
            <td>
                <button onclick="return preiaProiect('${tempResult.codProiect}')" class="btn btn-success">
                    Preia
                </button>
            </td>

    </tr>
    </c:forEach>
</table>

<script>
    function backToManageTeam() {
        $("#tab3").click();

        return false;
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
