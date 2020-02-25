<%@ include file="header.jspf"%>
<style>
   td {
   max-width: 100px;
   overflow: hidden;
   text-overflow: ellipsis;
   white-space: nowrap;
   }

   .chatbox {
       position: fixed;
       bottom: 0px;
       right: 30px;
       width: 300px;
       height: 400px;
       background-color: #fff;
       font-family: 'Lato', sans-serif;

       -webkit-transition: all 600ms cubic-bezier(0.19, 1, 0.22, 1);
       transition: all 600ms cubic-bezier(0.19, 1, 0.22, 1);

       display: -webkit-flex;
       display: flex;

       -webkit-flex-direction: column;
       flex-direction: column;
   }

   .chatbox--tray {
       bottom: -350px;
   }

   .chatbox--closed {
       bottom: -400px;
   }

   .chatbox .form-control:focus {
       border-color: #1f2836;
   }

   .chatbox__title,
   .chatbox__body {
       border-bottom: none;
   }

   .chatbox__title {
       min-height: 50px;
       padding-right: 10px;
       background-color: #1f2836;
       border-top-left-radius: 4px;
       border-top-right-radius: 4px;
       cursor: pointer;
       margin-top:-39.5px;
       display: -webkit-flex;
       display: flex;

       -webkit-align-items: center;
       align-items: center;
   }

   .chatbox__title h5 {
       height: 50px;
       margin: 0 0 0 15px;
       line-height: 50px;
       position: relative;
       padding-left: 20px;

       -webkit-flex-grow: 1;
       flex-grow: 1;
   }

   .chatbox__title h5 a {
       color: #fff;
       max-width: 195px;
       display: inline-block;
       text-decoration: none;
       white-space: nowrap;
       overflow: hidden;
       text-overflow: ellipsis;
   }

   .chatbox__title h5:before {
       content: '';
       display: block;
       position: absolute;
       top: 50%;
       left: 0;
       width: 12px;
       height: 12px;
       background: #4CAF50;
       border-radius: 6px;

       -webkit-transform: translateY(-50%);
       transform: translateY(-50%);
   }

   .chatbox__title__tray,
   .chatbox__title__close {
       width: 24px;
       height: 24px;
       outline: 0;
       border: none;
       background-color: transparent;
       opacity: 0.5;
       cursor: pointer;

       -webkit-transition: opacity 200ms;
       transition: opacity 200ms;
   }

   .chatbox__title__tray:hover,
   .chatbox__title__close:hover {
       opacity: 1;
   }

   .chatbox__title__tray span {
       width: 12px;
       height: 12px;
       display: inline-block;
       border-bottom: 2px solid #fff
   }

   .chatbox__title__close svg {
       vertical-align: middle;
       stroke-linecap: round;
       stroke-linejoin: round;
       stroke-width: 1.2px;
   }

   .chatbox__body,
   .chatbox__credentials {
       padding: 15px;
       border-top: 0;
       background-color: #f5f5f5;
       border-left: 1px solid #ddd;
       border-right: 1px solid #ddd;

       -webkit-flex-grow: 1;
       flex-grow: 1;
   }

   .chatbox__credentials {
       display: none;
   }

   .chatbox__credentials .form-control {
       -webkit-box-shadow: none;
       box-shadow: none;
   }

   .chatbox__body {
       overflow-y: auto;
   }

   .chatbox__body__message {
       position: relative;
   }

   .chatbox__body__message p {
       padding: 15px;
       border-radius: 4px;
       font-size: 14px;
       background-color: #fff;
       -webkit-box-shadow: 1px 1px rgba(100, 100, 100, 0.1);
       box-shadow: 1px 1px rgba(100, 100, 100, 0.1);
   }

   .chatbox__body__message img {
       width: 40px;
       height: 40px;
       border-radius: 4px;
       border: 2px solid #fcfcfc;
       position: absolute;
       top: 15px;
   }

   .chatbox__body__message--left p {
       margin-left: 15px;
       padding-left: 30px;
       text-align: left;
   }

   .chatbox__body__message--left img {
       left: -5px;
   }

   .chatbox__body__message--right p {
       margin-right: 15px;
       padding-right: 30px;
       text-align: right;
   }

   .chatbox__body__message--right img {
       right: -5px;
   }

   .chatbox__message {
       padding: 15px;
       min-height: 50px;
       outline: 0;
       resize: none;
       border: none;
       font-size: 12px;
       border: 1px solid #ddd;
       border-bottom: none;
       background-color: #fefefe;
   }

   .chatbox--empty {
       height: 262px;
   }

   .chatbox--empty.chatbox--tray {
       bottom: -212px;
   }

   .chatbox--empty.chatbox--closed {
       bottom: -262px;
   }

   .chatbox--empty .chatbox__body,
   .chatbox--empty .chatbox__message {
       display: none;
   }

   .chatbox--empty .chatbox__credentials {
       display: block;
   }

</style>

<br>

<div>

<button class="btn btn-outline-dark btn-lg" onclick="window.location.href = '/';">Inapoi</button>

<button type="button" class="btn btn-outline-primary btn-lg" style="float: right;" onclick="window.location.href='/retex/addNewRetex'">Adauga inregistrare</button>
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

<div class="chatbox chatbox--tray chatbox--empty">
    <div class="chatbox__title">
        <h5><a href="#">Expleo ChatBot</a></h5>
        <button class="chatbox__title__tray">
            <span></span>
        </button>
        <button class="chatbox__title__close">
            <span>
                <svg viewBox="0 0 12 12" width="12px" height="12px">
                    <line stroke="#FFFFFF" x1="11.75" y1="0.25" x2="0.25" y2="11.75"></line>
                    <line stroke="#FFFFFF" x1="11.75" y1="11.75" x2="0.25" y2="0.25"></line>
                </svg>
            </span>
        </button>
    </div>
    <div class="chatbox__body">
        <div class="chatbox__body__message chatbox__body__message--left">
            <img src="https://s3.amazonaws.com/uifaces/faces/twitter/brad_frost/128.jpg" alt="Picture">
            <p>Care este problema dumneavoastra, domnule?</p>
        </div>
        <div class="chatbox__body__message chatbox__body__message--right">
            <img src="https://s3.amazonaws.com/uifaces/faces/twitter/arashmil/128.jpg" alt="Picture">
            <p>Nu stiu cum sa te implementez.</p>
        </div>
        <div class="chatbox__body__message chatbox__body__message--left">
            <img src="https://s3.amazonaws.com/uifaces/faces/twitter/brad_frost/128.jpg" alt="Picture">
            <p>"not found" error 404.</p>
        </div>
    </div>
    <form class="chatbox__credentials">
        <div class="form-group">
            <label for="inputName">Name:</label>
            <input type="text" class="form-control" id="inputName" required>
        </div>
        <div class="form-group">
            <label for="inputEmail">Email:</label>
            <input type="email" class="form-control" id="inputEmail" required>
        </div>
        <button type="submit" class="btn btn-success btn-block">Enter Chat</button>
    </form>
    <textarea class="chatbox__message" placeholder="Write something interesting"></textarea>
</div>

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

(function($) {
    $(document).ready(function() {
        var $chatbox = $('.chatbox'),
            $chatboxTitle = $('.chatbox__title'),
            $chatboxTitleClose = $('.chatbox__title__close'),
            $chatboxCredentials = $('.chatbox__credentials');
        $chatboxTitle.on('click', function(e) {
            $chatbox.toggleClass('chatbox--tray');
            e.preventDefault();
            $chatbox.removeClass('chatbox--empty');
        });
        $chatboxTitleClose.on('click', function(e) {
            e.stopPropagation();
            $chatbox.addClass('chatbox--closed');
        });
        $chatbox.on('transitionend', function() {
            if ($chatbox.hasClass('chatbox--closed')) $chatbox.remove();
        });
    });
})(jQuery);

</script>
<%@ include file="footer.jspf"%>
