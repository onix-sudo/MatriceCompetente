<%@ include file="leaders_leadersHeader.jspf"%>

<br>

 <button type="button" class="btn btn-info"
 onclick="window.location.href='/webCM/leaders/${varPath}/addSkills'">Adauga competente</button>

 <button type="button" class="btn btn-warning"
 onclick="window.location.href='/webCM/leaders/${varPath}'">Inapoi</button>



 <br><hr>
        <form:form action="adaugaColaboratori" method="POST">
            <table>
                <tr>
                    <th><label>Search</label>
                    <input type="text" pattern=".{3,}" name = "searchTerm" title="Campul trebuie sa contina cel putin 4 caractere." required/>
                    <input type="submit" value="search"/></th>
                </tr>
            </table>
        </form:form>
 <br>
         <c:if test="${not empty result}">
         <table>
             <tr>
                 <th>Nume</th>
                 <th>Prenume</th>
                 <th>Numar Matricol</th>
                 <th>Email</th>
                 <th>Rol</th>
                 <th>Adaugare</th>
             </tr>
             <c:forEach var="tempResult" items="${result}">
                 <spring:url var="modifyUser" value="/webCM/leaders/${varPath}/adaugaColaboratori/add">
                     <spring:param name="userId" value="${tempResult.id}"/>
                 </spring:url>
             <tr>
                 <td>${tempResult.nume}</td>
                 <td>${tempResult.prenume}</td>
                 <td>${tempResult.numarMatricol}</td>
                 <td>${tempResult.email}</td>
                 <td>${tempResult.functie}</td>

                 <td>

                              <form:form action="${modifyUser}" method="POST">
                                <input type="submit" class="btn btn-success" value="Adauga-l">
                              </form:form>

                 </td>
             </tr>
             </c:forEach>

         </table>
         </c:if>

<%@ include file="footer.jspf"%>