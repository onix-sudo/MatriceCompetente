<%@ include file="header.jspf"%>
<%@ include file="navigation.jspf"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>


<h2>Add Skill</h2>

<%--
<!--            Display username and role-->
--%>
<p>
    User email address:
    <security:authentication property="principal.username"/>
    <br>
</p>

<br>

<body>
<spring:url var="go" value="/retex/personalProfile/showFormForAddSkill/search" >
</spring:url>

<form:form action="${go}" method="get">

    <label>Search</label>
    <input type="text" minlength="3" name = "searchTerm" title="Campul trebuie sa contina cel putin 2 caractere." required/>


    <input type="submit" value="search"/>

</form:form>

<!--<div id="container">-->
<!--    <div id="content">-->

        <core:if test="${result != null}">
            <table>
                <thead>
                <tr>
                    <th>Nume Skill</th>
                    <th>Categorie</th>
                </tr>
                </thead>
                <core:forEach var="tempResult" items="${result}">
                    <spring:url var="addSkill" value="/retex/personalProfile/showFormForAddSkill/search/addSkillToUser">
                        <spring:param name="skillId" value="${tempResult.idSkill}"/>
                    </spring:url>
                    <tr>
                        <td>${tempResult.numeSkill}</td>
                        <td>${tempResult.categorie}</td>
                        <td>
                            <a href="${addSkill}">Add</a>
                        </td>
                    </tr>
                </core:forEach>

            </table>
        </core:if>


<!--    </div>-->

<!--</div>-->

    <%--
    <!--        Add a logout button-->
    --%>


</body>

</html>