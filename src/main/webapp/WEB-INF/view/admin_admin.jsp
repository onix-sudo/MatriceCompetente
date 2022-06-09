<%@ include file="admin_header.jspf" %>
<%-- This page renders the application management for admin
     It comprises buttons with links to the paths defined below within onclick field --%>
<br>
<h2>Database Management</h2>
<hr>
 <button type="button" class="btn btn-outline-primary" onclick="window.location.href='/admin/addUser'">Add Employee</button>
 <button type="button" class="btn btn-outline-primary" onclick="window.location.href='/admin/addSkill'">Add Skill</button>
 <button type="button" class="btn btn-outline-primary" onclick="window.location.href='/admin/updateUser'">Edit Employee</button>
<%-- <button type="button" class="btn btn-outline-danger" onclick="window.location.href='/manager'">TomcatManager</button>--%>

<hr>

<%@ include file="footer.jspf" %>