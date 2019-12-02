<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<br>
  <button type="button" class="btn btn-warning"
  onclick="return back()">Inapoi</button>
<hr>

<form:form onsubmit= "return sendData()" modelAttribute = "newProject" method="POST" accept-charset = "utf-8">
    <table>
        <thead>
        <tr>
            <td><label>Nume proiect:</label></td>
            <td><form:input path="numeProiect"/></td>
            <td><form:errors path="numeProiect" cssClass="error"/></td>
        </tr>

        <tr>
            <td><label>Cod proiect:</label></td>
            <td><form:input path="codProiect"/></td>
            <td><form:errors path="codProiect" cssClass="error"/></td>
        </tr>

        <tr>
            <td><label></label></td>
            <td><input type="submit" value="Save" class="save"/></td>
        </tr>
        </thead>
    </table>

</form:form>

<script>
    function back() {
        $("#div3").load("/webCM/leaders/");

        return false;
    }

        var model = $("#newProject").serialize();

        function sendData() {
            $.ajax({
                type: "POST",
                data: model,
                url: "/webCM/leaders/addProject",
                success: function(data){
                    $("#tab3").click();
                },
                error: function(xhr, status) {
                    $("#tab2").click();
                }
            });
            return false;
        }

</script>