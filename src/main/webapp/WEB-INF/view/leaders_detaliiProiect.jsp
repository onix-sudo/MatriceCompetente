<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<security:csrfMetaTags />

<br>

 <button type="button" class="btn btn-info"
         onclick="return addCollaborators('${varPath}')">Modifica colaboratori</button>

 <button type="button" class="btn btn-info"
 onclick="return adaugaCompetente()">Modifica competente</button>

  <button type="button" class="btn btn-warning"
  onclick="leaders()">Inapoi</button>

<br>
<br>

<form:form id = "renuntaForm">
  <input type="submit" class="btn btn-danger" value = "Renunta la proiect">
</form:form>


 <hr>


 <font size="5">Numele proiectului: ${project.numeProiect}</font>
 <br>
 <font size="5"> Cod: ${project.codProiect}</font>
 <br><hr>



       <br>
       <hr>




<script>
    function addCollaborators(varPath) {
        $("#div3").load("/webCM/leaders/project/" + varPath + "/adaugaColaboratori");

        return false;
    }

    function adaugaCompetente() {
        $("#div3").load("/webCM/leaders/project/${varPath}/addSkills");

        return false;
    }

    $("#renuntaForm").submit(function(e) {
        e.preventDefault();

        $.ajax({
            type: "POST",
            headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
            url: "${pageContext.request.contextPath}/webCM/leaders/project/${codProiect}/renuntaLaProiect",
            contentType : "application/json",
            success: function(res){
                if(res == "ceva")
                    $("#tab3").click();
                },
            error: function(res){
                    console.log("ERROR");
                    console.log(res)
                }
        });
    });





    function changePondere(valuePondere, skillId) {
        var url = "/webCM/leaders/project/${varPath}/setPondere?value=" + valuePondere + "&skillId=" + skillId;
        $.ajax({
            type: "GET",
            headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
            data: {value: valuePondere, skillId: skillId},
            url: url,
            success: function(){
                $("#div3").load("/webCM/leaders/project/" + '${codProiect}');
            },
            error: function(res){
                    console.log("ERROR");
                    console.log(res);
            }
        });

        return false;
    }

    function changeTarget(valueTarget, skillId) {
        var url = "/webCM/leaders/project/${varPath}/setTarget?value=" + valueTarget + "&skillId=" + skillId;
        $.ajax({
            type: "GET",
            headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
            data: {value: valueTarget, skillId: skillId},
            url: url,
            success: function(){
                $("#div3").load("/webCM/leaders/project/" + '${codProiect}');
            },
            error: function(res){
                    console.log("ERROR");
                    console.log(res);
            }
        });

        return false;
    }
</script>
