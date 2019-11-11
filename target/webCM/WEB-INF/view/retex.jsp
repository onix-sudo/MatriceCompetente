<%@ include file="../jspf/header.jspf"%>
<%@ include file="navigation.jspf"%>


<h2>Home Page</h2>
<hr>
<p>
    Welcome!
</p>
<hr>
<%--
<!--            Display username and role-->
--%>
<p>
    User:
    <security:authentication property="principal.username"/>
    <br><br>
    Role(s):
    <security:authentication property="principal.authorities"/>
</p>

<hr>
<%--
<!--            Add a link to /leaders and / admin-->
--%>


<hr>

<table>
    <thead>
        <tr>
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
                    <form method="GET" action="/leaders/projectMemb">
                        <input type="submit" value="${proiect.numeProiect}" name="proiect">
                        <input type="hidden" value="${proiect.proiectId}" name="proiectId" style="display: none" >
                    </form>
                </td>
                <td>${proiect.codProiect}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>


 <button type="button" class="btn btn-success" onclick="window.location.href='/retex/employee'">Angajat</button>
 <button type="button" class="btn btn-success" onclick="window.location.href='/retex/leaders'">Manager</button>
<%--
<!--        Add a logout button-->
--%>



</body>
</html>