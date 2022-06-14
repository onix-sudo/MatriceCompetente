<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- This page renders the add-skill-to-project form
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field
     The JSTL core tag provides variable support and flow control
     --%>

<security:csrfMetaTags/>

<br>

 <button type="button" class="btn btn-outline-primary"
 onclick="return addCollaborators('${varPath}');">Employees</button>

 <button type="button" class="btn btn-outline-primary"
 onclick="return modify('${codProiect}')">Back</button>

  <br>
 <hr>

<c:choose>
<c:when test="${empty skills}">
<h4>No skills added to the project.</h4>
</c:when>
<c:otherwise>
  <h3>Skills</h3>
<table class="table table-striped">
           <thead class="thead-dark">
          <tr>
             <th>Skill</th>
             <th>Category</th>
             <th>Average</th>
             <th>Needed</th>
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
                     <button class="btn btn-outline-danger" onclick="return removeSkillForProject(${skill.skill.idSkill})">
                         Delete
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
            <table class="table table-striped">
                       <thead class="thead-dark">
                <tr>
                    <th></th>
                    <th>
                    <label>Search skill: </label>
                    <input type="text" placeholder="Skill" pattern=".{3,}" id = "searchTermSkill" title="The field must contain at least 3 characters." required/>
                    <input type="submit" class="btn btn-outline-primary" value="Search"></th>
                    <th></th>
                </tr>
                </thead>
            </table>
        </form:form>

<c:if test="${not empty result}">
     <table class="table table-striped">
            <thead class="thead-dark">
             <tr>
                 <th>Skill</th>
                 <th>Category</th>
                 <th></th>
             </tr>
            </thead>
             <c:forEach var="tempResult" items="${result}" varStatus="status">


             <tr id = "tableRow">
                 <td>${tempResult.numeSkill}</td>
                 <td>${tempResult.categorie}</td>
                 <td>
                     <button class="btn btn-outline-primary" onclick="return addSkill(${tempResult.idSkill}, ${status.count}, '${search}')">
                        Add
                     </button>
                 </td>
             </tr>
             </c:forEach>
     </table>
</c:if>
<br>



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