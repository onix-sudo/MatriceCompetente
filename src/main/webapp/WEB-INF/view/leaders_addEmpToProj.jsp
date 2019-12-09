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
 onclick="return adaugaCompetente();">Adauga competente</button>

 <button type="button" class="btn btn-warning"
 onclick="return modify('${codProiect}')">Inapoi</button>


 <br><hr>
            <table class="table">
                <tr>
                    <th><label>Search</label>
                    <input type="text" pattern=".{3,}" id = "searchTermUser" title="Campul trebuie sa contina cel
                    putin 3 caractere." required/>
                    <button onclick="return searchUser(document.getElementById('searchTermUser').value)">Search</button></th>
                </tr>
            </table>
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
                   success: function(data)
                   {
                       console.log("SUCCESS");
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



</script>