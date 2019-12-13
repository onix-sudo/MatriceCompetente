<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<button type="button" class="btn btn-outline-primary" onclick="return createProject()">Creeaza un proiect</button>

<button type="button" class="btn btn-outline-primary"
 onclick="return proiectFaraManageri()">Alege un proiect fara manager</button>

<hr>
<c:choose>
<c:when test="${empty projects}">
<h4>Nu gestionezi niciun proiect.</h4>
</c:when>
<c:otherwise>
   <table class="table table-striped">
           <thead class="thead-dark">
            <h2>Proiectele tale:</h2>
            <tr>
                <th>Nume proiect</th>
                <th>Cod proiect</th>
                <th></th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="proiecte" items="${projects}">
                <tr>
                    <td>${proiecte.numeProiect}</td>
                    <td>${proiecte.codProiect}</td>
                    <td>
                        <button class="btn btn-outline-primary" onclick="return modify('${proiecte.codProiect}')">Modifica</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>

    </table>
    </c:otherwise>
    </c:choose>
<hr>

<script>
    function modify(codProiect) {
        $("#div3").load("/webCM/leaders/project/" + codProiect);

        return false;
    }

    function createProject() {
        $("#div3").load("/webCM/leaders/addNewProject");

        return false;
    }

    function proiectFaraManageri() {
            $("#div3").load("/webCM/leaders/freeProjects");

            return false;
    }
</script>

