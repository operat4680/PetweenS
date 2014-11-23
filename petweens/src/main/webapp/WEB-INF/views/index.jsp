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
		<div class="contents">
			<div class=" page-header">
				<h1>강의목록</h1>
			</div>
			<div class="row">
			<c:forEach var="info" items="${infoList}">
				<div class="col-xs-6 col-md-3">
					<a href="/petweens/room/${info.roomid}" class="thumbnail"><img src="/petweens/room/image/${info.path}/1"></a>
					<div class="caption"><h3>${info.roomname}</h3>-${info.username}</div>
				</div>
			</c:forEach>
			</div>
			
		</div>
	</div>
</body>
</html>
