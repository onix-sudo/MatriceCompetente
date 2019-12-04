<%@ include file="header.jspf"%>

<security:csrfMetaTags />

<div class="container">
<%--        <div class="split left">
             <th>
                  <form:select path="proiecte" onChange="sendData(this.value)">

                             <c:forEach items="${proiecte}" var="proiect">
                                   <option value="${proiect.codProiect}"> ${proiect.numeProiect} ${proiect.codProiect}</option>
                             </c:forEach>

                  </form:select>
                </th>--%>
                <table class="table">
                    <tr>
                        <td></td>
                        <c:forEach var="projectSkill" items="${foundSkills}">
                            <th>${projectSkill.skill.numeSkill}</th>
                        </c:forEach>
                        <th>Scor</th>
                    </tr>

                    <c:forEach var="projectUser" items="${foundUsers}">
                        <tr>
                            <th>${projectUser.nume} ${projectUser.prenume}</th>
                            <c:forEach var="projectUserSkill" items="${foundUserSkills}">
                                <c:if test="${projectUser.id eq projectUserSkill.id.userId}">
                                <th>${projectUserSkill.evaluation}</th>
                                </c:if>
                            </c:forEach>
                            <th></th>
                        </tr>
                    </c:forEach>
                    <tr>
                        <th>Pondere</th>
                        <c:forEach var="projectSkill" items="${foundSkills}">
                            <td>${projectSkill.pondere}</td>
                        </c:forEach>

                    </tr>
                    <tr>
                        <th>Necesar</th>
                        <c:forEach var="projectSkill" items="${foundSkills}">
                            <td>${projectSkill.target}</td>
                        </c:forEach>

                    </tr>
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