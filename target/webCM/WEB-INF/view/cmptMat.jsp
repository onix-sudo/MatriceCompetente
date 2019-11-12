<%@ include file="header.jspf"%>

    <div>
        <%@ include file="navigation.jspf"%>

    </div>


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
                </tr>
                </thead>

                <tbody>
                    <c:forEach var="skill" items="${skillList}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${skill.idSkill}</td>
                            <td>${skill.numeSkill}</td>
                            <td>${skill.categorie}</td>
                            <td>
                                <form method="GET" action="/retex/cmptMat">
                                    <input type="submit" value="${proiect.numeProiect}" name="proiect">
                                    <input type="hidden" value="${proiect.proiectId}" name="proiectId" style="display: none" >
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div class="outer">
        <div class="inner">

        </div>
    </div>

    <div class="split right">
        <div class="centered">
        </div>
    </div>

<%@ include file="footer.jspf"%>