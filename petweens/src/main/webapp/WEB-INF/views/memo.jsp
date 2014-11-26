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
<div  class="wrap">
	<jsp:include page="header.jsp"></jsp:include>
	<div class="contents">
         <div class=" page-header">
            <h1>필기목록</h1>
         </div>
         <div id="writingList">
         	<c:forEach var="info" items="${memoList}">
	            <h3><a href="/petweens/room/${info.roomid}">${info.roomname}</a></h3>
	            <div class="jumbotron">
	                <p id="writingArea" >${info.memo}</p>
	            </div>
            </c:forEach>
     	</div>
	</div>
</div>
</body>