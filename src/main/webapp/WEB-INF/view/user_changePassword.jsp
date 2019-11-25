<%@ include file="header.jspf"%>

<div>
    <form:form action="changePassword/save" method="POST">
    <table>
        <tr>
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
        </tr>
    </table>
   <button type="submit" class = "btn btn-success">Change Password</button>
   </form:form>
</div>

<%@ include file="footer.jspf"%>