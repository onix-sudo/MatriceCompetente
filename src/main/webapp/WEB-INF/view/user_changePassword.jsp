<%@ include file="header.jspf"%>

<div>
    <form:form action="changePassword/save" method="POST" modelAttribute = "password">
    <table>
<%--        <tr>
            <th><label> Parola actuala: </label>
             <input name="oldPassword" type="password" required/></th>
         </tr>
        <tr>
            <th><label> Parola noua: </label>
            <input name="password" type="password" required/></th>
        </tr>
        <tr>
            <th><label> Confirma parola noua: </label>
            <input name="confirmPassword" type="password" required/></th>
        </tr>--%>
        <tr>
                    <td><label> Parola actuala: </label><td>
                    <form:input type="password" path="oldPassword" /></td>
                </tr>

                <tr>
                    <td><label> Parola noua: </label><td>
                    <td><form:input type="password" path="newPassword"  />
                    <form:errors path="newPassword" cssClass="error"/></td>
                </tr>

                <tr>
                    <td><label> Confirma parola noua: </label><td>
                    <form:input type="password" path="confirmPassword" /></td>
                </tr>
        </tr>

    </table>
   <button type="submit" class = "btn btn-success">Change Password</button>
   </form:form>
</div>

<%@ include file="footer.jspf"%>