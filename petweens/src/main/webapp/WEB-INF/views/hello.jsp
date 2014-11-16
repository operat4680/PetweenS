
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p id=roomnum>1</p>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.0.js"></script>
 	<script type="text/javascript" src="<c:url value="/js/sockjs-0.3.4.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/stomp.js"/>"></script>
    <script type="text/javascript">
    var key='';
    var socket = new SockJS('/petweens/lecture');
    var stompClient = Stomp.over(socket);
    stompClient.connect({},function(frame){
    	stompClient.subscribe('/user/queue/key', function(key){
            subscribeRoom(key);
        });
    	requestKey();
    });

    function requestKey(){
    	var data = $('#roomnum').text();
    	stompClient.send('/app/getkey',{},data);
    }
    function subscribeRoom(room){
    	stompClient.subscribe('/topic/'+room.body,function(data){
    		console.log(data+'??');
    		key=room.body
    		$('#roomnum').text(data);
    	});
    }
    function sendPublish(){
    	stompClient.send('/app/publish',{},'1in');
    }
    </script>
    
     <button id="sendName" onclick="sendPublish();">Send</button>
</body>
</html>