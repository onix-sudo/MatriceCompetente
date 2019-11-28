<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="container">
        <div class="split left">
            <div class="">
                <table class="table">
                    <thead>
                    <tr>
                        <th>Nr</th>
                        <th>ID Skill</th>
                        <th>Nume Skill</th>
                        <th>Categorie</th>
                        <th>Evaluare</th>
                        <th>Schimba evaluare</th>
                    </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="skill" items="${skillList}" varStatus="status">
                           <script>pushSkill("${skill.skill.numeSkill}", ${userSkillList[status.index].evaluation});</script>
                            <tr>
                                <td>${status.count}</td>
                                <td>${skill.skill.idSkill}</td>
                                <td>${skill.skill.numeSkill}</td>
                                <td>${skill.skill.categorie}</td>
                                <td>${userSkillList[status.index].evaluation}</td>
                                <td>
                                    <form id="modifyTForm">
                                        <select name="evaluation">
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

<script>
    $("#modifyTForm").submit(function(e){
        console.log(form.serialize());
        e.preventDefault();

        var form = $(this);
        var url = "/webCM/cmptMat/modifyT";

        $.ajax({
                   type: "GET",
                   url: url,
                   data: form.serialize(), // serializes the form's elements.
                   success: function(data)
                   {
                       $("#div1").html(data);
                       alert(data);
                   },
                   error: function(data)
                   {
                       console.log("ERROR");
                       $("#div1").load(data);
                   }
        });
    });

</script>





