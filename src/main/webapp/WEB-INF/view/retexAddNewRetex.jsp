<%@ include file="header.jspf"%>
<link rel="stylesheet" href="/resources/css/addRetexForm.css">
<security:csrfMetaTags/>


<h1 style="color:black;text-align:left;"> Adauga o noua inregistrare</h1>
<hr>

<body>
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
                <textarea id="my-input" maxlength="10000" name="record.descriere" placeholder="Write something.." style="height:200px"></textarea><span id='remainingC'></span>
              </div>
        </div>
        <div class="row">
              <div class="col-25">
                <label class="label1">Solutie:</label>
              </div>
              <div class="col-75">
                <textarea  id="my-input1" maxlength="20000" name="solution.solutie" placeholder="Write something.." style="height:200px"></textarea><span id='remainingD'></span>
              </div>
        </div>
</div>
<hr>
       <input type="submit" value="Adauga" class="btn btn-outline-primary"/>
</form:form>

</body>


<%@ include file="footer.jspf"%>

<script>

$(document).ready(function() {
  var len = 0;
  var maxchar = 10000;

  $( '#my-input' ).keyup(function(){
    len = this.value.length
    if(len > maxchar){
        return false;
    }
    else {
        $( "#remainingC" ).html(( maxchar - len ) +"/10000 " );
    }
  })
});

$(document).ready(function() {
  var len = 0;
  var maxchar = 20000;

  $( '#my-input1' ).keyup(function(){
    len = this.value.length
    if(len > maxchar){
        return false;
    }
    else {
        $( "#remainingD" ).html( ( maxchar - len ) +"/20000 " );
    }
  })
});

</script>