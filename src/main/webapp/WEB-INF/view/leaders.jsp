<%@ include file="leadersHeader.jspf"%>

<br>

 <button type="button" class="btn btn-success"
 onclick="window.location.href='/retex/leaders/addNewProject'">Creeaza un proiect</button>

 <button type="button" class="btn btn-info"
 onclick="window.location.href='/retex/employee'">Adauga angajati</button>

 <button type="button" class="btn btn-info"
 onclick="window.location.href='/retex/employee'">Adauga competente</button>

 <button type="button" class="btn btn-success"
 onclick="window.location.href='/retex/employee'">Alege un proiect fara manager</button>

 <button type="button" class="btn btn-danger"
 onclick="window.location.href='/retex/employee'">Renunta la proiect</button>

<hr>
    <table>
        <thead>
            <h2>Proiectele tale:</h2>
            <tr>
                <th>Nume proiect</th>
                <th>Cod proiect</th>
                <th>Modifica</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="proiecte" items="${projects}">
                <tr>
                    <td>${proiecte.numeProiect}</td>
                    <td>${proiecte.codProiect}</td>
                    <td>
                        <a href="/retex/leaders/${proiecte.codProiect}"> Modifica </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>

    </table>
<hr>
<a href="${pageContext.request.contextPath}/">Back to home page.</a>

<%@ include file="footer.jspf"%>