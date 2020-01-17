<%@ include file="header.jspf"%>
<link rel="stylesheet" href="/resources/css/addRetexForm.css">
<security:csrfMetaTags/>


<h3>Adauga o noua inregistrare</h3>
<hr>

<form:form action="saveNewRetex" modelAttribute="recordSolution" method="POST" accept-charset = "utf-8">
<div class="separatecontainer">

        <div class = "row">
            <div class="col-25">
                <label class="label1">Titlu:</label>
            </div>

            <div class = "col-75">
                <form:input path="record.titlu"/>
            </div>
        </div>
        <div class = "row">
            <div class="col-25">
                <label class="label1">Categorie:</label>
            </div>

            <div class = "col-75">
                <form:input path="record.categorie"/>
            </div>
        </div>
        <div class="row">
              <div class="col-25">
                <label class="label1">Descriere:</label>
              </div>
              <div class="col-75">
                <textarea id="descriere" name="record.descriere" placeholder="Write something.." style="height:200px"></textarea>
              </div>
        </div>
        <div class="row">
              <div class="col-25">
                <label class="label1">Solutie:</label>
              </div>
              <div class="col-75">
                <textarea id="descriere" name="solution.solutie" placeholder="Write something.." style="height:200px"></textarea>
              </div>
        </div>
</div>
<hr>
       <input type="submit" value="Adauga" class="btn btn-outline-primary"/>
</form:form>

<%--<form:form action="saveNewRetex" method="POST">
    <input type="submit" class="btn btn-outline-primary">

    <button class="btn btn-outline-primary">Upvote</button>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>--%>
<%@ include file="footer.jspf"%>

