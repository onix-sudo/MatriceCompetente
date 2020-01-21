<%@ include file="header.jspf"%>
<br>
<h2 class="display-2">${record.titlu}</h2>
<div class="font-weight-normal text-break">${record.descriere}</div>
<hr>
<style>
   .message {margin-bottom: 15px; }
   .image {float:left; margin-right: 10px; }
   .user {float:left; font-weight:bold; color:#009; margin-bottom: 5px; }
   .content { margin-left: 30px;font-style:italic; color:#; }
   .overlay {
   position: fixed;
   top: 0;
   bottom: 0;
   left: 0;
   right: 0;
   background: rgba(0, 0, 0, 0.7);
   transition: opacity 500ms;
   visibility: hidden;
   opacity: 0;
   }
   .overlay:target {
   visibility: visible;
   opacity: 1;
   }
   .popup {
   margin: 70px auto;
   padding: 20px;
   background: #fff;
   border-radius: 5px;
   width: 30%;
   position: relative;
   transition: all 5s ease-in-out;
   }
   .popup h2 {
   margin-top: 0;
   color: #333;
   font-family: Tahoma, Arial, sans-serif;
   }
   .popup .close {
   position: absolute;
   top: 20px;
   right: 30px;
   transition: all 200ms;
   font-size: 30px;
   font-weight: bold;
   text-decoration: none;
   color: #333;
   }
   .popup .close:hover {
   color: #06D85F;
   }
   .popup .content {
   max-height: 30%;
   overflow: auto;
   }
   @media screen and (max-width: 700px){
   .box{
   width: 70%;

   }
   .popup{
   width: 70%;
   }
   }
</style>
<c:forEach var="solution" items="${solutionList}" varStatus="status">
   <div>
      <div class="message">
         <div class="user">${status.index+1}. ${solution.getUserExpleo().getFullName()} -- ${solution.date}</div>
         <div style="clear:both;"></div>
         <div class="content">${solution.solutie}</div>
         <br>
         <c:if test="${mainUser.id} eq ${solution.getUserExpleo().getId()}">
            <div class="content"><a class="btn btn-info" onclick="editSolution(${solution.id}, '${solution.solutie}')"
            href="#popup1">Editeaza</a></div>
         </c:if>
      </div>
   </div>
</c:forEach>
<div id="popup1" class="overlay">
   <div class="popup">
      <h2>Editeaza solutie</h2>
      <a class="close" href="#">&times;</a>
      <div class="content">
         <form:form action="editRetex" modelAttribute="recordSolution" method="POST" accept-charset = "utf-8">
            Solutie:
            <div class="col-75">
               <textarea  id="edit-input" maxlength="20000" name="textSolutie" placeholder="Write something.."
               style="height:200px"></textarea><span id='remainingD'></span>
            </div>
            <hr>
            <input type="submit" value="Salveaza" class="btn btn-outline-primary"/>
            <input id="input-solutie" type="hidden" name="idSolutie"/>
            <input name="recordId" type="hidden" value="${record.id}"/>
         </form:form>
      </div>
   </div>
</div>
<script>
   function editSolution(idSolutie, solutie) {
        $('#edit-input').val(solutie);
        $('#input-solutie').val(idSolutie);
   }
</script>

<script>

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

</script>
<%@ include file="footer.jspf"%>
