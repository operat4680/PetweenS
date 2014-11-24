$(function() {
	/* canvas Initialization */
	var windowWidth = window.innerWidth,
	windowHeight = window.innerHeigh,
	canvasWidth = $("#slideShow").width(),
	canvasHeight = $("#slideShow").height()*0.94,
	drawingLineWidth = 10,
	drawingLineColor = "#000000",
	widthCanvasScale = 1,
	heightCanvasScale =1,
	drawObjectArray = new Array(),
	proffesorPage=1,
	auth;

	/* canvas Backgorund Image List */
	var backImg = new Image(),
	imgArray = $("#slide_list").children().children().children("img"),
	imgTotalCount = imgArray.length,
	backImg = imgArray[0],
	currentPage = 0;

	canvas = this.__canvas = new fabric.Canvas('basic-canvas', {
		isDrawingMode: true,
		width:canvasWidth,
		height:canvasHeight,
		selection: false
	});
	
	canvas.setBackgroundImage(backImg.src,canvas.renderAll.bind(canvas),{width:canvas.width, height:canvas.height});
	$("#clear-canvas").click(function() { 
		canvas.clear();
		savePage(currentPage);
		sendCanvasData();
	});

	
	 /* WebSocket Config*/
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
		 stompClient.send('/app/getkey');
	  });

	/* changeLectureMode */
	$("#optionBtn").attr("disabled","disabled");
	$("#optionBtn").popover("hide");

	changeLectureMode = function(){
		$("#optionBtn").attr("class","btn btn-danger");
		$("#optionBtn").prop("disabled",false);
		$("#optionBtn").popover("show");
	};	
	$("#optionBtn").click(function(){
		this.setAttribute("class","btn btn-primary");
		this.setAttribute("disabled","disabled");

	});

	/* Divided by student or professor  */
	var studentValue=0,
		whoValue = $("#who").data("value");
	if(whoValue=="student"){
		$("#line-tools-group").css("display","none");
		$("#nextPageBtn").css("display","none");
		$("#prePageBtn").css("display","none");
		studentValue = 1;
	};
	changeCanvasDrawingMode = function(){
		if (studentValue==1) {
			canvas.isDrawingMode = false;
			return 1;
		}
		else {
			return 0;
		}
	};

	var drawingMode = changeCanvasDrawingMode();
	
	/* change Background-Image */
	$("#slide_list a").click(function(){
		var array = $(this).children("img");
		var img = array[0];
		currentPage = img.getAttribute("alt") -1;
		backImg = imgArray[currentPage];
		changeBackgroundImage(img);
	});

	changeBackgroundImage = function(){
		canvas.clear();
		if(drawObjectArray[currentPage]!=undefined){
			drawPage(currentPage);
		}
		canvas.setBackgroundImage(backImg.src,canvas.renderAll.bind(canvas),{width:canvas.width, height:canvas.height});
		changeLectureMode();
	};

	/* change Line width or Line Color */
	$('#drawing-line-width li').click(function() {
		changeLineWidth($(this).val());
	});

	$('#drawing-line-color li').click(function() {
		changeLineColor($(this).attr("data-value"));
	});

	changeLineColor = function(lineColor){
		canvas.freeDrawingBrush.color = lineColor;
	};

	changeLineWidth = function(lineWidth){
		var realWidth=lineWidth;
		if(screenfull.isFullscreen){
			realWidth*=widthCanvasScale;
		}
		canvas.freeDrawingBrush.width = parseInt(realWidth, 10) || 1;
		drawingLineWidth=lineWidth;
	};


	if (canvas.freeDrawingBrush) {
		canvas.freeDrawingBrush.color = drawingLineColor;
		canvas.freeDrawingBrush.width = parseInt(drawingLineWidth, 10) || 1;
		canvas.freeDrawingBrush.scaleX = widthCanvasScale;
		canvas.freeDrawingBrush.scaleY = heightCanvasScale; 
	};


	/* fullScreenMode Module */
	function resetZoom() {
		canvas.setHeight(canvas.height * (1 / heightCanvasScale));
		canvas.setWidth(canvas.width * (1 / widthCanvasScale));
		var objects = canvas.getObjects();
		for (var i in objects) {
			var scaleX = objects[i].scaleX;
			var scaleY = objects[i].scaleY;
			var left = objects[i].left;
			var top = objects[i].top;

			var tempScaleX = scaleX * (1 / widthCanvasScale);
			var tempScaleY = scaleY * (1 / heightCanvasScale);
			var tempLeft = left * (1 / widthCanvasScale);
			var tempTop = top * (1 / heightCanvasScale);

			objects[i].scaleX = tempScaleX;
			objects[i].scaleY = tempScaleY;
			objects[i].left = tempLeft;
			objects[i].top = tempTop;

			objects[i].setCoords();

		}
		changeLineWidth(drawingLineWidth);
		canvas.setBackgroundImage(backImg.src,canvas.renderAll.bind(canvas),{width:canvas.width, height:canvas.height});
		canvas.renderAll();
		widthCanvasScale = 1;
		heightCanvasScale =1;
	};

	function zoomIn(widthScaleFactor, HeightScaleFactor) {
		widthCanvasScale = widthCanvasScale * widthScaleFactor;
		heightCanvasScale = heightCanvasScale * HeightScaleFactor;

		canvas.setWidth(canvas.width *  widthScaleFactor);
		canvas.setHeight(canvas.height *  HeightScaleFactor);

		var objects = canvas.getObjects();
		for (var i in objects) {
			var scaleX = objects[i].scaleX;
			var scaleY = objects[i].scaleY;
			var left = objects[i].left;
			var top = objects[i].top;

			var tempScaleX = scaleX * widthScaleFactor;
			var tempScaleY = scaleY * HeightScaleFactor;
			var tempLeft = left * widthScaleFactor;
			var tempTop = top * HeightScaleFactor;

			objects[i].scaleX = tempScaleX;
			objects[i].scaleY = tempScaleY;
			objects[i].left = tempLeft;
			objects[i].top = tempTop;

			objects[i].setCoords();
		}
		changeLineWidth(drawingLineWidth);
		canvas.setBackgroundImage(backImg.src,canvas.renderAll.bind(canvas),{width:canvas.width, height:canvas.height});
		canvas.renderAll();
	};

	//windowResizingEventListener
	window.addEventListener("resize", OnResizeCalled, false);
	function OnResizeCalled() {

		if(screenfull.isFullscreen){
			$("tools").css("height","(window.innerHeight*0.04)" + "px");
			zoomIn(window.innerWidth/canvas.width,window.innerHeight*0.96/canvas.height);
		}
		else{
			$("tools").css("height","6%");
			resetZoom();
		}
	};

	savePage = function(page){
		var canvasObject = canvas.toObject();
		var objects = canvasObject['objects'];
		for (var i in objects) {
			if(widthCanvasScale>1){
				objects[i].left= objects[i].left/widthCanvasScale;
				objects[i].top= objects[i].top/heightCanvasScale;
				objects[i].scaleX =objects[i].scaleX/widthCanvasScale;
				objects[i].scaleY =objects[i].scaleY/heightCanvasScale;
			}
			objects[i].selectable = false;
		}
		drawObjectArray[page] = objects;
		
	};
	drawPage = function(page){
		backImg = imgArray[page];
		if(drawObjectArray[page]!=undefined){
			var objects = drawObjectArray[page];
			for(var i in objects){
				if(widthCanvasScale>1){
					objects[i].left= objects[i].left*widthCanvasScale;
					objects[i].top= objects[i].top*heightCanvasScale;
					objects[i].scaleX =objects[i].scaleX*widthCanvasScale;
					objects[i].scaleY =objects[i].scaleY*heightCanvasScale;
				}
				objects[i].selectable=false;
			}
		}

		var temp = canvas.toObject();
		temp['objects']=drawObjectArray[page];
		temp['backgroundImage']='';
		canvas.loadFromJSON(temp);
		canvas.setBackgroundImage(backImg.src,canvas.renderAll.bind(canvas),{width:canvas.width, height:canvas.height});

	};

	/* tools button opration */
	undoObject = function(){
		var objects = canvas.getObjects();
		canvas.remove(objects[objects.length-1]);
		savePage(currentPage);
		sendCanvasData();
	};

	nextPageOperation = function(){
		if(currentPage<imgTotalCount-1){
			savePage(currentPage);
			canvas.clear();
			currentPage = currentPage +1;
			sendCanvasData();
			drawPage(currentPage);
			
			
		}
		
	};
	prePageOperation = function(){
		if(currentPage>0){
			savePage(currentPage);
			canvas.clear();
			currentPage = currentPage -1;
			sendCanvasData();
			drawPage(currentPage);
			
			
		}
		
	};
	fullScreen = function(){
		var slideContainer = document.getElementById('slideShow');
		if(!screenfull.isFullscreen){
			screenfull.request( slideContainer );
		}else{
			$("#tools").css("height","6%");
			screenfull.exit();
		}
	};

	// Keyboard OnKeyDown
	window.addEventListener("keydown", OnKeyPress, false);
	function OnKeyPress(e){
		if(e.keyCode==39 && studentValue==0){
			nextPageOperation();
		}else if(e.keyCode==37 && studentValue==0){
			prePageOperation();
		}
	};
	
	/*draw mouse up event handler*/
	canvas.observe('mouse:up', function() {
			if(auth=='professor'){
	           	savePage(currentPage);
	           	sendCanvasData();
			}
       	});

    function subscribeRoom(room){
    	stompClient.subscribe('/topic/canvas/'+room,function(data){
			//canvas Data in
    		var dataArray = JSON.parse(data.body);
    		professorPage = dataArray['currentPage'];
    		drawObjectArray[professorPage]=dataArray['canvasData'];
    		drawPage(professorPage);	
    	});
    	stompClient.subscribe('/topic/canvasAll/'+room,function(data){
    		var dataArray=JSON.parse(data.body);
    		for(var i in dataArray){
    			var data = JSON.parse(dataArray[i]);
    			drawObjectArray[data['currentPage']]=data['canvasData'];
    		}
    	});
    	stompClient.subscribe('/topic/enter/'+room,function(data){
    		if(data.body=='in'){
    			console.log('professor in');
    		}
    		else{
    			console.log('professor out');
    		}
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
    function sendCanvasData(){
    	var data= new Object();
    	if(auth=='professor'){
	    	data['currentPage']=currentPage;
	    	data['canvasData']=drawObjectArray[currentPage];
	    	stompClient.send('/app/canvasdata',{},JSON.stringify(data));
    	}
    }
    //질문전송
    function sendQuestion(){
    	
    	var text = $('#question').val();
    	stompClient.send('/app/userQuestion',{},text);
    }
	
});

