<%@ include file="header.jspf"%>
<%@ include file="navigation.jspf"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<h2>Profil Personal</h2>

<%--
<!--            Display username and role-->
--%>
<p>
    User email address:
    <security:authentication property="principal.username"/>
    <br><br>
    Skilluri personale:
</p>

<table>
    <div><button type="button" class="btn btn-info" onclick="window.location.href='/retex/personalProfile/showFormForAddSkill'">Adauga Skill</button>
    </div>
    <br>
    <thead>
    <tr>
        <th>Nume Skill</th>
        <th>Categorie</th>
        <th>Elimina</th>
        <th>Modifica</th>
    </tr>
    </thead>
    <tbody>

<c:forEach var="userSkill" items="${userSkills}">

   <c:url var="deleteLink" value="/retex/deleteSkill">
       <c:param name="skillId" value="${userSkill.skill.idSkill}"/>
   </c:url>

    <c:url var="modify" value="/retex/modify">
        <c:param name="skillId" value="${userSkill.skill.idSkill}"/>
    </c:url>

    <tr>
        <td>${userSkill.skill.numeSkill}</td>
        <td>${userSkill.skill.categorie}</td>
        <td>
            <a href="${deleteLink}" onclick="if(!(confirm('Are you sure you want to delete this skill?'))) return false">
                <button type="button" class="btn btn-danger">X</button>
            </a><!--Java script code-->
        </td>
        <td>
            <a href="${modify}">
                <button type="button" class="btn btn-warning">X</button>
            </a><!--Java script code-->
        </td>

    </tr>
</c:forEach>
    </tbody>
</table>
<br>
<button type="button" class="btn btn-success" onclick="window.location.href='/retex/employee'">Angajat</button>
<button type="button" class="btn btn-success" onclick="window.location.href='/retex/leaders'">Manager</button>


</html>