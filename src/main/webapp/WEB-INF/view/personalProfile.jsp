<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>


<h2>Profil Personal</h2>
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
    <b>Rol:</b> ${user.functie}
    <br>
    <hr>
</p>

<c:choose>
<c:when test="${empty projectSkills && empty userSkills}">
<h4>Nu ai nicio competenta adauata.</h4>
</c:when>
<c:otherwise>

<c:choose>
<c:when test="${empty projectSkills}">
<h4>Nu ai fost adaugat in niciun proiect.</h4>
</c:when>
<c:otherwise>
<h4><p><b>Competente din proiecte:</b></p></h4>
<table class="table table-striped">
 <thead class="thead-dark">
 <tr>
        <th>Competente</th>
        <th>Categorie</th>
        <th>Evaluare</th>
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
<h4>Nu ai adaugat nicio competenta aditionala.</h4>
</c:when>
<c:otherwise>
<h4><p><b>Competente aditionale:</b></p></h4>
<table class="table table-striped">
 <thead class="thead-dark">
    <tr>
        <th>Competente</th>
        <th>Categorie</th>
        <th>Evaluare</th>
        <th>Valoare noua</th>
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
                        <input type="submit" class="btn btn-outline-primary" value="Schimba">
                    </form>
                </td>
                <td>
                    <a onclick="if((confirm('Esti sigur ca vrei sa elimini competenta?'))) return elimina(${userSkill.skill.idSkill})">
                        <button type="button" class="btn btn-outline-danger">Elimina</button>
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

<button type="button" class="btn btn-outline-danger" onclick="schimbaParola()">Schimba parola</button>
<button type="button" class="btn btn-outline-primary" onclick="addSkill()">Adauga competenta</button>
<button type="button" class="btn btn-outline-primary" onclick="viewHistory()">Istoric</button>

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
                console.log("succes");
            },
            error: function(xhr, status) {
                $("#tab2").click();
                console.log("eroare");
            }
        });

        return false;
    }

    function elimina(skillId) {
        console.log("AICI");
        console.log(skillId);
        $.ajax({
            url: "/webCM/deleteSkill",
            data:{skillId:skillId},
            success: function(res){
                console.log("success");
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