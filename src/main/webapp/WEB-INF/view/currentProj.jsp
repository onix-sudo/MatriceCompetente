<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


        <table class="table">
            <thead>
            <tr>
                <th>Nr</th>
                <th>ID Proiect</th>
                <th>Nume Proiect</th>
                <th>Cod</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="proiect" items="${proiectList}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${proiect.proiectId}</td>

                        <td>
                            <form method="GET" action="/webCM/cmptMat" onsubmit="return cmptMat(${proiect.proiectId})">
                                <input type="submit" value="${proiect.numeProiect}" name="proiect">
                                <input type="hidden" value="${proiect.proiectId}" name="proiectId" style="display: none" >
                            </form>
                        </td>

                    <td>${proiect.codProiect}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <script type="text/javascript">
            function cmptMat(proiectId) {
                $("#div1").load("/webCM/cmptMat?proiectId=" + proiectId);

                return false;
            }
        </script>

