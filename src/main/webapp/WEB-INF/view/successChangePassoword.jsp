<%@ include file = "header.jspf" %>

    <font size="5"> Parola a fost schimbata. Vei fi redirectionat in 5 secunde. </font>

<script>
  setTimeout(function() {
      window.location.href = "/";
  }, 4000); // <-- this is the delay in milliseconds
</script>

<%@ include file = "footer.jspf" %>