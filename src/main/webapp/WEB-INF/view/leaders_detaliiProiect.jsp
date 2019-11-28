<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>


<br>

 <button type="button" class="btn btn-info"
         onclick="return addCollaborators('${varPath}')">Adauga colaboratori</button>

 <button type="button" class="btn btn-info"
 onclick="window.location.href='/webCM/leaders/project/${varPath}/addSkills'">Adauga competente</button>

  <button type="button" class="btn btn-warning"
  onclick="window.location.href='/webCM/leaders/'">Proiecte</button>

<br>
<br>

<form:form action = "/webCM/leaders/project/${project.codProiect}/renuntaLaProiect" method = "POST">
  <input type="submit" class="btn btn-danger" value = "Renunta la proiect">
</form:form>


 <hr>


 <font size="5">Numele proiectului: ${project.numeProiect}</font>
 <br>
 <font size="5"> Cod: ${project.codProiect}</font>
 <br><hr>

     <table>
         <tbody>
          <font size="4">Colaboratori</font>
         <tr>
            <th>Nume</th>
            <th>Prenume</th>
            <th>Numar Matricol</th>
            <th><th>
         </tr>
         </thead>

         <tbody>
            <c:forEach var="user" items="${users}">
                <spring:url var="removeUser" value="/webCM/leaders/project/${varPath}/removeEmp">
                    <spring:param name="userId" value="${user.id}"/>
                </spring:url>



               <tr>
                 <td>${user.nume}</td>
                 <td>${user.prenume}</td>
                 <td>${user.numarMatricol}</td>
                 <td>
                    <form:form action="${removeUser}" method="POST">
                        <input type="submit" class="btn btn-danger" value="Elimina-l din proiect">
                    </form:form>
                 </td>
               </tr>
            </c:forEach>
         </tbody>
      </table>

       <br>
       <hr>

     <table>
         <tbody>
         <font size="4">Competente</font>
         <tr>
            <th>Competenta</th>
            <th>Categorie</th>
            <th>Pondere</th>
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
                                 <form:select path="intervalPondere" onChange="window.location.href='/webCM/leaders/project/${varPath}/setPondere?value='+this.value+'&skillId=${skill.skill.idSkill}'">
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
                    <form:form action="${removeSkill}" method="POST">
                        <input type="submit" class="btn btn-danger" value="Elimina competenta"
                    </form:form>
                 </td>
               </tr>
            </c:forEach>
         </tbody>
      </table>


<script>
    function addCollaborators(varPath) {
        $("#div3").load("/webCM/leaders/project/" + varPath + "/adaugaColaboratori");

        return false;
    }
</script>
