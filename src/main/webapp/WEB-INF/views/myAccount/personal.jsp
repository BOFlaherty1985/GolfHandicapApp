<%--
  Created by IntelliJ IDEA.
  User: BO034731
  Date: 08/07/2014
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
    <head>
        <title>Personal Information</title>

        <!-- JQuery -->
        <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap/bootstrap.min.js"></script>

        <!-- Bootstrap -->
        <link href="${pageContext.request.contextPath}/resources/style/bootstrap/bootstrap.css" rel="stylesheet">

        <!-- CSS -->
        <link href="${pageContext.request.contextPath}/resources/css/template.css" rel="stylesheet">

    </head>

    <body>

        <div class="container">

             <div class="row">
                 <!-- Navigation -->
                 <div class="navbar navbar-inverse">
                     <div class="navbar-header">
                         <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
                             <span class="icon-bar"></span>
                             <span class="icon-bar"></span>
                             <span class="icon-bar"></span>
                         </button>
                     </div>
                     <div class="navbar-collapse collapse navbar-inverse-collapse">
                         <ul class="nav navbar-nav">
                             <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                             <li><a href="${pageContext.request.contextPath}/myHandicap/history">My Handicap</a></li>
                             <li class="dropdown">
                                 <a href="#" class="dropdown-toggle" data-toggle="dropdown">Analysis <b class="caret"></b></a>
                                 <ul class="dropdown-menu">
                                     <li><a href="${pageContext.request.contextPath}/scoreAnalysis/overall">Overall Stats</a></li>
                                     <li><a href="${pageContext.request.contextPath}/scoreAnalysis/courseName">Course Stats</a></li>
                                 </ul>
                             </li>
                             <li class="dropdown active">
                                 <a href="#" class="dropdown-toggle" data-toggle="dropdown">My Account <b class="caret"></b></a>
                                 <ul class="dropdown-menu">
                                     <li class="active"><a href="${pageContext.request.contextPath}/myAccount/personalInformation">Personal Information</a></li>
                                     <li><a href="${pageContext.request.contextPath}/myAccount/changeAccountPassword">Change Password</a></li>
                                     <li><a href="${pageContext.request.contextPath}/myAccount/disableUserAccount">Delete Account</a></li>
                                 </ul>
                             </li>
                             <li><a href="${pageContext.request.contextPath}/static/j_spring_security_logout">Logout</a></li>
                         </ul>
                     </div>
                 </div>
             </div>

             <div class="row" style="background-color: #ecf0f1">
                 <div class="col-md-2"></div>

                 <div class="col-md-8">

                     <!-- Personal Details Form -->
                     <form:form method="post" action="${pageContext.request.contextPath}/myAccount/personalInformation/update"
                                commandName="personalInformationDto" class="form-horizontal">

                         <fieldset>
                             <legend>Personal Information</legend>

                             <div class="form-group">
                                <form:errors path="*" cssClass="text-danger"/>
                             </div>

                             <form:hidden path="id"/>

                             <div class="form-group">
                                 <label for="inputFirstName" class="col-lg-2 control-label">First Name</label>
                                 <div class="col-lg-10">
                                     <form:input id="inputFirstName" path="firstName" class="form-control" placeholder="First Name"/>
                                 </div>
                             </div>
                             <div class="form-group">
                                 <label for="inputLastName" class="col-lg-2 control-label">Last Name</label>
                                 <div class="col-lg-10">
                                     <form:input id="inputLastName" path="lastName" class="form-control" placeholder="Last Name"/>
                                 </div>
                             </div>
                             <div class="form-group">
                                 <label for="inputEmail" class="col-lg-2 control-label">Email</label>
                                 <div class="col-lg-10">
                                     <form:input id="inputEmail" path="email" class="form-control" placeholder="Email"/>
                                 </div>
                             </div>
                             <!-- Address Information -->
                             <div class="form-group">

                             </div>
                             <!-- Error Status Output -->
                             <c:if test="${not empty success}">
                                 <div class="alert alert-success" role="alert">
                                     <c:out value="${success}"/>
                                 </div>
                             </c:if>
                             <!-- Error Status Output -->
                             <c:if test="${not empty failure}">
                                 <div class="alert alert-danger" role="alert">
                                     <c:out value="${failure}"/>
                                 </div>
                             </c:if>
                             <div class="form-group">
                                 <div class="col-lg-10 col-lg-offset-2">
                                     <button type="submit" class="btn btn-primary">Submit</button>
                                 </div>
                             </div>
                         </fieldset>
                     </form:form>
                 </div>

                 <div class="col-md-2"></div>

             </div>

            <jsp:include page="../common/footer.jsp"/>

        </div>
    </body>
</html>
