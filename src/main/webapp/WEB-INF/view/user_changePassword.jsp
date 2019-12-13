<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<button type="button" onclick="showSkills()" class="btn btn-outline-primary">Inapoi</button>
<hr>
<h3>Schimba parola</h3>
<br>
<div>
    <form:form id="changePasswordForm" modelAttribute = "password">
        <table class="table table-striped">
            <thead class="thead-light">
                <tr>
                    <th><label> Parola actuala: </label></th>
                    <th><form:input type="password" path="oldPassword" /></th>
                    <%--<th><form:errors path="oldPassword" cssClass="error"/></th>--%>
                </tr>

                <tr>
                    <th><label> Parola noua: </label></th>
                    <th><form:input type="password" path="newPassword"  /></th>

                </tr>

                <tr>
                    <th><label> Confirma parola noua: </label></th>
                    <th><form:input type="password" path="confirmPassword" /></th>

                </tr>
                <tr>
                    <th></th>
                    <th>
                          <button type="submit" class = "btn btn-outline-success">Schimba</button>
                    </th>
                </tr>

        </tr>
        </thead>
    </table>

<br>
         <c:forEach var="message"  items = "${errors}">
        <span class="error">${message}</span>
        <br>
         </c:forEach>
   </form:form>
</div>

<script>
    $("#changePasswordForm").submit(function(e) {

          //Prevent default submission of form
          e.preventDefault();

          var form = $(this);

          $.ajax({
             type : "POST",
             url : "/changePassword/save",
             headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
             data : form.serialize(),
             success : function(data) {
                $("#div2").html(data);
             },
             error: function(data){
                console.log("ERROR");
                $("#div2").html(data);
                //console.log(res);
             }
          })
    });
</script>

