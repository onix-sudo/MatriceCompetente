<%@ include file="header.jspf"%>
<body onload="return loadWebCM()">

<div class="ownBody">

    <div class="page">
        <!-- tabs -->
        <div class="pcss3t pcss3t-effect-scale pcss3t-theme-1">
            <input type="radio" name="pcss3t" checked  id="tab1"class="tab-content-first" onclick="skillMatrix()">
            <label for="tab1"><i class="icon-bolt"></i>Skill Matrix</label>

            <input type="radio" name="pcss3t" id="tab2" class="tab-content-2" onclick="showSkills()">
            <label for="tab2"><i class="icon-picture"></i>Personal Skills</label>

            <security:authorize access="hasRole('MANAGER')">
                <input type="radio" name="pcss3t" id="tab3" class="tab-content-3" onclick="leaders()">
                <label for="tab3"><i class="icon-cogs"></i>Manage Team</label>
            </security:authorize>

            <security:authorize access="hasRole('MANAGER')">
                <input type="radio" name="pcss3t" id="tab5" class="tab-content-last" onclick="searchPeople()">
                <label for="tab5"><i class="icon-globe"></i>Search People</label>
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

    <script>
        function loadWebCM() {
        $("#div1").load("/webCM/currentProj");
            <%--$("#tab1").click();--%>

            return false;
        }
    </script>

    <%@ include file="footer.jspf"%>