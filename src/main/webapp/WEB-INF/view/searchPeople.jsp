
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:url var="go" value="/webCM/leaders/searchPeople/search" >
    </spring:url>

    <form id="searchForm">
        <h4>Introduceti numele competentei si evaluarea minima</h4>
        <br>
        <label>Search</label>
        <input type="text" pattern=".{4,}" name = "searchTerm" title="Campul trebuie sa contina cel putin 4 caractere." required/>
        <td>
            <select name="evaluation">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
            </select>
            <input type='submit' value='Submit' />
        </td>
    </form>

<core:if test="${usersSkills != null}">

<table class="table">
    <thead>
    <tr>
        <th>Nume User</th>
        <th>Functie</th>
        <th>Competenta</th>
        <th>Categorie</th>
        <th>Evaluare</th>
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
        <spring:url var="download" value="/webCM/leaders/pdfDownload">
            <spring:param name="downloadSearchTerm" value="${param.searchTerm}"/>
            <spring:param name="downloadEvaluationTerm" value="${param.evaluation}"/>
        </spring:url>
    <br>
                                <form:form action="${download}" method="POST">
                                    <input type="submit" class="btn btn-warning" value="Descarca lista">
                                </form:form>
</core:if>

<br>
<button type="button" class="btn btn-info" onclick="window.location.href='/webCM/leaders'">Back</button>

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

