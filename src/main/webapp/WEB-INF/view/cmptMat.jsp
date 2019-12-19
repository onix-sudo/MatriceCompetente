<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- This page renders the personal skill chart. The chart is created with the Plotly JavaScript library
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field
     The JSTL core tag provides variable support and flow control
     --%>

<div class="container">
        <div class="split left">
            <div class="">
                <table class="table table-striped">
                    <thead class="thead-dark">
                    <tr>
                        <th>Nume competenta</th>
                        <th>Categorie</th>
                        <th>Evaluare</th>
                        <th>Schimba evaluare</th>
                    </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="skill" items="${userSkillList}" varStatus="status">
                           <script>pushSkill("${skill.skill.numeSkill}", ${userSkillList[status.index].evaluation});</script>
                            <tr>
                                <td>${skill.skill.numeSkill}</td>
                                <td>${skill.skill.categorie}</td>
                                <td>${userSkillList[status.index].evaluation}</td>
                                <td>
                                    <form id="modifyTForm"
                                          onsubmit="return reloadMat(${skill.skill.idSkill}, evalCmpt.value, ${projectId})">
                                        <select name="evaluation" id="evalCmpt">
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                        </select>

                                        <input type=hidden name="idskill" value="${skill.skill.idSkill}"/>
                                        <input type="hidden" value="${projectId}" name="proiectId"
                                               style="display: none">
                                        <input class="btn btn-outline-primary btn-sm" type='submit' value='Schimba'/>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>



    <div class="split right">
        <div class="centered">
            <div id="chartDiv" style="max-width: 450px;height: 500px;margin: 0px auto">

                <script>
                    plotRadar();
                </script>
            </div>
        </div>
    </div>

</div>

<%-- The JavaScript function below renders the Radar Chart and takes the personal skills as parameters --%>

<script>
function reloadMat(idSkill, evaluation, idProiect) {

    console.log("HERE");
     //var form = $(this);
        var csrfParameter = '${_csrf.parameterName}';
        var csrfToken = '${_csrf.token}';
        var url = "/webCM/cmptMat/modifyT";
        //console.log(form.serialize());

        $.get({
                   url: url,
                   data: {idskill: idSkill, evaluation: evaluation, proiectId: idProiect},
                   success: function(data)
                   {
                        jsVarList = [];
                        dataList = [];
                        $("#div1").html(data);

                   },
                   error: function(data)
                   {
                        console.log("ERROR");
                        $("#div1").html(data);
                   }
         })

    return false;
}
</script>





