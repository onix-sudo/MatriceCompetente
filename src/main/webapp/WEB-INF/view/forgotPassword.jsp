<%@ include file = "header.jspf" %>

<%-- This page renders the forgot-password form
     The methods are taken from the controller class through the Spring's form tag library
     The objects are defined within the modelAttribute field
     The JSTL core tag provides variable support and flow control
     --%>

<br>
<h3> Please enter a valid email address :</h3>
<hr>
    <c:if test="${not empty email}">
    <h2> Email ${email} not found in database. </h2>
    </c:if>
                <form:form action="${pageContext.request.contextPath}/forgotPassword/reset"
                           method="POST" class="form-group">


                                <c:if test="${param.error != null}">
                                    <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                        Invalid username and password.
                                    </div>
                                </c:if>

                                <c:if test="${param.logout != null}">

                                    <div class="alert alert-success col-xs-offset-1 col-xs-10">
                                        You have been successfully logged out.
                                    </div>

                                </c:if>
                            </div>
                        </div>
                    </div>

                    <div style="margin-left: 25px; width: 25%; padding: 5px;" class="input-group">

                        <input type="text" name="email" placeholder="Email" class="form-control">

                    </div>


                    <div style="margin-top: 10px" class="form-group">
                        <div class="col-sm-6 controls">
                            <button type="submit" class="btn btn-outline-success">Reset password</button>
                        </div>
                    </div>
                </form:form>



<%@ include file = "footer.jspf" %>