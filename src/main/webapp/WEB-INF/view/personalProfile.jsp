<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


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
    <b>Functie:</b> ${user.functie}
    <br>
    <hr>
    <b>Skilluri personale:</b>
</p>

<table class="table">

    <thead>
    <tr>
        <th>Competente</th>
        <th>Categorie</th>
        <th>Auto-Evaluare</th>
        <th>Valoare noua</th>
        <th>Elimina</th>
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
                    <form method="GET" onsubmit="return sendData()" id="pForm">
                        <select name="evaluation">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                        </select>
                        <input type="hidden" name="idskill" value="${userSkill.skill.idSkill}"/>
                        <input type="submit" value="Submit">
                    </form>
                </td>
                <td>
                    <a onclick="if((confirm('Are you sure you want to delete this skill?'))) return elimina()">
                        <button type="button" class="add-button">X</button>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<br>

<div>

<button type="button" class="btn btn-info" onclick="addSkill()">Adauga Skill</button>
</div>

<script>
    function sendData() {
        $.ajax({
            type: "GET",
            data: $("#pForm").serialize(),
            url: "/webCM/modifyP",
            success: function(data){
                $("#tab2").click();
            },
            error: function(xhr, status) {
                $("#tab2").click();
            }
        });

        return false;
    }

    function elimina() {
        console.log("AICI");
        $.ajax({
            url: "${deleteLink}",
            success: function(data){
                $("#tab2").click();
            },
            error: function(xhr, status) {
                $("#tab2").click();
            }
        });

        return false;
    }

    function addSkill() {
        $("#div2").load("webCM/personalProfile/showFormForAddSkill");
    }
</script>


</html>