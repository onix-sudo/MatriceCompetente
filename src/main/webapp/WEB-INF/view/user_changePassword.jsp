<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- This file renders the change-password form
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field
     The JSTL core tag provides variable support and flow control
     --%>

<br>
<h3>Change password</h3>
<hr>
<br>
<div>
    <form:form id="changePasswordForm" modelAttribute = "password">
        <table class="table table-striped">
            <thead class="thead-light">
                <tr>
                    <th><label> Old password: </label></th>
                    <th><form:input type="password" path="oldPassword" /></th>
                    <%--<th><form:errors path="oldPassword" cssClass="error"/></th>--%>
                </tr>

                <tr>
                    <th><label> New password: </label></th>
                    <th><form:input type="password" path="newPassword"  /></th>

                </tr>

                <tr>
                    <th><label> Confirm new password: </label></th>
                    <th><form:input type="password" path="confirmPassword" /></th>

                </tr>
                <tr>
                    <th></th>
                    <th>
                          <button type="submit" class = "btn btn-outline-success">Submit</button>
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

