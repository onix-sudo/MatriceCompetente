<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<security:csrfMetaTags/>

<br>

 <button type="button" class="btn btn-info"
 onclick="return adaugaCompetente();">Modifica competente</button>

 <button type="button" class="btn btn-warning"
 onclick="return modify('${codProiect}')">Inapoi</button>

<br>
<hr>


<table class="table">
         <thead>
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
                <spring:url var="removeUser" value="/webCM/leaders/project/${varPath}/removeEmp">
                    <spring:param name="userId" value="${user.id}"/>
                </spring:url>
               <tr>
                 <td>${user.nume}</td>
                 <td>${user.prenume}</td>
                 <td>${user.numarMatricol}</td>
                 <td>
                    <%--<form:form id="eliminaEmpForm">
                        <input type="submit" class="btn btn-danger" value="Elimina-l din proiect">
                    </form:form>--%>
                    <button class="btn btn-danger" onclick="return removeEmpFromProject(${user.id})">Elimina-l din
                    proiect</button>
                 </td>
               </tr>
            </c:forEach>
         </tbody>
</table>

 <br><hr>
        <form:form onsubmit="return searchUser(document.getElementById('searchTermUser').value)">
            <table class="table">
                <tr>
                    <th><label>Search</label>
                    <input type="text" pattern=".{3,}" id = "searchTermUser" title="Campul trebuie sa contina cel
                    putin 3 caractere." required/>
                    <input type="submit" value="Search"></th>
                </tr>
            </table>
        </form:form>
 <br>
         <c:if test="${not empty result}">
             <table class="table">
                 <tr>
                     <th>Nume</th>
                     <th>Prenume</th>
                     <th>Numar Matricol</th>
                     <th>Email</th>
                     <th>Rol</th>
                     <th>Adaugare</th>
                 </tr>
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
                        <button class="btn btn-lg btn-primary"
                        onclick="return addUser(${tempResult.id}, '${searchTermUser}')">Adauga</button>
                     </td>
                 </tr>
                 </c:forEach>

             </table>
         </c:if>


<script>

        function addUser(userId, searchTerm) {
            $.ajax({
                url: "/webCM/leaders/project/${varPath}/adaugaColaboratori/add",
                type: "POST",
                headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
                data: {userId: userId},
                success: function(){
                    searchUser(searchTerm);
                },
                error: function(res){
                    console.log("ERROR");
                    console.log(res);
                    searchUser(searchTerm);
                }
            });

            return false;
        }

        function searchUser(searchTerm) {
            var url = "webCM/leaders/project/" + '${codProiect}' + "/adaugaColaboratori";
            console.log(searchTerm);

            $.ajax({
                   type: "POST",
                   url: url,
                   data: {searchTerm : searchTerm}, // serializes the form's elements.
                   headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
                   success: function(data)
                   {
                       console.log("SUCCESS");
                       console.log(data);
                       $("#div3").html(data);
                   },
                   error: function(data, res)
                   {
                       console.log(res);
                       console.log("ERROR");
                       $("#div3").html(data);
                   }
            });

            return false;
        }

        function removeEmpFromProject(userId) {
                    $.ajax({
                        type: "POST",
                        headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
                        data: {userId: userId},
                        url: "/webCM/leaders/project/${varPath}/removeEmp",
                        success: function(){
                            $("#div3").load("/webCM/leaders/project/" + varPath + "/adaugaColaboratori");
                        },
                        error: function(res){
                                console.log("ERROR");
                                console.log(res);
                        }
                    });

                    return false;
             }

</script>