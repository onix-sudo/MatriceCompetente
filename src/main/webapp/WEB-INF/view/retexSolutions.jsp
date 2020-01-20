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
</style>
<c:forEach var="solution" items="${solutionList}" varStatus="status">
   <div>
      <div class="message">
         <div class="user">${status.index+1}. ${solution.autor} -- ${solution.date}</div>
         <div style="clear:both;"></div>
         <div class="content">${solution.solutie}</div>
         <br>
         <div class="content"><button class="btn btn-info" onclick="editSolution()">Edit</button></div>
      </div>
   </div>
</c:forEach>

<script>
    function editSolution() {

    }

</script>
<%@ include file="footer.jspf"%>
