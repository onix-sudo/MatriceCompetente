<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<security:csrfMetaTags/>

<br>

 <button type="button" class="btn btn-primary"
 onclick="return addCollaborators('${varPath}');">Colaboratori</button>

 <button type="button" class="btn btn-warning"
 onclick="return modify('${codProiect}')">Inapoi</button>

  <br>
 <hr>

<c:choose>
<c:when test="${empty skills}">
<h4>Nicio competenta adaugata proiectului.</h4>
</c:when>
<c:otherwise>
 <table class="table">
          <tbody>
          <font size="4">Competente</font>
          <tr>
             <th>Competenta</th>
             <th>Categorie</th>
             <th>Pondere</th>
             <th>Target</th>
             <th></th>
          </tr>
          </thead>

          <tbody>
             <c:forEach var="skill" items="${skills}">

                <spring:url var="removeSkill" value="/webCM/leaders/project/${varPath}/removeSkill">
                    <spring:param name="skillId" value="${skill.skill.idSkill}"/>
                </spring:url>

                <tr>
                  <td>${skill.skill.numeSkill}</td>
                  <td>${skill.skill.categorie}</td>
                     <td>
                         <form:select path="intervalPondere" onChange="return changePondere(this.value, ${skill.skill.idSkill})">
                             <c:set var="pondere" value="${skill.pondere}"/>
                                 <c:forEach items="${intervalPondere}" var="temp">
                                     <c:choose>
                                     <c:when test="${temp eq pondere}">
                                     <option value="${temp}" selected="true">${temp}</option>
                                     </c:when>
                                     <c:otherwise>
                                     <option value="${temp}">${temp}</option>
                                     </c:otherwise>
                                     </c:choose>
                                 </c:forEach>
                         </form:select>
                     </td>
                     <td>
                         <form:select path="intervalTarget" onChange="return changeTarget(this.value, ${skill.skill.idSkill})">
                             <c:set var="target" value="${skill.target}"/>
                                 <c:forEach items="${intervalTarget}" var="temp">
                                     <c:choose>
                                     <c:when test="${temp eq target}">
                                     <option value="${temp}" selected="true">${temp}</option>
                                     </c:when>
                                     <c:otherwise>
                                     <option value="${temp}">${temp}</option>
                                     </c:otherwise>
                                     </c:choose>
                                 </c:forEach>

                         </form:select>
                         </td>
                  <td>
                     <button class="btn btn-danger" onclick="return removeSkillForProject(${skill.skill.idSkill})">
                         Elimina
                     </button>
                  </td>
                </tr>
             </c:forEach>
          </tbody>
       </table>
</c:otherwise>
</c:choose>

 <br><hr>
        <form:form onsubmit="return searchSkills(document.getElementById('searchTermSkill').value)">
            <table class="table">
                <tr>
                    <th>
                    <input type="text" placeholder="Numele competentei" pattern=".{3,}" id = "searchTermSkill" title="Campul trebuie sa contina cel putin
                     3
                    caractere." required/>
                    <input type="submit" class="btn btn-primary" value="Cauta"></th>
                </tr>
            </table>
        </form:form>

 <br>

<c:if test="${not empty result}">
         <table class="table" id="skillTable">

             <tr>
                 <th>Competenta</th>
                 <th>Categorie</th>
                 <th></th>
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

    function removeSkillForProject(skillId) {
            $.ajax({
                type: "POST",
                headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
                data: {skillId: skillId},
                url: "/webCM/leaders/project/${varPath}/removeSkill",
                success: function(){
                    $("#div3").load("/webCM/leaders/project/${varPath}/addSkills");
                },
                error: function(res){
                        console.log("ERROR");
                        console.log(res);
                }
            });

            return false;
    }



</script>