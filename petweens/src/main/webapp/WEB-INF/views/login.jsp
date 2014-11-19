<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="/petweens/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/petweens/css/main.css">

	<script src="/petweens/js/jquery-1.11.1.min.js"></script>
	<script src="/petweens/js/bootstrap.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div id="main_logo">
		<img src="/petweens/image/logo5.png" align="middle">
	</div>
	<div  class="wrap">
		<c:if test="${error==true}">
			 <div class="alert alert-danger" role="alert">
				  
				  <span class="sr-only">Error:</span>
	  					Incorrect username or password.
			</div>
		</c:if>
		<form class="form-horizontal" role="form" method="post" action="/petweens/user/login">
			<div class="login_input">
				<label class="login_label">Email</label>
				<input type="email" value="${user.email}" name="email" class="form-control" id="inputEmail3" placeholder="Email">
			</div>
			<div class="login_input">
				<label class="login_label">Password</label>
				<input type="password" class="form-control" name="password" id="inputPassword3" placeholder="Password">
			</div>
			<div class="login_input">
				<!--TO DO : button Tag / type change-->
				<a type="button" class="login_button btn btn-default" href="/petweens/user/signup">Sign up</a>
				<input  type="submit" class="login_button btn btn-primary" value="Login">
			</div>
		</form>
	</div>
</body>
</html>