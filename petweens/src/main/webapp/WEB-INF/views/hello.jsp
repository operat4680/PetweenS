
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
	<input type="text" id="question">
	<script type="text/javascript" src="/petweens/js/jquery-1.11.1.min.js"></script>
 	<script type="text/javascript" src="/petweens/js/sockjs-0.3.4.js"></script>
    <script type="text/javascript" src="/petweens/js/stomp.js"></script>
    <script type="text/javascript">
    var path='';
    var auth='';
    var socket = new SockJS('/petweens/lecture');
    var stompClient = Stomp.over(socket);
    stompClient.connect({},function(frame){
    	stompClient.subscribe('/user/queue/key', function(key){
    		var data = JSON.parse(key.body);
            auth = data['auth'];
            key = data['key'];
            
            if(auth=='student'){
            	subscribeRoom(key);
            }
            else{
            	subscribeQuestion(key)	
            }
        });
    	requestKey();
    });

    function requestKey(){
    	var data = $('#roomnum').text();
    	stompClient.send('/app/getkey');
    }
    function subscribeRoom(room){
    	stompClient.subscribe('/topic/canvas/'+room,function(data){
			console.log('canvasData in');
    	});
    }
    function subscribeQuestion(room){
    	stompClient.subscribe('/topic/question/'+room,function(data){
    		//교수에게 질문이 들어왔을때.
    		$('#roomnum').text(data.body);
    		console.log('question in');
    	});
    }
    
    //데이터 전송
    function sendPublish(){
    	stompClient.send('/app/publish',{},'1in');
    }
    //질문전송
    function sendQuestion(){
    	
    	var text = $('#question').val();
    	stompClient.send('/app/userQuestion',{},text);
    }
    </script>
    
    <button id="sendName" onclick="sendQuestion();">Send</button>
</body>
</html>