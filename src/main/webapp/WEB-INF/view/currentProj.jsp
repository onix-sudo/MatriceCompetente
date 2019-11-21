<table class="table">
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
            <form method="GET" action="/webCM/cmptMat">
                <td>
                    <input type="submit" value="${proiect.numeProiect}" name="proiect">
                    <input type="hidden" value="${proiect.proiectId}" name="proiectId" style="display: none" >
                </td>
            </form>
            <td>${proiect.codProiect}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>