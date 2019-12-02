<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<br>
  <button type="button" class="btn btn-warning"
  onclick="return back()">Inapoi</button>
<hr>

<form:form name="newProject" modelAttribute = "newProject" method="POST" accept-charset = "utf-8">
    <table>
        <thead>
        <tr>
            <td><label>Nume proiect:</label></td>
            <td><form:input path="numeProiect"/></td>
            <td><form:errors path="numeProiect" cssClass="error"/></td>
        </tr>

        <tr>
            <td><label>Cod proiect:</label></td>
            <td><form:input path="codProiect"/></td>
            <td><form:errors path="codProiect" cssClass="error"/></td>
        </tr>

        <tr>
            <td><label></label></td>
            <td><input type="button" onclick="doAjaxPost()" value="Save" class="save"/></td>
        </tr>
        </thead>
    </table>

</form:form>


<script type="text/javascript">
    function back() {
        $("#div3").load("/webCM/leaders/");

        return false;
    }

function doAjaxPost() {

    $.ajax({
        type: "POST",
        url: "/webCM/leaders/addProject",
        data: $('form[name=newProject]').serialize(),
        success: function(error){
            console.log(error)
            if(error){
                                $("#errorContainer").html(error);
             }else{
<%--                 errorInfo = "";
                 for(i =0 ; i < response.errorMessages.size ; i++){
                     errorInfo += "<br>" + (i + 1) +". " + response.errorMessages[i];
                 }
                 $('#error').html("Please correct following errors: " + errorInfo);
                 $('#info').hide('slow');
                 $('#error').show('slow');--%>
                 $("#tab1").click();
             }
         },
         error: function(error){
                console.log(error);
             alert('Error: ' + error);
         }
         return false;
    });
}



</script>