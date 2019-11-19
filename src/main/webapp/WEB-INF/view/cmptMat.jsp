<%@ include file="header.jspf"%>

<div>
    <%@ include file="navigation.jspf"%>
</div>


<div class="container">
    <form method="GET" action="/retex/cmptMat/modifyT">
        <div class="split left">
            <div class="centered">
                <table>
                    <thead>
                    <tr>
                        <th>Nr</th>
                        <th>ID Skill</th>
                        <th>Nume Skill</th>
                        <th>Categorie</th>
                        <th>Evaluare</th>
                        <th>Schimba evaluare</th>
                    </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="skill" items="${skillList}" varStatus="status">
                           <script>pushSkill("${skill.skill.numeSkill}", ${userSkillList[status.index].evaluation});</script>
                            <tr>
                                <td>${status.count}</td>
                                <td>${skill.skill.idSkill}</td>
                                <td>${skill.skill.numeSkill}</td>
                                <td>${skill.skill.categorie}</td>
                                <td>${userSkillList[status.index].evaluation}</td>
                                <td>
                                    <select name="evaluation">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                    </select>
                                    <!--                    <input type="number" name="evaluation" value=""/>-->
<!--                                    <input type=hidden name="idproiect" value="${skill.proiect.proiectId}"/>-->
                                    <input type=hidden name="idskill" value="${skill.skill.idSkill}"/>
                                    <input type="hidden" value="${skill.proiect.proiectId}" name="proiectId"
                                           style="display: none" >
                                    <input type='submit' value='Submit' />
                                </td>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div>
<!--                <input type="submit" class="btn btn-success place-btn" value="Update" name="updateEv">-->
<!--                <input type="hidden" value="${proiect.proiectId}" name="proiectId" style="display: none" >-->
            </div>
        </div>
    </form>


    <div class="split right">
        <div class="centered">
            <div id="chartDiv" style="max-width: 450px;height: 500px;margin: 0px auto">

                <script>
                    plotRadar();
                </script>
            </div>
        </div>
    </div>
</div>

<div>
    <%@ include file="footer.jspf"%>
</div>


