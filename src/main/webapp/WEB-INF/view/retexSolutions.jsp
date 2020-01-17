<%@ include file="header.jspf"%>

<br>

        <table class="table table-hover">
            <tbody class="thead-dark">
                <tr>
                    <th>Titlu</th>
                    <td>${record.titlu}</td>
                </tr>
                <tr>
                    <th>Descriere</th>
                    <td>${record.descriere}</td>
                </tr>
            </tbody>

        </table>

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
                  <a href==<div class="content">Edit</div>
              </div>

          </div>
          </c:forEach>


<%@ include file="footer.jspf"%>