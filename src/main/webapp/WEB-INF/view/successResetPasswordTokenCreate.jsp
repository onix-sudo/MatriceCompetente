<%@ include file = "header.jspf" %>

    <font size="5"> Un email pentru schimbarea parolei a fost trimis la adresa: ${email} </font>
    <br><br>
    <font size="4"> Link-ul este valabil <b>30 de minute.</b> </font>

<script>
  setTimeout(function() {
      window.location.href = "/";
  }, 3000); // <-- this is the delay in milliseconds
</script>

<%@ include file = "footer.jspf" %>