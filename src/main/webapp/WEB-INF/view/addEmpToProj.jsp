<%@ include file="leadersHeader.jspf"%>

<br>

 <button type="button" class="btn btn-info"
 onclick="window.location.href='/retex/leaders/${project.codProiect}/adaugaColaboratori'">Adauga colaboratori</button>

 <button type="button" class="btn btn-info"
 onclick="window.location.href='/retex/leaders/${project.codProiect}/adaugaCompetente'">Adauga competente</button>
 <br><hr>
 <font size="5">Detaliile proiectului: ${project.numeProiect}</font>
 <br>
 <font size="5"> Cod: ${project.codProiect}</font>

        <form:form action="adaugaColaboratori" method="POST">
            <table>
                <tr>
                    <th><label>Search</label>
                    <input type="text" minlength="3" name = "cauta" title="Campul trebuie sa contina cel putin 2 caractere." required/>
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
                 <spring:url var="modifyUser" value="/admin/updateUser/modify">
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
                              <a href="${modifyUser}">Adauga-l</a>
                        </c:otherwise>
                    </c:choose>
                 </td>
             </tr>
             </c:forEach>

         </table>
         </c:if>

<%@ include file="footer.jspf"%>