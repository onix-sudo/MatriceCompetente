<%@ include file="header.jspf"%>
<body onload="return loadWebCM()">

<div class="ownBody">

<%--webCM reprezinta pagina principala si este impartita in 4 taburi
    Fiecare tab este tip radioButton, iar atunci cand este apasat se va apela o functie ce va incarca
    pentru tab-ul respectiv jsp-ul returnat din controller--%>

<div class="page">
        <!-- tabs -->
        <div class="pcss3t pcss3t-effect-scale pcss3t-theme-1">
            <input type="radio" name="pcss3t" checked  id="tab1"class="tab-content-first" onclick="skillMatrix()">
            <label for="tab1"><i class="icon-bolt"></i>Matrice competente</label>

            <input type="radio" name="pcss3t" id="tab2" class="tab-content-2" onclick="showSkills()">
            <label for="tab2"><i class="icon-picture"></i>Profil personal</label>

            <security:authorize access="hasRole('MANAGER')">
                <input type="radio" name="pcss3t" id="tab3" class="tab-content-3" onclick="leaders()">
                <label for="tab3"><i class="icon-cogs"></i>Gestionare proiecte</label>
            </security:authorize>

            <security:authorize access="hasRole('MANAGER')">
                <input type="radio" name="pcss3t" id="tab5" class="tab-content-last" onclick="searchPeople()">
                <label for="tab5"><i class="icon-globe"></i>Cauta colaboratori</label>
            </security:authorize>

            <ul>
                <li class="tab-content tab-content-first typography" id="div1">
                    <script>
                        function skillMatrix() {
                            $("#div1").load("/webCM/currentProj");
                        }
                    </script>
                </li>

                <li class="tab-content tab-content-2 typography" id="div2">
                    <script>
                        function showSkills() {
                            $("#div2").load("/webCM/personalProfile");
                        }
                    </script>
                </li>

                <security:authorize access="hasRole('MANAGER')">
                    <li class="tab-content tab-content-3 typography" id="div3">
                        <script>
                            function leaders() {
                                $("#div3").load("/webCM/leaders");
                            }
                        </script>
                    </li>
                </security:authorize>

                <li class="tab-content tab-content-last typography" id="div4">
                    <script>
                            function searchPeople() {
                                $("#div4").load("/webCM/leaders/searchPeople");
                            }
                        </script>
                </li>
            </ul>
        </div>
</div>

<%--functie pentru incarcarea primului tab care este implicit selectat--%>
    <script>
        function loadWebCM() {
        $("#div1").load("/webCM/currentProj");

            return false;
        }
    </script>

    <%@ include file="footer.jspf"%>