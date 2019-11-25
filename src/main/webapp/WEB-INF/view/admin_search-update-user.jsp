<%@ include file="admin_header.jspf" %>



        <spring:url var="go" value="/admin/updateUser/search" >

        </spring:url>

        <form:form action="${go}" method="get">
            <table>
                <tr>
                    <th><label>Cauta angajat</label></th>
                    <th><input type="text" pattern=".{3,}" name = "searchTerm" title="Campul trebuie sa contina cel putin 4 caractere." required/>
                </tr>

                <tr>
                    <td></td>
                    <td><input type="submit" value="Cauta" class="btn btn-success"/></td>
                </tr>
            </table>
        </form:form>
<div id="container">
    <div id="content">
        <p>
        		User: <security:authentication property="principal.username" />
        </p>
        <c:if test="${result != null}">
        <table>
            <tr>
                <th>Nume</th>
                <th>Prenume</th>
                <th>Numar Matricol</th>
                <th>Email</th>
                <th>Functie</th>
                <th></th>
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
                 <button type="button" class="btn btn-info" onclick="window.location.href='${modifyUser}'">Modifica</button>
<%--
                <a href="${modifyUser}">Modify</a>
--%>
                </td>
            </tr>
            </c:forEach>

        </table>
        </c:if>


    </div>


</div>

<%@ include file="footer.jspf" %>