<%@ include file="header.jspf"%>
<%@ include file="navigation.jspf"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>


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

<!--    <c:url var="deleteLink" value="/retex/deleteSkill">-->
<!--        <c:param name="skillId" value="${skill.idSkill}"/>-->
<!--    </c:url>-->

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
        <!--            <td>-->
        <!--                <form method="GET" action="/retex/cmptMat">-->
        <!--                    <input type="submit" value="${proiect.numeProiect}" name="proiect">-->
        <!--                    <input type="hidden" value="${proiect.proiectId}" name="proiectId" style="display: none" >-->
        <!--                </form>-->
        <!--            </td>-->
        <!--            <td>${proiect.codProiect}</td>-->
    </tr>
</c:forEach>
    </tbody>
</table>
<button type="button" class="btn btn-success" onclick="window.location.href='/retex/employee'">Angajat</button>
<button type="button" class="btn btn-success" onclick="window.location.href='/retex/leaders'">Manager</button>

<!--<br>-->

<!--&lt;!&ndash;<form:form action="adaugaColaboratori" method="POST">&ndash;&gt;-->
<!--&lt;!&ndash;    <table>&ndash;&gt;-->
<!--&lt;!&ndash;        <tr>&ndash;&gt;-->
<!--&lt;!&ndash;            <th><label>Search</label>&ndash;&gt;-->
<!--&lt;!&ndash;                <input type="text" minlength="3" name = "cauta" title="Campul trebuie sa contina cel putin 2 caractere." required/>&ndash;&gt;-->
<!--&lt;!&ndash;                <input type="submit" value="search"/></th>&ndash;&gt;-->
<!--&lt;!&ndash;            <div><th><button type="button" class="btn btn-info" onclick="window.location.href='/retex/showFormForAddSkill'">Adauga Skill</button>&ndash;&gt;-->
<!--&lt;!&ndash;            </th></div>&ndash;&gt;-->
<!--&lt;!&ndash;        </tr>&ndash;&gt;-->
<!--&lt;!&ndash;    </table>&ndash;&gt;-->
<!--&lt;!&ndash;</form:form>&ndash;&gt;-->


<!--</body>-->
<!--<br>-->
<!--<p>Adauga Skill:</p>-->

<!--<body>-->
<!--<spring:url var="go" value="/retex/personalProfile/showFormForAddSkill/search" >-->
<!--</spring:url>-->

<!--<form:form action="${go}" method="get">-->

<!--            <label>Search</label>-->
<!--            <input type="text" minlength="3" name = "searchTerm" title="Campul trebuie sa contina cel putin 2 caractere." required/>-->


<!--            <input type="submit" value="search"/>-->

<!--</form:form>-->

<!--<div id="container">-->
<!--    <div id="content">-->

<!--        <core:if test="${result != null}">-->
<!--            <table>-->
<!--                <tr>-->
<!--                    <th>Nume Skill</th>-->
<!--                    <th>Categorie</th>-->

<!--                </tr>-->
<!--                <core:forEach var="tempResult" items="${result}">-->
<!--                    <spring:url var="addSkill" value="/retex/personalProfile/showFormForAddSkill/search/addSkillToUser">-->
<!--                        <spring:param name="skillId" value="${tempResult.idSkill}"/>-->
<!--                    </spring:url>-->
<!--                    <tr>-->
<!--                        <td>${tempResult.numeSkill}</td>-->
<!--                        <td>${tempResult.categorie}</td>-->

<!--                        <td>-->
<!--                            <a href="${addSkill}">Add</a>-->
<!--                        </td>-->
<!--                    </tr>-->
<!--                </core:forEach>-->

<!--            </table>-->
<!--        </core:if>-->


<!--    </div>-->

<%--
<!--        Add a logout button-->
--%>


</body>

</html>