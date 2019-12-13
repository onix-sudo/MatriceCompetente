<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

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
</p>


<br>


<spring:url var="go" value="/webCM/personalProfile/showFormForAddSkill/search" >
</spring:url>

<form:form id="searchForm" onsubmit="return search()">
<label><b>Cautare competente:</b></label>
    <input type="text" placeholder="Numele competentei" pattern=".{3,}" id="searchTerm" name = "searchTerm" title="Campul trebuie sa contina cel putin 4
    caractere."
           required/>

    <input type="submit" value="Cauta" class="btn btn-primary" />

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
<!--                    <form id="skillForm">-->
                        <button onclick="return ps(${tempResult.idSkill}, ${user.id})">Add</button>
<!--                        <input type="hidden" value="${tempResult.idSkill}" name="skillId"/>-->
<!--                    </form>-->
                </td>
            </tr>
        </core:forEach>
    </table>
</core:if>



<button type="button" onclick="showSkills()" class="btn btn-info">Back</button>

<script>
    function ps(skillID, userID) {
    $.ajax({
            type: "GET",
            url: "/webCM/personalProfile/showFormForAddSkill/search/addSkillToUser?skillId=" + skillID+"&userID="+userID,
            success: function(data){
                $("#tab2").click();
            },
            error: function(xhr, status) {
                $("#tab2").click();
            }
        });

        console.log(skillID);

        return false;
    }

    function search() {
        $("#div2").load("/webCM/personalProfile/showFormForAddSkill/search?searchTerm=" + $("#searchTerm").val());

        return false;
    }



</script>

