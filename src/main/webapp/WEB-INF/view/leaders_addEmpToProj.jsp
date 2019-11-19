<%@ include file="leaders_leadersHeader.jspf"%>

<br>

 <button type="button" class="btn btn-info"
 onclick="window.location.href='/retex/leaders/${varPath}/addSkills'">Adauga competente</button>

 <button type="button" class="btn btn-warning"
 onclick="window.location.href='/retex/leaders/${varPath}'">Inapoi</button>



 <br><hr>
        <form:form action="adaugaColaboratori" method="POST">
            <table>
                <tr>
                    <th><label>Search</label>
                    <input type="text" pattern=".{5,}" name = "cauta" title="Campul trebuie sa contina cel putin 4 caractere." required/>
                    <input type="submit" value="search"/></th>
                </tr>
            </table>
        </form:form>
 <br>
         <c:if test="${result != null}">
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
                 <spring:url var="modifyUser" value="/retex/leaders/${varPath}/adaugaColaboratori/add">
                     <spring:param name="userId" value="${tempResult.id}"/>
                 </spring:url>
             <tr>
                 <td>${tempResult.nume}</td>
                 <td>${tempResult.prenume}</td>
                 <td>${tempResult.numarMatricol}</td>
                 <td>${tempResult.email}</td>
                 <td>${tempResult.functie}</td>

                 <td>
                    <c:choose>
                        <c:when test="${hasProject}">
                            Deja adaugat
                        </c:when>

                        <c:otherwise>
                              <form:form action="${modifyUser}" method="POST">
                                <input type="submit" class="btn btn-success" value="Adauga-l">
                              </form:form>
                        </c:otherwise>
                    </c:choose>
                 </td>
             </tr>
             </c:forEach>

         </table>
         </c:if>

<%@ include file="footer.jspf"%>