<%@ include file="header.jspf"%>
<link rel="stylesheet" href="/resources/css/addRetexForm.css">
<security:csrfMetaTags/>
<br>
<button class="btn btn-outline-dark btn-lg" onclick="window.location.href = '/retex';">Back</button>
<h1 style="color:black;text-align:left;">Create</h1>
<hr>
<%--<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
   <script> tinymce.init({selector:'textarea'});</script>--%>
<%--
   <script src="//cdn.ckeditor.com/4.13.1/full-all/ckeditor.js"></script>

   <body>

   <textarea name="editor1"><button>Save</button></textarea>
                   <script>
                           CKEDITOR.replace( 'editor1' );
                   </script>
   --%>
<form:form action="saveNewRetex" modelAttribute="recordSolution" method="POST" accept-charset = "utf-8">
   <div class="separatecontainer">
      <div class = "row">
         <div class="col-25">
            <label class="label1">Title:</label>
         </div>
         <div class = "col-75">
            <form:input path="record.titlu" id="titlu"/>
         </div>
      </div>
      <div class = "row">
         <div class="col-25">
            <label class="label1">Category:</label>
         </div>
         <div class = "col-75">
            <form:input path="record.categorie" id="categorie" />
         </div>
      </div>
      <div class="row">
         <div class="col-25">
            <label class="label1">Description:</label>
         </div>
         <div class="col-75">
            <textarea id="summernote" maxlength="10000" name="record.descriere" style="height:200px"></textarea>
            <script>
               $('#summernote').summernote({
                 placeholder: 'Adauga descriere',
                 tabsize: 2,
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
            </script>
         </div>
      </div>
      <div class="row">
         <div class="col-25">
            <label class="label1">Solution:</label>
         </div>
         <div class="col-75">
            <textarea  id="summernoteSol" name="solution.solutie"></textarea>
            <script>
               $('#summernoteSol').summernote({
                 placeholder: 'Add solution',
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
            </script>
         </div>
      </div>
   </div>
   <hr>
   <input type="submit" value="Add" class="btn btn-outline-primary" />
   <button type="button" class="btn btn-outline-danger" onclick="closeAddRetex()">Close</button>
</form:form>
</body>
<%@ include file="footer.jspf"%>

<script>
 $('#summernote').summernote('code', "");
 $('#summernoteSol').summernote('code', "");

 document.getElementById("titlu").required = true;
 document.getElementById("categorie").required = true;

 function closeAddRetex() {
     window.location.href = '/retex';
 }

</script>
