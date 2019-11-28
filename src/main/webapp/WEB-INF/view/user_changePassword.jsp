<%@ include file="header.jspf"%>

<div>
    <form:form action="${pageContext.request.contextPath}/changePassword/save" method="POST" modelAttribute = "password">
    <table>
        <tr>
                    <th><label> Parola actuala: </label></th>
                    <th><form:input type="password" path="oldPassword" /></th>
                </tr>

                <tr>
                    <th><label> Parola noua: </label></th>
                    <th><form:input type="password" path="newPassword"  /></th>
                </tr>

                <tr>
                    <th><label> Confirma parola noua: </label></th>
                    <th><form:input type="password" path="confirmPassword" /></th>
                </tr>

        </tr>

    </table>
   <button type="submit" class = "btn btn-success">Change Password</button>
<br>
         <c:forEach var="message"  items = "${errors}">
        <span class="error">${message}</span>
        <br>
         </c:forEach>
   </form:form>
</div>

<%@ include file="footer.jspf"%>