<%@ include file="header.jspf"%>
<br>
<button class="btn btn-outline-dark btn-lg" onclick="window.location.href = '/retex';" >Inapoi</button>
<button id="btnAddSol" class="btn btn-outline-primary btn-lg" style="float: right;">Adauga solutie</button>
<h2 class="display-2" align="center">${record.titlu}</h2>
<div class="font-weight-normal text-break" align="center">${record.descriere}</div>
<hr>
<style>
   .modal {
   display: none; /* Hidden by default */
   position: fixed; /* Stay in place */
   z-index: 1; /* Sit on top */
   padding-top: 100px; /* Location of the box */
   left: 0;
   top: 0;
   width: 100%; /* Full width */
   height: 100%; /* Full height */
   overflow: auto; /* Enable scroll if needed */
   background-color: rgb(0,0,0); /* Fallback color */
   background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
   }
   /* Modal Content */
   .modal-content {
   background-color: #fefefe;
   margin: auto;
   padding: 20px;
   border: 1px solid #888;
   width: 80%;
   }
   /* The Close Button */
   .close {
   color: #aaaaaa;
   float: right;
   font-size: 28px;
   font-weight: bold;
   }
   .close:hover,
   .close:focus {
   color: #000;
   text-decoration: none;
   cursor: pointer;
   }
</style>

<br><br>

<c:forEach var="solution" items="${solutionList}" varStatus="status">
   <div>
      <div class="message">
         <div class="user">
            ${status.index+1}. ${solution.getUserExpleo().getFullName()} -- Creat la: ${solution.date}
            <c:if test = "${not empty solution.date_update}"> -- Ultima Modificare: ${solution.date_update}
         </div>
         </c:if>
         <div style="clear:both;"></div>
         <div class="content">${solution.solutie}</div>
         <br>
         <c:if test="${mainUser.id==solution.userExpleo.id}">
            <div class="content">
                <button id="btnModSol" class="btn btn-outline-success"
                    onclick="editSolution(${solution.id}, '${solution.solutie}')" name="btnModName">Modifica</button>
            </div>
         </c:if>
      </div>
   </div>
   </div>
   <br><br>
</c:forEach>

<div id="popup1" class="modal">
   <div class="modal-content">
      <span id="spanModal1" class="close">&times;</span>
      <br>
      <h2>Modifica solutie</h2>
      <form:form action="editRetex" modelAttribute="recordSolution" method="POST" accept-charset = "utf-8">
         Solutie:
         <div class="col-75">
            <textarea  id="modificaSolutie" maxlength="20000" name="textSolutie"></textarea>
         </div>
         <hr>
         <input type="submit" value="Salveaza" class="btn btn-outline-primary"/>
         <input id="idSolutie1" type="hidden" name="idSolutie"/>
         <input name="recordId" type="hidden" value="${record.id}"/>
      </form:form>
   </div>
</div>

<div id="popup2" class="modal">
   <div class="modal-content">
      <span id="spanModal2" class="close">&times;</span>
      <br>
      <h2>Adauga solutie</h2>
      <form:form action="addSolution" modelAttribute="recordSolution" method="POST" accept-charset = "utf-8">
         Solutie:
         <div class="col-75">
            <textarea  id="adaugaSolutie" maxlength="20000" name="textSolutie" placeholder="Write something.."
               style="height:200px; width:500px"></textarea><span id='remainingD'></span>
         </div>
         <hr>
         <input type="submit" value="Salveaza" class="btn btn-outline-primary"/>
         <input id="idSolutie2" type="hidden" name="idSolutie"/>
         <input name="recordId" type="hidden" value="${record.id}"/>
      </form:form>
   </div>
</div>












<script>
   function editSolution(idSolutie, solutie) {
        $('#idSolutie1').val(idSolutie);
        $('#modificaSolutie').val(solutie);

        document.getElementById("popup1").style.display = "block";
   }

    $('#modificaSolutie').summernote({
        tabsize: 1,
        height: 200,
        toolbar: [
        ['style', ['style']],
        ['font', ['bold', 'underline', 'clear']],
        ['color', ['color']],
        ['para', ['ul', 'ol', 'paragraph']],
        ['table', ['table']],
        ['insert', ['link', 'picture', 'video']],
        ['view', ['fullscreen', 'codeview', 'help']]
        ]
    });

    $('#adaugaSolutie').summernote({
            tabsize: 1,
            height: 500,
            toolbar: [
            ['style', ['style']],
            ['font', ['bold', 'underline', 'clear']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['table', ['table']],
            ['insert', ['link', 'picture', 'video']],
            ['view', ['fullscreen', 'codeview', 'help']]
            ]
    });

    // Get the modal
    var modal1 = document.getElementById("popup1")
    var modal2 = document.getElementById("popup2");

    // Get the button that opens the modal
    var btnAddSol = document.getElementById("btnAddSol");

    // Get the <span> element that closes the modal
    var span1 = document.getElementById("spanModal1");
    var span2 = document.getElementById("spanModal2");

    // When the user clicks the button, open the modal
    btnAddSol.onclick = function() {
      modal2.style.display = "block";
    }

    // When the user clicks on <span> (x), close the modal
    span1.onclick = function() {
        modal1.style.display = "none";
    }

    span2.onclick = function() {
        modal2.style.display = "none";
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
      if (event.target == modal1) {
        modal1.style.display = "none";
      } else if (event.target == modal2) {
        modal2.style.display = "none";
      }
    }


</script>

<%--<script>
   $(document).ready(function() {
     var len = 0;
     var maxchar = 20000;

     $( '#edit-input' ).keyup(function(){
       len = this.value.length
       if(len > maxchar){
           return false;
       }
       else {
           $( "#remainingD" ).html( ( maxchar - len ) +"/20000 " );
       }
     })
   });

   </script>--%>
<%@ include file="footer.jspf"%>
