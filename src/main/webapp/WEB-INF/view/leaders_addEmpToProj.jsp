<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- This page renders the add-user-to-project form
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field
     The JSTL core tag provides variable support and flow control
     --%>

<security:csrfMetaTags/>

<br>

 <button type="button" class="btn btn-outline-primary"
 onclick="return adaugaCompetente();">Competente</button>

 <button type="button" class="btn btn-outline-primary"
 onclick="return modify('${codProiect}')">Inapoi</button>

<hr>

<c:choose>
<c:when test="${empty users}">
<h4>Niciun colaborator adaugat proiectului.</h4>
</c:when>
<c:otherwise>
<table class="table table-striped">
           <thead class="thead-dark">
             <h3>Colaboratori</h3>
             <tr>
                <th>Nume</th>
                <th>Prenume</th>
                <th>Numar Matricol</th>
                <th></th>
             </tr>
         </thead>

         <tbody>
            <c:forEach var="user" items="${users}">
                <spring:url var="removeUser" value="/webCM/leaders/project/${varPath}/removeEmp">
                    <spring:param name="userId" value="${user.id}"/>
                </spring:url>
               <tr>
                 <td>${user.nume}</td>
                 <td>${user.prenume}</td>
                 <td>${user.numarMatricol}</td>
                 <td>
                    <button class="btn btn-outline-danger" onclick="return removeEmpFromProject('${user.id}')">Elimina</button>
                 </td>
               </tr>
            </c:forEach>
         </tbody>
</table>
</c:otherwise>
</c:choose>

 <br><hr>
        <form:form onsubmit="return searchUser(document.getElementById('searchTermUser').value)">
            <table class="table table-striped">
                   <thead class="thead-dark">
                <tr>
                    <th>
                    <label>Cautare colaboratori: </label>
                    <input type="text" placeholder="Numele colaboratorului" pattern=".{3,}" id = "searchTermUser" title="Campul trebuie sa contina cel
                    putin 3 caractere." required/>
                    <input type="submit" class="btn btn-outline-primary" value="Cauta"></th>
                </tr>
                </thead>
            </table>
        </form:form>
         <c:if test="${not empty result}">
             <table class="table table-striped">
                        <thead class="thead-dark">
                 <tr>
                     <th>Nume</th>
                     <th>Prenume</th>
                     <th>Numar Matricol</th>
                     <th>Email</th>
                     <th>Rol</th>
                     <th></th>
                 </tr>
                 </thead>
                 <tbody>
                 <c:forEach var="tempResult" items="${result}">
                     <spring:url var="modifyUser" value="/webCM/leaders/project/${varPath}/adaugaColaboratori/add">
                         <spring:param name="userId" value="${tempResult.id}"/>
                     </spring:url>
                 <tr>
                     <td>${tempResult.nume}</td>
                     <td>${tempResult.prenume}</td>
                     <td>${tempResult.numarMatricol}</td>
                     <td>${tempResult.email}</td>
                     <td>${tempResult.functie}</td>

                     <td>
                        <button class="btn btn-outline-primary"
                        onclick="addUser(${tempResult.id}, '${param.searchTerm}')">Adauga</button>
                     </td>
                 </tr>
                 </c:forEach>
                 </tbody>

             </table>
         </c:if>


<script>
        function addUser(userId, searchTerm) {
            var url="/webCM/leaders/project/" + '${codProiect}' + "/adaugaColaboratori/add?userId="+userId;
            $.ajax({
                type: "POST",
                url: url,
                headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
                success: function(res){
                    searchUser(searchTerm);
                },
                error: function(res){
                    console.log(res);
                    console.log("ERROR - addUser");

                }
            });
        }

        function searchUser(searchTerm) {
            var url = "/webCM/leaders/project/" + '${codProiect}' + "/adaugaColaboratori";

            $.ajax({
                   type: "POST",
                   url: url,
                   data: {searchTerm : searchTerm}, // serializes the form's elements.
                   headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
                   success: function(data)
                   {
                       $("#div3").html(data);
                   },
                   error: function(data, res)
                   {
                       console.log(res);
                       console.log("ERROR - searchUser");
                   }
            });

            return false;
        }

        function removeEmpFromProject(userId) {
                    $.ajax({
                        type: "POST",
                        headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
                        data: {userId:userId},
                        url: "webCM/leaders/project/" + '${codProiect}' + "/removeEmp",
                        success: function(){
                            $("#div3").load("/webCM/leaders/project/" + '${codProiect}' + "/adaugaColaboratori");
                        },
                        error: function(res){
                                console.log("ERROR");
                                console.log(res);
                        }
                    });
                    return false;
             }

</script>