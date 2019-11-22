<%@ include file="header.jspf"%>

<h2>Salut, ${userExpleo.prenume}!</h2>

<c:choose>
<c:when test="${not loginUser.isExpired()}">
<div>
    <form:form action="/forgotPassword/newPassword/save" method="POST">
    <table>
        <tr>
            <th><label> Parola noua: </label>
            <input name="password" type="password" required/></th>
        </tr>
        <tr>
            <th><label> Confirma parola noua: </label>
            <input name="confirmPassword" type="password" required/></th>
        </tr>
        <input type="hidden" name="userId" value="${loginUser.id}">
    </table>
   <button type="submit" class = "btn btn-success">Change Password</button>
   </form:form>
</div>
</c:when>
<c:otherwise>
<h2> Token-ul nu este valid. <h2>
</c:otherwise>
</c:choose>

<%@ include file="footer.jspf"%>