<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- This file renders the skill-form page
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field
     The JSTL core tag provides variable support and flow control
     --%>

<h2>Adauga competenta aditionala</h2>

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


<spring:url var="go" value="/webCM/personalProfile/showFormForAddSkill/search" >
</spring:url>

<form id="searchForm">
<label><b>Cautare competente:</b></label>
    <input type="text" placeholder="Numele competentei" pattern=".{1,}" id="searchTerm" name = "searchTerm" title="Campul trebuie sa contina cel putin 1
    caracter."
           required/>

    <input type="submit" value="Cauta" class="btn btn-outline-primary" />

</form>



<core:if test="${result != null}">
     <table class="table table-striped">
         <thead class="thead-dark">
            <tr>
                <th>Nume Competenta</th>
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
                        <button class="btn btn-outline-primary" onclick="return ps(${tempResult.idSkill}, ${user.id})">Adauga</button>
                </td>
            </tr>
        </core:forEach>
    </table>
</core:if>



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
        return false;
    }

        $("#searchForm").submit(function(e){
            e.preventDefault();

            var form = $(this);
            var url = "/webCM/personalProfile/showFormForAddSkill/search";

            $.ajax({
                    type: "GET",
                    url: url,
                    data: form.serialize(),
                    success: function(data){
                        $("#div2").html(data);

                    },
                    error: function(data) {
                        $("#div2").html(data);
                    }
                });
        })

</script>

