<%@ include file="header.jspf"%>

<security:csrfMetaTags />

<%--<div class="container">
        <div class="split left">
            <div class="">
                <th>
                  <form:select path="proiecte" onChange="sendData(this.value)">

                             <c:forEach items="${proiecte}" var="proiect">
                                   <option value="${proiect.codProiect}"> ${proiect.numeProiect} ${proiect.codProiect}</option>
                             </c:forEach>

                  </form:select>
                </th>
            </div>
        </div>

</div>--%>


<div class="container">
        <div class="split left">
            <div class="">
             <th>
                  <form:select path="proiecte" onChange="sendData(this.value)">

                             <c:forEach items="${proiecte}" var="proiect">
                                   <option value="${proiect.codProiect}"> ${proiect.numeProiect} ${proiect.codProiect}</option>
                             </c:forEach>

                  </form:select>
                </th>
                <table class="table">
                    <thead>
                    <tr>
                        <th>Nume</th>
                        <th>Skill1</th>
                        <th>Skill2</th>
                        <th>Skill1</th>
                        <th>Skill4</th>
                        <th>Skill5</th>
                    </tr>
                    </thead>

                  <%--  <tbody>
                        <c:forEach var="skill" items="${skillList}" varStatus="status">
                           <script>pushSkill("${skill.skill.numeSkill}", ${userSkillList[status.index].evaluation});</script>
                            <tr>
                                <td>${status.count}</td>
                                <td>${skill.skill.idSkill}</td>
                                <td>${skill.skill.numeSkill}</td>
                                <td>${skill.skill.categorie}</td>
                                <td>${userSkillList[status.index].evaluation}</td>
                                <td>
                                    <form id="modifyTForm"
                                          onsubmit="return reloadMat(${skill.skill.idSkill}, evalCmpt.value, ${skill.proiect.proiectId})">
                                        <select name="evaluation" id="evalCmpt">
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                        </select>

                                        <input type=hidden name="idskill" value="${skill.skill.idSkill}"/>
                                        <input type="hidden" value="${skill.proiect.proiectId}" name="proiectId"
                                               style="display: none">
                                        <input class="btn btn-secondary btn-sm" type='submit' value='Submit'/>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>--%>
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