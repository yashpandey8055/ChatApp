var mouseX =0, mouseY = 0;
var container,cropper;
var file = null;
function drag(event){
		mouseX = event.clientX-mouseX;
		mouseY = event.clientY-mouseY;
		var topValue;
		var leftValue;
		if(cropper.offsetTop>=0&&cropper.offsetTop<=container.offsetHeight-cropper.offsetHeight){
		topValue =  (cropper.offsetTop + mouseY) + "px";
		}else if(cropper.offsetTop<0){
			topValue = (0) + "px";
		}else if(cropper.offsetTop>container.offsetHeight-cropper.offsetHeight){
			topValue = container.offsetHeight-cropper.offsetHeight + "px";
		}
		if(cropper.offsetLeft>=0&&cropper.offsetLeft<=container.offsetWidth-cropper.offsetWidth){
			leftValue = (cropper.offsetLeft + mouseX) + "px";
			}else if(cropper.offsetLeft<0){
				leftValue = (0) + "px";
			}else if(cropper.offsetLeft>container.offsetWidth-cropper.offsetWidth){
				leftValue = container.offsetWidth-cropper.offsetWidth + "px";
		}
		cropper.style.top = topValue;
		cropper.style.left = leftValue;
		mouseX =event.clientX;
		mouseY = event.clientY;
}
function resize(event){
	var resizefactor;
	mouseY = event.clientY-mouseY;
	mouseX = event.clientX-mouseX;
	var cropperHeight,cropperWidth;
	if(mouseY>0&&mouseX>0){
		resizefactor = 2;
	}else{
		resizefactor = 1;
	}
	
	cropperHeight = (cropper.offsetHeight+cropper.offsetTop)<=container.offsetHeight?(cropper.offsetHeight + resizefactor*mouseY)
			:container.offsetHeight-cropper.offsetTop;
	cropperWidth = (cropper.offsetWidth+cropper.offsetLeft)<=container.offsetWidth?(cropper.offsetWidth + resizefactor*mouseX)
			:container.offsetWidth-cropper.offsetLeft;
	var resize = cropperHeight>cropperWidth?cropperWidth:cropperHeight;
	cropper.style.height = resize + "px";
	cropper.style.width = resize + "px";
	mouseY = event.clientY;
	mouseX = event.clientX;
}

function crop(){
	var cropper = document.getElementById("cropper")
	var cropImage = document.getElementById("crop-image")
	var canvas = document.createElement('canvas');

	var sourceY =cropper.offsetTop;
	var sourceX = cropper.offsetLeft;
	var sourceHeight = cropper.offsetHeight;
	var sourceWidth =  cropper.offsetWidth;
	canvas.offsetHeight = cropper.offsetHeight;
	canvas.offsetWidth = cropper.offsetWidth;
	var destY =cropper.offsetTop;
	var destX = cropper.offsetLeft;
	var destHeight = cropper.offsetHeight;
	var destWidth = cropper.offsetWidth;
	var context = canvas.getContext('2d');
    context.drawImage(cropImage, sourceX, sourceY, sourceWidth, sourceHeight, destX, destY, destWidth, destHeight);
   
    var srcUrl = canvas.toDataURL();
     $("#crop-image").attr({"src":srcUrl})
      $("#crop-image").css({"border-radius":"50%"})
    
	
}
function load(){
	cropper = document.getElementById("cropper");
	container = document.getElementById("cropper-container");
	cropper.style.top = 0+ "px";
	cropper.style.left = 0 + "px";

	cropper.addEventListener('mousemove',function(e){
		if(event.offsetY<20&&event.offsetX<20){
			$(".cropper").css({"cursor":"crosshair"})
		}else{
			$(".cropper").css({"cursor":"move"})
		}
	});
	cropper.addEventListener('mousedown',function(e){
		$(".cropper").css({"cursor":"move"})
		mouseX = event.clientX;
		mouseY = event.clientY;
		if(event.offsetY<20&&event.offsetX<20){
			$(".cropper").css({"cursor":"crosshair"})
			
			cropper.addEventListener('mousemove',resize);
			
			cropper.addEventListener('mouseup',function(e){
				$(".cropper").css({"cursor":"contex-menu"})
					$(".cropper").css({"cursor":"context-menu"})
					cropper.removeEventListener('mousemove',resize)
				})
		}else{
			cropper.addEventListener('mousemove',drag);
			cropper.addEventListener('mouseup',function(e){
				$(".cropper").css({"cursor":"contex-menu"})
					$(".cropper").css({"cursor":"context-menu"})
					cropper.removeEventListener('mousemove',drag)
				})
		}
		$(document).mouseup(function(e){
			cropper.removeEventListener('mousemove',drag)
			cropper.removeEventListener('mousemove',resize)
		})
	})
}
