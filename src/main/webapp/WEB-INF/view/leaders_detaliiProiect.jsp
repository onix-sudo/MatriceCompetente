<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- This file renders the project details page
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field
     The JSTL core tag provides variable support and flow control
     --%>

<script type="text/javascript" src="/resources/js/radarForTeam.js"></script>

<security:csrfMetaTags />

<br>

 <button type="button" class="btn btn-outline-primary"
         onclick="return addCollaborators('${varPath}')">Colaboratori</button>

 <button type="button" class="btn btn-outline-primary"
 onclick="return adaugaCompetente()">Competente</button>

  <button type="button" class="btn btn-danger" onclick="renunta()" style="float: right;">Renunta la proiect</button>
<br>
<br>





 <hr>

 <font size="5">Numele proiectului: ${project.numeProiect}</font>
 <br>
 <font size="5"> Cod: ${project.codProiect}</font>
 <br><hr>


<div class="container">


<style>
td
{
    text-align: center;
    vertical-align: middle;

}
th
{
    text-align: center;
    vertical-align: middle;
    font-weight: bold;
}
</style>

                <table class="table table-bordered table-striped table-hover">
                    <tr class="table-Class">
                        <td></td>
                        <c:forEach var="skills" items="${matrixTeam[0].proiectSkills}">
                            <td>${skills.skill.numeSkill}</td>
                        </c:forEach>
                        <td>Total</td>
                    </tr>
                    <tr class="table-danger">
                        <td><b>Pondere</b></td>
                            <c:forEach var="projectSkill" items="${matrixTeam[0].proiectSkills}">
                                <td><b>${projectSkill.pondere}</b></td>
                            </c:forEach>
                            <td></td>
                    </tr>
                    <tr class="table-primary">
                        <td><b>Necesar</b></td>
                        <c:forEach var="projectSkill" items="${matrixTeam[0].proiectSkills}">
                            <td><b>${projectSkill.target}</b></td>
                                <script>
                                    pushTargetVal(${projectSkill.target});
                                </script>
                        </c:forEach>

                        <td></td>
                    </tr>
                    <tr></tr>
                    <c:forEach var="user" items="${matrixTeam}" varStatus="status">

                        <tr>
                            <td>${user.name}</td>
                            <c:forEach var="skills" items="${user.skills}">
                          <script>
                                pushSkillTeam("${skills.skill.numeSkill}", ${skills.evaluation});
                          </script>
                                <td>${skills.evaluation}</td>
                            </c:forEach>
                            <script>
                                pushAllUsers("${user.user.nume} " + "${user.user.prenume}");
                            </script>
                            <td>${user.score}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

        <c:forEach var="user" items="${matrixTeam}" varStatus="status">
                <script>
                    pushProjectName("${user.proiectSkills[status.index].proiect.numeProiect}");
                </script>
        </c:forEach>

        <div id="chart" style="width: 1000px;height: 800px;margin: 0px auto">
            <script>
                plotRadarTeam();
            </script>
        </div>

<br><br><br><br><br><br><br><br><br><br>
<hr>


<script>

    function loadWebCM() {
        $("#tab1").click();
        return false;
    }

    function addCollaborators(varPath) {
        $("#div3").load("/webCM/leaders/project/" + varPath + "/adaugaColaboratori");

        return false;
    }

    function adaugaCompetente() {
        $("#div3").load("/webCM/leaders/project/${varPath}/addSkills");

        return false;
    }

    function renunta() {
        $.ajax({
            type: "POST",
            headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
            url: "/webCM/leaders/project/${codProiect}/renuntaLaProiect",
            success: function(res){
                if(res == "ceva")
                    $("#tab3").click();
                },
            error: function(res){
                    console.log("ERROR");
                    console.log(res)
                }
        });
    }

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
