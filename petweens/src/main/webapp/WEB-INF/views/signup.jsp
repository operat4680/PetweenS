<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="/petweens/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/petweens/css/join.css">
	<script src="/petweens/js/jquery-1.11.1.min.js"></script>
	<script src="/petweens/js/bootstrap.min.js"></script>
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/jquery.validate.min.js"></script>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div id="main_logo">
		<img src="/petweens/image/logo5.png" align="middle">
	</div>
	<div  class="wrap">
		<form method="POST" class="form-horizontal" role="form" id="contact-form" action="/petweens/user/register">
			<div class="join_input control-group">
				<label class="join_label">Email</label>
				<input type="email" value ="${user.email}"name="email" class="form-control" id="email" placeholder="Email">
			</div>
			<div class="join_input control-group">
				<label class="join_label">UserName</label>
				<input type="text" value ="${user.username}"name="username" class="form-control" id="username" placeholder="UserName">
			</div>
			<div class="join_input control-group">
				<label class="join_label">Password</label>
				<input type="password" name="password" class="form-control" id="password" placeholder="Password">
			</div>
			<div class="join_input control-group">
				<label class="join_label">Confirm Password</label>
				<input type="password" class="form-control" name="passwordconfirm" id="passwordconfirm" placeholder="Confirm Password">
			</div>
			<div class="join_input control-group">
				<!--TO DO : button Tag / type change-->
				<input  type="submit" class="join_button btn btn-default" value="Join">
				<input  type="button" class="join_button btn btn-primary" value="Cancel">
			</div>
		</form>
	</div>
	<script type="text/javascript">
	
	$(document).ready(function () {
		jQuery.validator.addMethod("alphanumeric", function(value, element) {
	        return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
		}); 
	    $('#contact-form').validate({
	        rules: {
	            username: {
	                minlength: 2,
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
	            	remote: "Aleady Exist User Name"
	            },
	            passwordconfirm : {
	                equalTo: "Password must be same"   
	            }
	        },
	        highlight: function (element) {
	            $(element).closest('.control-group').removeClass('success').addClass('error');
	        },
	        success: function (element) {
	            element.addClass('valid')
	                .closest('.control-group').removeClass('error');
	        }
	    });

	});
	
	</script>
</body>
</html>