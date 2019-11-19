<%@ include file="header.jspf"%>

<h2>Home Page</h2>
<hr>
<p>
    Welcome!
</p>
<hr>
<p>
    User:
    <security:authentication property="principal.username"/>
    <br><br>
    Role(s):
    <security:authentication property="principal.authorities"/>
    <br><br>

    <button type="button" class="btn btn-info" onclick="window.location.href='/retex/personalProfile'">Personal Profile</button>


</p>

<hr>

<hr>

    <table>
        <thead>
        <tr>
            <th>Nr</th>
            <th>ID Proiect</th>
            <th>Nume Proiect</th>
            <th>Cod</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="proiect" items="${proiectList}" varStatus="status">
            <tr>
                <td>${status.index + 1}</td>
                <td>${proiect.proiectId}</td>
                <td>
                    <form method="GET" action="/retex/cmptMat">
                        <input type="submit" value="${proiect.numeProiect}" name="proiect">
                        <input type="hidden" value="${proiect.proiectId}" name="proiectId" style="display: none" >
                                               </form>
                </td>
                <td>${proiect.codProiect}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

<br>


 <button type="button" class="btn btn-success" onclick="window.location.href='/'">HomePage</button>
 <button type="button" class="btn btn-success" onclick="window.location.href='/retex/leaders'">Manager</button>

<%@ include file="footer.jspf"%>