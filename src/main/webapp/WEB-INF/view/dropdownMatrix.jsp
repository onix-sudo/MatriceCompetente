<%@ include file="header.jspf"%>

<security:csrfMetaTags />

<div class="container">

                <table class="table">
                    <tr>
                        <th></th>
                        <c:forEach var="skills" items="${matrixTeam[0].proiectSkills}">
                            <th>${skills.skill.numeSkill}</th>
                        </c:forEach>
                        <th>Total</th>
                    </tr>
                    <tr>
                        <th><i>Pondere</i></th>
                            <c:forEach var="projectSkill" items="${matrixTeam[0].proiectSkills}">
                                <td><i>${projectSkill.pondere}</i></td>
                            </c:forEach>
                            <td></td>
                    </tr>
                    <tr>
                        <th><i>Necesar</i></th>
                        <c:forEach var="projectSkill" items="${matrixTeam[0].proiectSkills}">
                            <td><i>${projectSkill.target}</i></td>
                                  <script>
                                        pushSkillTeam("target", ${projectSkill.target});
                                  </script>
                        </c:forEach>

                            <script>pushAllUsers("target",);</script>

                        <td></td>
                    </tr>
                    <tr></tr>
                    <c:forEach var="user" items="${matrixTeam}">
                        <tr>
                            <th>${user.name}</th>
                            <c:forEach var="skills" items="${user.skills}">
                          <script>
                                pushSkillTeam("${skills.skill.numeSkill}", ${skills.evaluation});
                          </script>
                                <th>${skills.evaluation}</th>
                            </c:forEach>
                            <script>pushAllUsers("${user.user.nume} " + "${user.user.prenume}", "${user.proiectSkills[0].proiect.numeProiect}");</script>
                            <th>${user.score}</th>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

                    <div id="chart" style="max-width: 850px;height: 800px;margin: 0px auto">

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