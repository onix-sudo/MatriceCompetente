<%@ include file="header.jspf"%>


<c:choose>
<c:when test="${loginUser.isExpired()}">
<h2>Salut, ${userExpleo.prenume}!</h2>
<div>
    <form:form action="/forgotPassword/newPassword/save" method="POST" modelAttribute = "password">
    <table>
        <tr>
            <th><label> Parola noua: </label>
            <form:input type="password" path="newPassword"/></th>
        </tr>
        <tr>
            <th><label> Confirma parola noua: </label>
            <form:input type="password" path="confirmPassword" /></th>
        </tr>
        <input type="hidden" name="userId" value="${loginUser.id}">
    </table>
   <button type="submit" class = "btn btn-success">Change Password</button>
   </form:form>
</div>
</c:when>
<c:otherwise>
<br><br>
<h2> <p> Link-ul este invalid. </p> <h2>

<script>
  setTimeout(function() {
      window.location.href = "/";
  }, 3000); // <-- this is the delay in milliseconds
</script>
</c:otherwise>
</c:choose>

<%@ include file="footer.jspf"%>