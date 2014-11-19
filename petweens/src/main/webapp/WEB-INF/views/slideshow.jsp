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
<div class="wrap">
		<jsp:include page="header.jsp"></jsp:include>
		<!--  Todo 강의제목 info.getRoomId, info.getUserName -->
		
		<p>auth : ${auth}</p>
		<p>roomOwner userId : ${info.userid}</p>
		<p>filename : ${info.filename}</p>
		<p>roomname : ${info.roomname}</p>
		<p>roomKey : ${info.path}</p> 
		<p>page : ${info.endpage}</p>
		
		<c:forEach var="i" begin="1" end="${info.endpage}" step="1">
  			 <img src="/petweens/room/image/${info.path}/${i}" />
		</c:forEach>
		

</div>


</body>
</html>