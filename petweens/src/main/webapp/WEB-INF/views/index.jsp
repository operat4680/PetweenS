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
					  <c:choose>
							<c:when test="${info.ispasswd==false}">
					           <a href="/petweens/room/${info.roomid}" class="thumbnail"><img src="/petweens/room/image/${info.path}/1"></a>
							</c:when>
							<c:otherwise>
								 <a href="#myModal" class="thumbnail" data-id="${info.roomid}" data-toggle="modal"><img src="/petweens/image/lock.png"></a>
							</c:otherwise>
					  </c:choose>
					<div class="caption">
                  		<h3>${info.roomname}</h3>
                  		<c:if test="${info.userid eq sessionScope['userId']}">
                 		<a href="/petweens/room/delete/${info.roomid}"><button type="button" class="btn btn-danger btn-xs">Delete</button></a>
                 		</c:if>
               		</div>
               		<h3 id="caption_name"><small>-${info.username}</small></h3>
				</div>
			</c:forEach>
			</div>
		</div>
	</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title" id="myModalLabel">비밀번호를 입력해주세요.</h4>
				</div>
				<form id="modalForm" method="post">
				<div class="modal-body">
					<div class="input-group" style="width:100%">
						<input type="password" name="password" class="form-control">
					</div>
				</div>
				</form>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button type="button" id="request" class="btn btn-primary">확인</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	
	$(function (){
		var id;
		$('#request').click(function(){
			$('#modalForm').attr('action','/petweens/room/'+id);
			$('#modalForm').submit();
		});
		$('#myModal').on('show.bs.modal', function (event) {
			var tag =  $(event.relatedTarget);
			id=tag.data('id');
		});
	});
</script>
</html>
