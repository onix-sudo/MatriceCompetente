<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <title>Expleo webCM</title>

            <meta charset="utf-8">

            <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">

            <link rel="stylesheet"
                  href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/3.1.0/css/font-awesome.min.css" />

            <link rel="stylesheet" href="/resources/css/tabs.css"/>

            <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"
                  id="bootstrap-css">

            <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

            <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>


            <link rel="stylesheet" href="/resources/css/util.css">

            <link rel="stylesheet" href="/resources/css/navbar.css">

            <link rel="stylesheet" href="/resources/css/split-screen.css">

            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

            <script type="text/javascript" src="/resources/js/radar.js"></script>

            <script type="text/javascript" src="/resources/js/radarForTeam.js"></script>

            <script type="text/javascript" src="/resources/js/plotly-latest.min.js"></script>

            <%--<script src="https://cdn.plot.ly/plotly-latest.min.js"></script>--%>

            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
               integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

            <link rel="shortcut icon" type="image/png" href="/resources/favicon.png"/>

    </head>

    <%@ include file="navigation.jspf"%>

    <script>
    function loadWebCM() {
        $("#tab1").click();

        return false;
    }
    </script>

<body>

    <div class="ownBody">


<security:csrfMetaTags />

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>


<div class="container">
<%--
class="text-center"
--%>

<style>
td
{
    text-align: center;
    vertical-align: middle;
    font-weight: bold;
}
th
{
    text-align: center;
    vertical-align: middle;
    font-weight: bold;
}
</style>

                <table class="table table-active table-hover">
                    <tr>
                        <th></th>
                        <c:forEach var="skills" items="${matrixTeam[0].proiectSkills}">
                            <th>${skills.skill.numeSkill}</th>
                        </c:forEach>
                        <th>Total</th>
                    </tr>
                    <tr class="table-danger">
                        <td><b>Pondere</b></td>
                            <c:forEach var="projectSkill" items="${matrixTeam[0].proiectSkills}">
                                <td>${projectSkill.pondere}</td>
                            </c:forEach>
                            <td></td>
                    </tr>
                    <tr class="table-primary">
                        <td><i>Necesar</i></td>
                        <c:forEach var="projectSkill" items="${matrixTeam[0].proiectSkills}">
                            <td>${projectSkill.target}</td>

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
                                pushAllUsers("${user.user.nume} " + "${user.user.prenume}", "${user.proiectSkills[0].proiect.numeProiect}" );
                                pushTargetVal(${user.proiectSkills[status.index].target});
                            </script>
                            <td>${user.score}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>



                    <div id="chart" style="max-width: 900px;height: 800px;margin: 0px auto">

                        <script>
                            plotRadarTeam();
                        </script>
                    </div>


<script>
    function sendData(varPath) {
                $.ajax({
                    type: "POST",
                    headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
                    url: "${pageContext.request.contextPath}/webCM/leaders/project/"+varPath+"/matrix",
                    contentType : "application/x-www-form-urlencoded",
                    success: function(res){
                    console.log("${pageContext.request.contextPath}/webCM/leaders/project/"+varPath+"/matrix");
                      <%--  if(res == "ceva"){
                            console.log(res);
                            $("#tab3").click();
                            }
                        else
                            $("#tab1").click();--%>
                        },
                    error: function(res){
                            console.log("ERROR");
                            console.log("${pageContext.request.contextPath}/webCM/leaders/project/"+varPath+"/matrix");
                        }
                });
    }
</script>

