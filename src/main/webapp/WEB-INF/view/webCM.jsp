<%@ include file="header.jspf"%>

<h2>Home Page</h2>
<hr>
<p>
    Welcome!
</p>
<hr>
<p>
    User:
    <security:authentication property="principal.username"/>
    <br><br>
    Role(s):
    <security:authentication property="principal.authorities"/>
    <br><br>

    <button type="button" class="btn btn-info" onclick="window.location.href='/webCM/personalProfile'">Personal Profile</button>


</p>

<hr>

<hr>

<div class="page">
    <!-- tabs -->
    <div class="pcss3t pcss3t-effect-scale pcss3t-theme-1">
        <input type="radio" name="pcss3t" checked  id="tab1"class="tab-content-first">
        <label for="tab1"><i class="icon-bolt"></i>Skill Matrix</label>

        <input type="radio" name="pcss3t" id="tab2" class="tab-content-2" >
        <label for="tab2"><i class="icon-picture"></i>Personal Skills</label>

        <input type="radio" name="pcss3t" id="tab3" class="tab-content-3">
        <label for="tab3"><i class="icon-cogs"></i>Manage Team</label>

        <input type="radio" name="pcss3t" id="tab5" class="tab-content-last">
        <label for="tab5"><i class="icon-globe"></i>Search People</label>

        <ul>
            <li class="tab-content tab-content-first typography">
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
                            <form method="GET" action="/webCM/cmptMat">
                                <td>
                                    <input type="submit" value="${proiect.numeProiect}" name="proiect">
                                    <input type="hidden" value="${proiect.proiectId}" name="proiectId" style="display: none" >
                                </td>
                            </form>
                            <td>${proiect.codProiect}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </li>

            <li class="tab-content tab-content-2 typography" >
                <%@ include file="personalProfile.jsp"%>

            </li>

            <li class="tab-content tab-content-3 typography">

            </li>

            <li class="tab-content tab-content-last typography">
                <div class="typography">

                </div>
            </li>
        </ul>
    </div>
    <!--/ tabs -->
</div>



<br>

<button type="button" class="btn btn-success" onclick="window.location.href='/'">HomePage</button>
<button type="button" class="btn btn-success" onclick="window.location.href='/webCM/leaders'">Manager</button>

<%@ include file="footer.jspf"%>