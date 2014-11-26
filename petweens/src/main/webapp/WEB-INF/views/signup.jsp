<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="/petweens/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/petweens/css/main.css">
	<script src="/petweens/js/jquery-1.11.1.min.js"></script>
	<script src="/petweens/js/bootstrap.min.js"></script>
	<script src="/petweens/js/jquery.validate.min.js"></script>

</head>
<body>
	<div  class="wrap">
		<jsp:include page="header.jsp"></jsp:include>
		<div id="main_logo">
			<img src="/petweens/image/logo5.png" align="middle">
		</div>
		<form method="POST"  role="form" id="contact-form" action="/petweens/user/register">
			<div class="form-group login_input">
				<label class="login_label">Email</label>
				<input type="email" value ="${user.email}"name="email" class="form-control" id="email" placeholder="Email">
			</div>
			<div class="form-group login_input">
				<label class="login_label">UserName</label>
				<input type="text" value ="${user.username}"name="username" class="form-control" id="username" placeholder="UserName">
			</div>
			<div class="form-group login_input">
				<label class="login_label">Password</label>
				<input type="password" name="password" class="form-control" id="password" placeholder="Password">
			</div>
			<div class="form-group login_input">
				<label class="login_label">Confirm Password</label>
				<input type="password" class="form-control" name="passwordconfirm" id="passwordconfirm" placeholder="Confirm Password">
			</div>
			<div class="form-group login_input">
				<!--TO DO : button Tag / type change-->
				<input  type="submit" class="join_button btn btn-primary" value="Join">
			</div>
		</form>
	</div>
	<script type="text/javascript">
	$(document).ready(function () {

		jQuery.validator.addMethod("alphanumeric", function(value, element) {
	        return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
		}); 
	    $('#contact-form').validate({
	    	errorClass : "my-error-class",
	        rules: {
	            username: {
	                minlength: 2,
	                maxlength:20,
	                required: true,
	                alphanumeric: true,
	                remote:{
	                    type:"post",
	                    url:"/petweens/user/checkName",
	                    dataType: "json",
	                    data: {
	                        username: function(){
	                            return $("#username").val();
	                        }
	                    }
	                }
	            },
	            email: {
	                required: true,
	                email: true,
	                remote:{
	                    type:"post",
	                    url:"/petweens/user/checkEmail",
	                    data: {
	                        email: function(){
	                            return $("#email").val();
	                        }
	                    }
	                }
	            },
	            password: {
	                minlength: 7,
	                required: true
	            },
	            passwordconfirm : {
	                minlength: 7,
	                required: true,
	                equalTo: "#password"
	            }
	        },
	        messages: {
	            username: {
	                remote: "Aleady Exist User Name",
	                alphanumeric : "Only Possible alphanumeric characters"
	            },
	            email: {
	            	remote: "Aleady Exist Email"
	            },
	            passwordconfirm : {
	                equalTo: "Password must be same"   
	            }
	        },
	        highlight: function (element, errorClass, validClass) {
	        	 $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
	        },
	        success: function (element, errorClass, validClass) {
	        	$(element).closest('.form-group').removeClass('has-error').addClass('has-success');
	        }
	    });

	});
	
	</script>
</body>
</html>