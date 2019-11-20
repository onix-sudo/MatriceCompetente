<%@ include file="leaders_leadersHeader.jspf"%>

<br>

 <button type="button" class="btn btn-info"
 onclick="window.location.href='/webCM/leaders/${varPath}/adaugaColaboratori'">Adauga colaboratori</button>

 <button type="button" class="btn btn-warning"
 onclick="window.location.href='/webCM/leaders/${varPath}'">Inapoi</button>

 <br><hr>
        <form:form action="addSkills" method="POST">
            <table>
                <tr>
                    <th><label>Search</label>
                    <input type="text" pattern=".{3,}" name = "searchTerm" title="Campul trebuie sa contina cel putin 4 caractere." required/>
                    <input type="submit" value="Search"/></th>
                </tr>
            </table>
        </form:form>
 <br>
         <c:if test="${not empty result}">
         <table>
             <tr>
                 <th>Competenta</th>
                 <th>Categorie</th>
                 <th></th>

             </tr>
             <c:forEach var="tempResult" items="${result}">
                 <spring:url var="modifyUser" value="/webCM/leaders/${varPath}/addSkills/add">
                     <spring:param name="skillId" value="${tempResult.idSkill}"/>
                 </spring:url>
             <tr>
                 <td>${tempResult.numeSkill}</td>
                 <td>${tempResult.categorie}</td>

                 <td>

                              <form:form action="${modifyUser}" method="POST">
                                <input type="submit" class="btn btn-success" value="Adauga">
                              </form:form>

                 </td>
             </tr>
             </c:forEach>

         </table>
         </c:if>

<%@ include file="footer.jspf"%>