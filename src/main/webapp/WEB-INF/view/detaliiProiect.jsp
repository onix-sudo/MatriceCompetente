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
 <br><br><br>

     <table>
         <tbody>
          <font size="4">Colaboratori</font>
         <tr>
            <th>Nume</th>
            <th>Prenume</th>
            <th>Numar Matricol</th>
         </tr>
         </thead>

         <tbody>
            <c:forEach var="user" items="${users}">
               <tr>
                 <td>${user.nume}</td>
                 <td>${user.prenume}</td>
                 <td>${user.numarMatricol}</td>
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
                 <td>${skill.numeSkill}</td>
                 <td>${skill.categorie}</td>
               </tr>
            </c:forEach>
         </tbody>
      </table>









<%@ include file="footer.jspf"%>