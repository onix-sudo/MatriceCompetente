<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%-- This page renders the projects in which the user is added.
     The methods are taken from the controller class through the Spring's form tag library
     The JSTL core tag provides variable support and flow control
     --%>

        <c:choose>
            <c:when test="${empty proiectList}">
            <h4><p>Nu ai fost adaugat in niciun proiect.</p><h4>
            </c:when>
            <c:otherwise>
        <table class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th>Nr.</th>
                <th>Nume Proiect</th>
                <th>Cod</th>
                <th>Competente</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="proiect" items="${proiectList}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${proiect.numeProiect}</td>
                    <td>${proiect.codProiect}</td>
                    <td>
                        <form method="GET" action="/webCM/cmptMat" onsubmit="return cmptMat(${proiect.proiectId})">
                            <input class="btn btn-outline-primary" type="submit" value="Deschide" name="proiect">
                            <input type="hidden" value="${proiect.proiectId}" name="proiectId" style="display: none" >
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </c:otherwise>
        </c:choose>

<%-- function for loading the first tab which is selected by default --%>

        <script type="text/javascript">
            function cmptMat(proiectId) {
                $("#div1").load("/webCM/cmptMat?proiectId=" + proiectId);
                return false;
            }
        </script>

