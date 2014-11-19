
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>이미지 업로드</title>
</head>
<body>
	<form action="/petweens/room/create" method="post" enctype="multipart/form-data">
	    방이름 : <input type="text" name="roomname"><br>
	    비밀방<input type="checkbox" name="ispasswd"><br>
	    업로드 파일<input type="file" name="file"><br>
		<input type="submit" value="전송">
	</form>
</body>
</html>