<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- This file renders the add-new-project form
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field
     The JSTL core tag provides variable support and flow control
     --%>


<security:csrfMetaTags/>

<h3> Create a new project</h3>
<hr>

<form:form action="/webCM/leaders/addProject" modelAttribute = "newProject" method="POST" id="addProject" accept-charset = "utf-8">
    <table class="table table-striped">
        <thead class="thead-light">

        <tr>
            <th><label>Project name:</label></th>
            <th><form:input path="numeProiect"/></th>
            <th><form:errors path="numeProiect" cssClass="error"/></th>
        </tr>

        <tr>
            <th><label>Project ID:</label></th>
            <th><form:input path="codProiect"/></th>
            <th><form:errors path="codProiect" cssClass="error"/></th>
        </tr>

        <tr>
            <th></th>
            <th><input type="submit" value="Create" class="btn btn-outline-primary"/></th>
            <th></th>
        </tr>
        </thead>
        <tbody></tbody>
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

      var form = $(this);

      $.ajax({
         type : "POST",
         url : "/webCM/leaders/addProject",
         headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
         data : form.serialize(),
         success : function(res, data) {
            if(res.validated){
                //alert("Registration Successful");
                $("#tab3").click();
            }else{
              //Set error messages
                $('input[name=numeProiect]').next().remove();
                $('input[name=codProiect]').next().remove();
              $.each(res.errorMessages,function(key,value){
  	            $('input[name='+key+']').after('<span class="error">'+value+'</span>');
              });
            }
         },
         error: function(res){
            console.log("ERROR");
            console.log(res);
         }
      })
   });



</script>