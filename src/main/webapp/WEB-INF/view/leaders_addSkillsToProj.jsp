<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<security:csrfMetaTags/>

<br>

 <button type="button" class="btn btn-info"
 onclick="return addCollaborators('${varPath}');">Adauga colaboratori</button>

 <button type="button" class="btn btn-warning"
 onclick="return modify('${codProiect}')">Inapoi</button>

 <br><hr>

            <table class="table">
                <tr>
                    <th><label>Search</label>
                    <input type="text" pattern=".{3,}" id = "searchTermSkill" title="Campul trebuie sa contina cel putin
                     3
                    caractere." required/>
                    <button onclick="return searchSkills(document.getElementById('searchTermSkill').value)
                    ">Search</button></th>
                </tr>
            </table>

 <br>

<c:if test="${not empty result}">
         <table class="table" id="skillTable">

             <tr>
                 <th>Competenta</th>
                 <th>Categorie</th>
                 <th>${search}</th>
             </tr>

             <c:forEach var="tempResult" items="${result}" varStatus="status">


             <tr id = "tableRow">
                 <td>${tempResult.numeSkill}</td>
                 <td>${tempResult.categorie}</td>
                 <td>
                     <button class="btn btn-lg btn-primary" onclick="return addSkill(${tempResult.idSkill}, ${status.count}, '${search}')">
                        Adauga
                     </button>
                 </td>
             </tr>
             </c:forEach>


         </table>
</c:if>


<script>


    function addSkill(skillId, tableRowNr, searchTerm) {
                $.ajax({
                    url: "/webCM/leaders/project/" + '${varPath}' + "/add",
                    type: "POST",
                    headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
                    data: {skillId: skillId},
                    success: function(){
                        searchSkills("${search}");
                    },
                    error: function(res){
                        console.log("ERROR");
                        console.log(res);
                        searchSkills(searchTerm);

                    }
                });

                return false;
    }

    function searchSkills(searchTerm) {
            $.ajax({
                url: "/webCM/leaders/project/" + '${codProiect}' + "/addSkills",
                type: "POST",
                headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
                data: {searchTerm: searchTerm},
                success: function(data){
                    $("#div3").html(data);
                },
                error: function(res){
                    console.log("ERROR");
                    console.log(res);
                    $("#div3").html(data);
                }
            });

            return false;
    }

</script>