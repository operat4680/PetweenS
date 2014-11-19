<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="header">
		<nav class="navbar navbar-default" role="navigation">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<a href="/petweens"><img src="/petweens/image/small_logo.png" align="middle"></a>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav nav-pills navbar-right nav_list" role="tablist">
						<li role="presentation"><a href="/petweens/room/generate">방 만들기</a></li>
						<li role="presentation"><a href="#">강의 목록</a></li>
						<li role="presentation"><a href="#">필기 목록</a></li>
						<c:choose>
							<c:when test="${empty sessionScope['userName']}">
								<li role="presentation"><a href="/petweens/user/signin"><input  type="button" class="join_button btn btn-primary" value="Login"></a></li>
							</c:when>
							<c:otherwise>
								<li role="presentation"><a href="/petweens/user/logout"><input  type="button" class="join_button btn btn-primary" value="Logout"></a></li>
								<c:out value='${sessionScope["userName"]}'/>
							</c:otherwise>
						</c:choose>
						
					</ul>
				</div>
			</div>
		</nav>
</div>