<%@ include file="header.jspf"%>
<style>
   td {
   max-width: 100px;
   overflow: hidden;
   text-overflow: ellipsis;
   white-space: nowrap;
   }
</style>

<br>

<div>

<button class="btn btn-dark" onclick="window.location.href = '/';">Inapoi la Home</button>

<button type="button" class="btn btn-outline-primary" onclick="window.location
.href='/retex/addNewRetex'">Add new RETEX</button>
</div>

<hr>
<div class="s003">
   <form method="GET" action="/retex/search">
      <div class="inner-form">
         <div class="input-field first-wrap">
            <select data-trigger="" name="category" id="searchCategory">
               <option selected="selected">Toate</option>
               <option>Titlu</option>
               <option>Categorie</option>
               <option>Descriere</option>
            </select>
         </div>
         <div class="input-field second-wrap">
            <input name="terms" id="search" type="text" placeholder="Cauta solutie" required/>
         </div>
         <div class="input-field third-wrap">
            <button class="btn-search" type="submit">
               <svg class="svg-inline--fa fa-search fa-w-16" aria-hidden="true" data-prefix="fas" data-icon="search" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                  <path fill="currentColor" d="M505 442.7L405.3 343c-4.5-4.5-10.6-7-17-7H372c27.6-35.3 44-79.7 44-128C416 93.1 322.9 0 208 0S0 93.1 0 208s93.1 208 208 208c48.3 0 92.7-16.4 128-44v16.3c0 6.4 2.5 12.5 7 17l99.7 99.7c9.4 9.4 24.6 9.4 33.9 0l28.3-28.3c9.4-9.4 9.4-24.6.1-34zM208 336c-70.7 0-128-57.2-128-128 0-70.7 57.2-128 128-128 70.7 0 128 57.2 128 128 0 70.7-57.2 128-128 128z"></path>
               </svg>
            </button>
         </div>
      </div>
   </form>
</div>
<br>
<hr>
<table id="retexIndexTable" class="table" cellspacing="0">
   <thead class="thead-dark">
      <tr>
         <th scope="col">Categorie</th>
         <th>Titlu</th>
         <th>Descriere</th>
         <th>Data</th>
      </tr>
   </thead>
   <tbody>
      <c:forEach var="solution" items="${solutionList}">
         <a href="/retex/solution">
            <tr style="cursor: pointer;" class="clickable-row" data-href="retex/solution?recordId=${solution
            .record.id}">
               <td>${solution.record.categorie}</td>
               <td>${solution.record.titlu}</td>
               <td>${solution.record.descriere}</td>
               <td>${solution.date}</td>
            </tr>
         </a>
      </c:forEach>
   </tbody>
</table>

<script src="resources/js/extention/choices.js"></script>
<script>
   var choices = new Choices('[data-trigger]', {
       searchEnabled: false,
       itemSelectText: '',
   });

   jQuery(document).ready(function($) {
       $(".clickable-row").click(function() {
           window.location = $(this).data("href");
       });
   });

    $(document).ready(function () {
            $('#retexIndexTable tbody tr').click(function () {
            }).hover(function () {
                  $(this).css('background-color','#9999ff');
               }, function () {
                  $(this).css('background-color','#fff');
            });
    });

</script>
<%@ include file="footer.jspf"%>
