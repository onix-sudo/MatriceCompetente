<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<security:csrfMetaTags/>

<br>
  <button type="button" class="btn btn-warning"
  onclick="return back()">Inapoi</button>
<hr>

<form:form action="/webCM/leaders/addProject" modelAttribute = "newProject" method="POST" id="addProject" accept-charset = "utf-8">
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
            <td><input type="submit" value="Save" class="save"/></td>
        </tr>
        </thead>
    </table>

</form:form>


<script type="text/javascript">
    function back() {
        $("#div3").load("/webCM/leaders/");

        return false;
    }

<%--function doAjaxPost() {

    $.ajax({
        type: "POST",
        headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
        url: "/webCM/leaders/addProject",
        data: $('form[name=newProject]').serialize(),
            success: function(res){
                if(res.validated){
                alert("Registration Successful");
            }else{
              $.each(res.errorMessages,function(key,value){
  	            $('input[name='+key+']').after('<span class="error">'+value+'</span>');
              });
            }
         },
         error: function(res){
                console.log(res);
         };

    });

};--%>
   /*  Submit form using Ajax */
   $("#addProject").submit(function(e) {

      //Prevent default submission of form
      e.preventDefault();

      //Remove all errors
      $('input').next().remove();

      $.ajax({
         type : "POST",
         url : "/webCM/leaders/addProject",
         headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
         data : $('form[name=newProject]').attr("content"),
         success : function(res) {

            if(res.validated){
                alert("Registration Successful");
            }else{
              //Set error messages
              $.each(res.errorMessages,function(key,value){
  	            $('input[name='+key+']').after('<span class="error">'+value+'</span>');
              });
            }

         },
                     error: function(res){
                             console.log("ERROR");
                             console.log(res)
                         }
      })
   });



</script>