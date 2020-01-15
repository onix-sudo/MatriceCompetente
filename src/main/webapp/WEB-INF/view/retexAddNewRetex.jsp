<%@ include file="header.jspf"%>
<security:csrfMetaTags/>

<h2>forms
...
...
...
...
</h2>
<form:form action="saveNewRetex" method="POST">
    <input type="submit" class="btn btn-outline-primary">

    <button class="btn btn-outline-primary">Upvote</button>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>
<%@ include file="footer.jspf"%>

