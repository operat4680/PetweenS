var windowWidth = window.innerWidth,
windowHeight = window.innerHeigh,
canvasWidth = $("#slideShow").width(),
canvasHeight = $("#slideShow").height()*0.94,
widthCanvasScale = 1,
heightCanvasScale =1,
preScaleX = 1,
preScaleY = 1;

var drawTools = document.getElementById("tools");
var drawObjectArray = new Array();

var drawingLineWidth = 10,
drawingLineColor = "#000000";

var backImg = new Image(),
slideImg = document.getElementById("slide_list"),
imgArray = $("#slide_list").children().children().children("img"),
imgTotalCount = imgArray.length,
backImg = imgArray[0],
currentPage = 0;

var fullScreenStat = 0;

$('#drawing-line-width li').click(function() {
	changeLineWidth($(this).val());
});
$('#drawing-line-color li').click(function() {
	changeLineColor($(this).attr("data-value"));
});
$("#slide_list a").click(function(){
	var array = $(this).children("img");
	var img = array[0];
	currentPage = img.getAttribute("alt") -1;
	backImg = imgArray[currentPage];
	changeBackgroundImage(img);
});

$(function() {
	var $ = function(id){return document.getElementById(id)};
	canvas = this.__canvas = new fabric.Canvas('basic-canvas', {
		isDrawingMode: true,
		width:canvasWidth,
		height:canvasHeight,
		selection: false
	});
	canvas.setBackgroundImage(backImg.src,canvas.renderAll.bind(canvas),{width:canvas.width, height:canvas.height});

	fabric.Object.prototype.transparentCorners = false;

	var clearEl = $('clear-canvas');

	//clear button click
	clearEl.onclick = function() { canvas.clear() };

	// Student : NotDrawingNode
	// drawingModeEl.onclick = function() {
	// 	canvas.isDrawingMode = !canvas.isDrawingMode;
	// 	if (canvas.isDrawingMode) {
	// 		drawingModeEl.innerHTML = 'Cancel drawing mode';
	// 	}
	// 	else {
	// 		canvas.forEachObject(function(object){ object.selectable = false });
	// 		drawingModeEl.innerHTML = 'Enter drawing mode';
	// 	}
	// };
	changeBackgroundImage = function(){
		canvas.clear();
		if(drawObjectArray[currentPage]!=undefined){
			drawPage(currentPage);
		}
		canvas.setBackgroundImage(backImg.src,canvas.renderAll.bind(canvas),{width:canvas.width, height:canvas.height});
	};

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


	// Reset Zoom
	function resetZoom() {
		preScaleX = widthCanvasScale;
		preScaleY = heightCanvasScale;
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
	// Zoom In
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
		var slideContainer = document.getElementById('slideShow');
		if(screenfull.isFullscreen){
			zoomIn(window.innerWidth/canvas.width,window.innerHeight*0.96/canvas.height);
			drawTools.style.height = (window.innerHeight*0.04) + "px";
			canvas.setBackgroundImage(backImg.src,canvas.renderAll.bind(canvas),{width:canvas.width, height:canvas.height});
			fullScreenStat = 1;
		}
		else{
			drawTools.style.height = "6%";
			resetZoom();
			fullScreenStat = 0;

		}
	};

	//undoObject
	undoObject = function(){
		var objects = canvas.getObjects();
		canvas.remove(objects[objects.length-1]);
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
			}
		}

		var temp = canvas.toObject();
		temp['objects']=drawObjectArray[page];
		temp['backgroundImage']='';
		canvas.loadFromJSON(temp);
		canvas.setBackgroundImage(backImg.src,canvas.renderAll.bind(canvas),{width:canvas.width, height:canvas.height});

	};
	nextPageOperation = function(){
		if(currentPage<imgTotalCount-1){
			savePage(currentPage);
			canvas.clear();
			currentPage = currentPage +1;
			drawPage(currentPage);
		}
		
	};
	prePageOperation = function(){
		if(currentPage>0){
			savePage(currentPage);
			canvas.clear();
			currentPage = currentPage -1;
			drawPage(currentPage);
		}
		
	};
	fullScreen = function(){
		var slideContainer = document.getElementById('slideShow');
		if(!screenfull.isFullscreen){
			screenfull.request( slideContainer );
		}else{
			drawTools.style.height = "6%";
			screenfull.exit();
		}
	};
	// Keyboard OnKeyDown
	window.addEventListener("keydown", OnKeyPress, false);
	function OnKeyPress(e){
		if(e.keyCode==39){
			nextPageOperation();
		}else if(e.keyCode==37 ){
			prePageOperation();
		}
	};
	/*
	draw mouse up event handler
	canvas.observe('mouse:up', function() {
           	console.log('mouse click! ');
       	});
       	*/ 
});

