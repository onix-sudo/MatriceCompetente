<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- This file renders the personal profile page
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field
     The JSTL core tag provides variable support and flow control
     --%>

<h2>Personal profile</h2>
<p>
<hr>
    <b>Name:</b> ${user.nume} ${user.prenume}
    <br>
    <b>ID number</b>: ${user.numarMatricol}
    <br>
    <b>Email:</b>
    <security:authentication property="principal.username"/>
    <br>
    <b>Starting date:</b> ${user.dataAngajare}
    <br>
    <b>Position:</b> ${user.functie}
    <br>
    <hr>
</p>

<c:choose>
<c:when test="${empty projectSkills && empty userSkills}">
<h4>No added skills.</h4>
</c:when>
<c:otherwise>

<c:choose>
<c:when test="${empty projectSkills}">
<h4>No added project.</h4>
</c:when>
<c:otherwise>
<h4><p><b>Project skills:</b></p></h4>
<table class="table table-striped">
 <thead class="thead-dark">
 <tr>
        <th>Skills</th>
        <th>Category</th>
        <th>Assessment</th>
 </tr>
 </thead>
    <tbody>
        <c:forEach var="projectSkills" items="${projectSkills}">
            <tr>
                <td>${projectSkills.skill.numeSkill}</td>
                <td>${projectSkills.skill.categorie}</td>
                <td>${projectSkills.evaluation}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<br>
</c:otherwise>
</c:choose>

<c:choose>
<c:when test="${empty userSkills}">
<h4>No additional skills.</h4>
</c:when>
<c:otherwise>
<h4><p><b>Additional skills:</b></p></h4>
<table class="table table-striped">
 <thead class="thead-dark">
    <tr>
        <th>Skills</th>
        <th>Category</th>
        <th>Assessment</th>
        <th>Edit assessment</th>
        <th></th>
    </tr>
 </thead>
    <tbody>
        <c:forEach var="userSkill" items="${userSkills}">

           <c:url var="deleteLink" value="/webCM/deleteSkill">
               <c:param name="skillId" value="${userSkill.skill.idSkill}"/>
           </c:url>
            <tr>
                <td>${userSkill.skill.numeSkill}</td>
                <td>${userSkill.skill.categorie}</td>
                <td>${userSkill.evaluation}</td>
                <td>
                    <form onsubmit="return sendData(${userSkill.skill.idSkill}, eval.value)" id="pForm">
                        <select id="eval" name="evaluation">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                        </select>
                        <input type="submit" class="btn btn-outline-primary" value="Change">
                    </form>
                </td>
                <td>
                    <a onclick="if((confirm('Are you sure you want to eliminate the skill?'))) return elimina(${userSkill.skill.idSkill})">
                        <button type="button" class="btn btn-outline-danger">Delete</button>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<br>
</c:otherwise>
</c:choose>

</c:otherwise>
</c:choose>

<div>

<button type="button" class="btn btn-outline-danger" onclick="schimbaParola()">Change password</button>
<button type="button" class="btn btn-outline-primary" onclick="addSkill()">Add skill</button>
<button type="button" class="btn btn-outline-primary" onclick="viewHistory()">History</button>



</div>

<script>
    function saveSelect() {

    }

    function sendData(idSkill, evaluation) {
        $.ajax({
            type: "GET",
            url: "/webCM/modifyP?evaluation=" + evaluation + "&idskill=" + idSkill,
            success: function(data){
                $("#tab2").click();
            },
            error: function(xhr, status) {
                $("#tab2").click();
                console.log("eroare");
            }
        });

        return false;
    }

    function elimina(skillId) {
        $.ajax({
            url: "/webCM/deleteSkill",
            data:{skillId:skillId},
            success: function(res){
                $("#tab2").click();
            },
            error: function(res) {
                console.log(res);
                console.log("Error");
            }
        });

        return false;
    }

    function addSkill() {
        $("#div2").load("webCM/personalProfile/showFormForAddSkill");
    }

    function viewHistory() {
        $("#div2").load("webCM/personalProfile/viewHistory");
    }

    function schimbaParola() {
        $("#div2").load("/changePassword");
    }
</script>


</html>