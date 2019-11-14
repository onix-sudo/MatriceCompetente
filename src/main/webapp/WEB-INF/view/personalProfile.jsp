<%@ include file="header.jspf"%>
<%@ include file="navigation.jspf"%>


<h2>Personal Profile</h2>

<%--
<!--            Display username and role-->
--%>
<p>
    User email address:
    <security:authentication property="principal.username"/>
    <br><br>
    Skilluri personale:
    <br><br>


</p>

<table>
    <div><button type="button" class="btn btn-info" onclick="window.location.href='/retex/showFormForAddSkill'">Adauga Skill</button>
    </div>
    <br><br>
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
<!--    <c:forEach var="skill" items="${skill}">-->

<!--        <c:url var="deleteLink" value="/retex/deleteSkill">-->
<!--            <c:param name="skillId" value="${skill.idSkill}"/>-->
<!--        </c:url>-->

<!--        <tr>-->
<!--            <td>${skill.numeSkill}</td>-->
<!--            <td>${skill.categorie}</td>-->
<!--            <td>-->
<!--                <select>-->
<!--                    <option value="1">1</option>-->
<!--                    <option value="2">2</option>-->
<!--                    <option value="3">3</option>-->
<!--                    <option value="4">4</option>-->
<!--                    <option value="5">5</option>-->
<!--                </select>-->
<!--            </td>-->
<!--            <td>-->
<!--                <a href="${deleteLink}" onclick="if(!(confirm('Are you sure you want to delete this skill?'))) return false">-->
<!--                    <button type="button" class="btn btn-danger">X</button>-->
<!--                </a>&lt;!&ndash;Java script code&ndash;&gt;-->
<!--            </td>-->
<!--&lt;!&ndash;            <td>&ndash;&gt;-->
<!--&lt;!&ndash;                <form method="GET" action="/retex/cmptMat">&ndash;&gt;-->
<!--&lt;!&ndash;                    <input type="submit" value="${proiect.numeProiect}" name="proiect">&ndash;&gt;-->
<!--&lt;!&ndash;                    <input type="hidden" value="${proiect.proiectId}" name="proiectId" style="display: none" >&ndash;&gt;-->
<!--&lt;!&ndash;                </form>&ndash;&gt;-->
<!--&lt;!&ndash;            </td>&ndash;&gt;-->
<!--&lt;!&ndash;            <td>${proiect.codProiect}</td>&ndash;&gt;-->
<!--        </tr>-->
<!--    </c:forEach>-->
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

<br>


<button type="button" class="btn btn-success" onclick="window.location.href='/retex/employee'">Angajat</button>
<button type="button" class="btn btn-success" onclick="window.location.href='/retex/leaders'">Manager</button>
<%--
<!--        Add a logout button-->
--%>



</body>
</html>