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
                        </c:forEach>
                        <td></td>
                    </tr>
                    <tr></tr>
                    <c:forEach var="user" items="${matrixTeam}">
                        <tr>
                            <th>${user.name}</th>
                            <c:forEach var="skills" items="${user.skills}">
                                <th>${skills.evaluation}</th>
                            </c:forEach>
                            <th>${user.score}</th>
                        </tr>
                    </c:forEach>
                </table>
            </div>
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