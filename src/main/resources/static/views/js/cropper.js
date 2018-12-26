var mouseX =0, mouseY = 0;
var file = null;
function drag(event){
		mouseX = event.clientX-mouseX;
		mouseY = event.clientY-mouseY;
		if(cropper.offsetTop>=0&&cropper.offsetTop<=container.offsetHeight-cropper.offsetHeight){
		cropper.style.top = (cropper.offsetTop + mouseY) + "px";
		cropper.style.left = (cropper.offsetLeft + mouseX) + "px";
		}
		mouseX =event.clientX;
		mouseY = event.clientY;
}
function resize(event){
	var resizefactor;
	mouseY = event.clientY-mouseY;
	mouseX = event.clientX-mouseX;
	if(mouseY>0&&mouseX>0){
		resizefactor = 1.2;
	}else{
		resizefactor = 0.8;
	}
	cropper.style.height = (cropper.offsetHeight + resizefactor*mouseY) + "px";
	cropper.style.width = (cropper.offsetHeight + resizefactor*mouseX) + "px";
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
    
	console.log("Cropper top:"+cropper.offsetTop)
	console.log("Cropper left:"+cropper.offsetLeft)
	console.log("Image top:"+cropImage.offsetTop)
	console.log("Image left:"+cropImage.offsetTop)
	
}
function upload(){
	var formData = new FormData();
	formData.append("file",file);
	formData.append("startX",cropper.offsetLeft);
	formData.append("startY",cropper.offsetTop)
	formData.append("cropHeight",cropper.offsetHeight)
	formData.append("cropWidth",cropper.offsetWidth)
	formData.append("fileName",$("#fileName").val())
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "/public/uploadProfilePic");
	xhr.send(formData);
}
$(function(){
	//Upload Image
	$(document).on('change', '#image-upload', function(e) {
		file = e.target.files[0];
		var reader = new FileReader();

		reader.onloadend = function(){
			$(".container").append("<img src='"+reader.result+"' id='crop-image'>");
		};
		
		reader.readAsDataURL(file)
	});
	
	var cropper = document.getElementById("cropper");
	
	var container = document.getElementById("container");
	cropper.style.top = (0) + "px";
	cropper.style.left = (0) + "px";
	cropper.addEventListener('mousemove',function(e){
		if(event.offsetY>cropper.offsetHeight-30&&event.offsetX>cropper.offsetWidth-30){
			$(".cropper").css({"cursor":"crosshair"})
		}else{
			$(".cropper").css({"cursor":"move"})
		}
	});
	cropper.addEventListener('mousedown',function(e){
		$(".cropper").css({"cursor":"move"})
		mouseX = event.clientX;
		mouseY = event.clientY;
		
		if(event.offsetY>cropper.offsetHeight-30&&event.offsetX>cropper.offsetWidth-30){
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
	})
})
