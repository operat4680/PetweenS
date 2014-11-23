
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="/petweens/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/petweens/css/main.css">
	<link rel="stylesheet" type="text/css" href="/petweens/css/loading.css">
	<script src="/petweens/js/jquery-1.11.1.min.js"></script>
	<script src="/petweens/js/bootstrap.min.js"></script>
	<script src="/petweens/js/jquery.validate.min.js"></script>
	<script src="/petweens/js/additional-methods.min.js"></script>
</head>
<body>
	<div class="wrap">
		<jsp:include page="header.jsp"></jsp:include>
		<div class="page-header">
			<h1>강좌추가</h1>
		</div>
		<form class="form-horizontal" role="form" action="/petweens/room/create" method="post" id="createForm" enctype="multipart/form-data">
			<div class="form-group">
				<label class="col-sm-2 control-label" for="formGroupInputLarge">Room Name</label>
				<div class="col-sm-10">
					<input class="form-control" type="text" name="roomname" id="roomname" placeholder="Room Name">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">Secret</label>
				<div class="col-sm-10">
  					<div class="checkbox">
  						<label>
	    					<input type="checkbox" name="ispasswd" id="password" value=0>
	    				</label>
	    			</div>
    			</div>
  			</div>
				<div class="form-group" id="pwdGroup">
				<label for="inputPassword" class="col-sm-2 control-label">Password</label>
				<div class="col-sm-10">
					<input type="password" name="password" class="form-control" id="inputPassword" placeholder="Password">
			    </div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="formGroupInputLarge">File</label>
				<div class="col-sm-10">
					<input type="file" name="file" id="file">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input  type="submit" class="btn btn-primary" id="formbutton" value="Make Room">
				</div>
			</div>
		</form>
		
		<c:if test="${fileError==true}">
			 <div class="alert alert-danger" role="alert"> 
					  <span class="sr-only">Error:</span>File is Invalid. Just use ppt,pptx
			</div>
		</c:if>
		<div class="modal"><!-- Place at bottom of page --></div>
	</div>
</body>
<script>
$(function(){
	buttonCheck();
	$body = $("body");
		
	$('#password').click(function(){
		buttonCheck();
	});
	function buttonCheck(){
		if(ischecked()){
			$('#pwdGroup').show();
			$('#password').val(1);
		}
		else{
			$('#pwdGroup').hide();
			$('#password').val(0);
		}
	}
	function ischecked(){
		var ischecked=$("input:checkbox[id='password']").is(":checked");
		if(ischecked==1)return true;
		return false;
	}
	jQuery.validator.addMethod("ispasswd", function(value, element) {
		console.log(value);
        return !ischecked() || value!='';
	});
	$('#createForm').validate({
		submitHandler: function(form) {
			$body.addClass("loading");
		    form.submit();
		 },
        rules: {
            roomname: {
                minlength: 2,
                required: true
            },
            file: {
            	required: true,
            	extension: "ppt|pptx"
            },
            password:{
            	ispasswd: true
            }
        },
        messages: {
        	password:{
        		ispasswd : "This field is required"
        	},
        	file: {
            	extension: "File is invalid. Just use ppt,pptx"
            },
        }
     });
	function checkFile(){
		var ext = $('#file').val().split('.').pop().toLowerCase();
	    if($.inArray(ext, ['ppt','pptx']) == -1) {
	 		return false;
	    }
	    else{
	    	return true;
	    }
	}
});
</script>
</html>