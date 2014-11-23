<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="/petweens/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/petweens/css/main.css">
	<link rel="stylesheet" type="text/css" href="/petweens/css/slide.css">
	<script src="/petweens/js/fabric.js"></script>
	<script src="/petweens/js/jquery-1.11.1.min.js"></script>
	<script src="/petweens/js/bootstrap.min.js"></script>
	<script src="/petweens/js/screenfull.js"></script>
</head>
<body>
<div class="wrap">
		<jsp:include page="header.jsp"></jsp:include>
		<!--  Todo 강의제목 info.getRoomId, info.getUserName 
		
		<p>auth : ${auth}</p>
		<p>roomOwner userId : ${info.userid}</p>
		<p>filename : ${info.filename}</p>
		<p>roomname : ${info.roomname}</p>
		<p>roomKey : ${info.path}</p> 
		<p>page : ${info.endpage}</p>
		-->
		<div class="section">
			<div id = "slide-container">
				<div id = "slideShow">
					<canvas id="basic-canvas" class="lower-canvas"></canvas>

					<div  id="tools" >
						<div id = "line-tools-group">
							<div id="line-width" class="btn-group dropup">
								<button type="button" class="btn btn-default">
									<span class="glyphicon glyphicon-pencil"  aria-hidden="true" ></span>
								</button>
								<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
									<span class="caret"></span>
									<span class="sr-only">Toggle Dropdown</span>
								</button>
								<ul id="drawing-line-width" class="dropdown-menu" role="menu">
									<li value="5" ><a href="#">5</a></li>
									<li value="10" ><a href="#">10</a></li>
									<li value="20"><a href="#" >20</a></li>
									<li value="30"><a href="#">30</a></li>
								</ul>
							</div>
							
							<div class="btn-group" id="drawing-line-color-bar">
								<ul id="drawing-line-color" class="pagination" style = "margin:0" role="menu">
									<li data-value="#FF0000" >
										<button itype="button" class="colorBtn btn btn-defalut" style="background-color:#FF0000;"></button>
									</li>
									<li data-value="#FFA500" >
										<button type="button" class="colorBtn btn btn-defalut" style="background-color:#FFA500;"></button>
									</li>
									<li data-value="#008000" >
										<button type="button" class="colorBtn btn btn-defalut" style="background-color:#008000;"></button>
									</li>
									<li data-value="#0000FF" >
										<button type="button" class="colorBtn btn btn-defalut" style="background-color:#0000FF;"></button>
									</li>
									<li data-value="#000000" >
										<button type="button" class="colorBtn btn btn-defalut" style="background-color:#000000;"></button>
									</li>
								</ul>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-default" onclick="undoObject()">
									<span class="glyphicon glyphicon-circle-arrow-left" aria-hidden="true"></span>
								</button>
							</div>
							<div class="btn-group">
								<button id="clear-canvas" class="btn btn-default" type="button">
									<span class="glyphicon glyphicon-trash"  aria-hidden="true" ></span>
								</button>
							</div>
						</div>
						<button type="button" id = "fullScreenBtn" class="btn btn-default"  onclick="fullScreen()">
							<span class="glyphicon glyphicon-fullscreen"  aria-hidden="true" ></span>
						</button>
						<button type="button" id = "nextPageBtn" class="btn btn-default"  onclick="nextPageOperation()">
							<span class="glyphicon glyphicon-chevron-right"  aria-hidden="true" ></span>
						</button>
						<button type="button" id = "prePageBtn" class="btn btn-default"  onclick="prePageOperation()">
							<span class="glyphicon glyphicon-chevron-left"  aria-hidden="true" ></span>
						</button>
					</div>
				</div>
			</div>
			<div id="right_wrap" class="jumbotron ">
				
			
				<c:choose>
					<c:when test="${auth eq 'student'}">				
						<button id="optionBtn" type="button" class="btn btn-primary">강의 중인 슬라이드</button>
						<div id = "writingContainer" class="panel panel-success">
							<div class="panel-heading">
								<h3 class="panel-title">Writing</h3>
							</div>
							<div class="panel-body">
								<textarea id="writeArea" class="form-control" rows="15"></textarea>
							</div>
						</div>

						<div id="questionContainer" class="panel panel-danger">
							<div class="panel-heading">
								<h3 class="panel-title">Question</h3>
							</div>
							<div class="panel-body">
								<textarea id="questionArea"class="form-control" rows="4"></textarea>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div id="professorContainer" class="panel panel-info">
							<div class="panel-heading">
								<h3 class="panel-title">Question List</h3>
							</div>
							<div id = "questionList" class="panel-body">
								<div class="alert alert-warning" role="alert">dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd</div>
								<div class="alert alert-warning" role="alert">...</div>
								<div class="alert alert-warning" role="alert">...</div>
								<div class="alert alert-warning" role="alert">...</div>
								<div class="alert alert-warning" role="alert">...</div>
								<div class="alert alert-warning" role="alert">...</div>
								<div class="alert alert-warning" role="alert">...</div>
								<div class="alert alert-warning" role="alert">...</div>
								<div class="alert alert-warning" role="alert">...</div>
								<div class="alert alert-warning" role="alert">...</div>

							</div>
						</div> 
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div data-spy="scroll" id="slide_list" >
			<c:forEach var="i" begin="1" end="${info.endpage}">
	  			 <div class="slide_contents col-md-3">
					<a href="#" class="thumbnail">
						<img src="/petweens/room/image/${info.path}/${i}" width:"25%" alt="${i}">
					</a>
				</div>
			</c:forEach>
		</div>
		<script type="text/javascript" src="/petweens/js/slideshow.js"></script>
</div>


</body>
</html>