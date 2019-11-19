<%@ include file="leaders_leadersHeader.jspf"%>
<br>

  <button type="button" class="btn btn-warning"
  onclick="window.location.href='/webCM/leaders/'">Proiecte</button>

<h2> Proiecte disponibile: </h2>

       <table>
             <tr>
                 <th>Nume proiect</th>
                 <th>Cod</th>
                 <th></th>

             </tr>

             <tr>
             <c:forEach var="tempResult" items="${result}">

             <spring:url var="addFreeProject" value="/webCM/leaders/freeProjects/add">
                <spring:param name="codProiect" value="${tempResult.codProiect}"/>
             </spring:url>

                 <td>${tempResult.numeProiect}</td>
                 <td>${tempResult.codProiect}</td>
                 <td>
                    <form:form action="${addFreeProject}" method = "POST">
                        <input type="submit" class="btn btn-success" value="Preia">
                    </form:form>
                 </td>

             </tr>
             </c:forEach>

         </table>


<%@ include file="footer.jspf"%>