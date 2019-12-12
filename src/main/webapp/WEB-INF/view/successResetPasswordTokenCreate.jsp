<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

    <font size="5"> Un email pentru schimbarea parolei a fost trimis la adresa: ${email} </font>
    <br><br>
    <font size="4"> Link-ul este valabil <b>30 de minute.</b> </font>

<script>
  setTimeout(function() {
      window.location.href = "/";
  }, 3000); // <-- this is the delay in milliseconds
</script>

