var mouseX =0, mouseY = 0;
function drag(event){
	mouseX = event.clientX-mouseX;
	mouseY = event.clientY-mouseY;

	cropper.style.top = (cropper.offsetTop + mouseY) + "px";
	cropper.style.left = (cropper.offsetLeft + mouseX) + "px";
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
$(function(){
	var cropper = document.getElementById("cropper");
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
