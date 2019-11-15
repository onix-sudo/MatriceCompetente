<%@ include file="leaders_leadersHeader.jspf"%>

<br>

 <button type="button" class="btn btn-info"
 onclick="window.location.href='/retex/leaders/${project.codProiect}/adaugaColaboratori'">Adauga colaboratori</button>

 <button type="button" class="btn btn-info"
 onclick="window.location.href='/retex/leaders/${project.codProiect}/addSkills'">Adauga competente</button>

 <spring:url var="renunta" value="/retex/leaders/${project.codProiect}/renuntaLaProiect"></spring:url>


<form:form action = "${renunta}" method = "POST">
  <input type="submit" class="btn btn-danger" value = "Renunta la proiect">
</form:form>

 <br><hr>


 <font size="5">Detaliile proiectului: ${project.numeProiect}</font>
 <br>
 <font size="5"> Cod: ${project.codProiect}</font>
 <br><br><br>

     <table>
         <tbody>
          <font size="4">Colaboratori</font>
         <tr>
            <th>Nume</th>
            <th>Prenume</th>
            <th>Numar Matricol</th>
            <th><th>
         </tr>
         </thead>

         <tbody>
            <c:forEach var="user" items="${users}">
                <spring:url var="removeUser" value="/retex/leaders/${varPath}/removeEmp">
                    <spring:param name="userId" value="${user.id}"/>
                </spring:url>

               <tr>
                 <td>${user.nume}</td>
                 <td>${user.prenume}</td>
                 <td>${user.numarMatricol}</td>
                 <td>
                    <form:form action="${removeUser}" method="POST">
                        <input type="submit" class="btn btn-danger" value="Elimina-l din proiect">
                    </form:form>
                 </td>
               </tr>
            </c:forEach>
         </tbody>
      </table>

       <br>
       <hr>
       <br>

     <table>
         <tbody>
         <font size="4">Competente</font>
         <tr>
            <th>Competenta</th>
            <th>Categorie</th>
         </tr>
         </thead>

         <tbody>
            <c:forEach var="skill" items="${skills}">
               <tr>
                 <td>${skill.skill.numeSkill}</td>
                 <td>${skill.skill.categorie}</td>
               </tr>
            </c:forEach>
         </tbody>
      </table>



<%@ include file="footer.jspf"%>