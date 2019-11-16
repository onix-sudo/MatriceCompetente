<%@ include file="header.jspf"%>
<%@ include file="navigation.jspf"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<h2>Personal Profile</h2>

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
        <th>evaluare</th>
        <th>Evaluare</th>
        <th>Elimina</th>
    </tr>
    </thead>
    <tbody>

<c:forEach var="userSkill" items="${userSkills}">

   <c:url var="deleteLink" value="/retex/deleteSkill">
       <c:param name="skillId" value="${userSkill.skill.idSkill}"/>
   </c:url>

    <tr>
        <td>${userSkill.skill.numeSkill}</td>
        <td>${userSkill.skill.categorie}</td>
        <td>${userSkill.evaluation}</td>

        <td>
            <select>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>
        </td>
        <td>
            <a href="${deleteLink}" onclick="if(!(confirm('Are you sure you want to delete this skill?'))) return false">
                <button type="button" class="btn btn-danger">X</button>
            </a><!--Java script code-->
        </td>

    </tr>
</c:forEach>
    </tbody>
</table>
<button type="button" class="btn btn-success" onclick="window.location.href='/retex/employee'">Angajat</button>
<button type="button" class="btn btn-success" onclick="window.location.href='/retex/leaders'">Manager</button>


</html>