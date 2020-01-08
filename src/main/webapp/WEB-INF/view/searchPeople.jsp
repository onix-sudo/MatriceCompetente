
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%-- This file renders the search people page
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field
     The JSTL core tag provides variable support and flow control
     --%>

<spring:url var="go" value="/webCM/leaders/searchPeople/search" >
    </spring:url>

    <form id="searchForm">
        <h3>Cauta colaboratori in functie de competenta</h3>
        <hr>
        <br>
        <label>
        Cautare competenta:
        </label>
        <input type="text" pattern=".{1,}" placeholder="Numele competentei" name = "searchTerm" title="Campul trebuie sa contina cel putin 4 caractere." required/>
        <label>
                 Evaluare minima:
        </label>
        <td>
            <select name="evaluation">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
            </select>
            <input type='submit' value='Cauta' class="btn btn-outline-primary" />
        </td>
    </form>

<core:if test="${usersSkills != null}">

  <spring:url var="download" value="/webCM/leaders/pdfDownload">
            <spring:param name="downloadSearchTerm" value="${param.searchTerm}"/>
            <spring:param name="downloadEvaluationTerm" value="${param.evaluation}"/>
        </spring:url>
    <br>
                                <form:form action="${download}" method="POST">
                                    <input type="submit" class="btn btn-warning" value="Descarca lista">
                                </form:form>

<table class="table table-striped">
    <thead class="thead-dark">
    <tr>
        <th>Nume User</th>
        <th>Functie</th>
        <th>Competenta</th>
        <th>Categorie</th>
        <th>Evaluare</th>
        <th></th>
    </tr>
    </thead>

    <core:forEach var="userSkill" items="${usersSkills}">
        <tr>
            <td>${userSkill.user.nume} ${userSkill.user.prenume}</td>
            <td>${userSkill.user.functie}</td>
            <td>${userSkill.skill.numeSkill}</td>
            <td>${userSkill.skill.categorie}</td>
            <td>${userSkill.evaluation}</td>
            <td>
            </td>
        </tr>
    </core:forEach>
    <br>
</table>

</core:if>

<script>
    $("#searchForm").submit(function(e){
        e.preventDefault();

        var form = $(this);
        var url = "/webCM/leaders/searchPeople/search";

        $.ajax({
                type: "GET",
                url: url,
                data: form.serialize(),
                success: function(data){
                    $("#div4").html(data);

                },
                error: function(data) {
                    $("#div4").html(data);
                }
            });
    })

</script>

