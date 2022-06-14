<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- This file renders the leader home page
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field
     The JSTL core tag provides variable support and flow control
     --%>

<button type="button" class="btn btn-outline-primary" onclick="return createProject()">Create a project</button>
<button type="button" class="btn btn-outline-primary"
 onclick="return proiectFaraManageri()">Choose a project without a manager</button>

<hr>
<c:choose>
<c:when test="${empty projects}">
<h4>You do not manage any projects.</h4>
</c:when>
<c:otherwise>
   <table class="table table-striped">
           <thead class="thead-dark">
            <h2>Your projects:</h2>
            <tr>
                <th>Project name</th>
                <th>Project ID</th>
                <th></th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="proiecte" items="${projects}">
                <tr>
                    <td>${proiecte.numeProiect}</td>
                    <td>${proiecte.codProiect}</td>
                    <td>
                        <button class="btn btn-outline-primary" onclick="return modify('${proiecte.codProiect}')">Open</button>
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

