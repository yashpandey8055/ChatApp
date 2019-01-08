var file;

function post(){
	var request = {
			'status':$("#update-user-status").val(),
			'likes':0,
			'commentCount':0,
			'comments':[],
			'isStatus':true,
			'postImageUrl':null
	}
	$("#update-user-status").val('');
	httpRequest.post("/posts/insert",request,function(response){
		response = JSON.parse(response);
		$(".center").append("<div class='title-content user-content' id='"+response.post.id+"'>"+
			"<div class='post-content-header'>"+
				"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
					"<img height=100% id='nav-bar-profile-picture' width=100% src='"+response.user.profileUrl+"'>"+
				"</div>"+
				"<div><h5><b>"+response.user.username+"</b></h5></div>"+
			"</div>"+ 
			"<div class='post-content'>"+
			"<div class='post-content-container'>"+
					"<div class='status-content-box'><div align='left' style='margin: 15px;font-size:20px;'>"+response.post.status+"</div>"+
					"<div class='post-content-footer'>"+
						"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
						"<button class='like-button' onclick='like(event)'><img alt='like' width=80% src='/views/images/like.png'></button>"+
					"</div>"+
				"</div>"+
				"<div class='comment-write-box'>"+
					"<div class='horizontal'><input type='text' placeholder='Add a comment'  class='chat-text-box comment-box'/></div>"+
					"<p align='left' style='margin: 15px;'>View All Comments</p>"+
				"</div>"+
				"</div>"+
			"</div>"+
		"</div>"+
		"</div>")
	})
}
function viewVideoandUpload(result){
	$("body").append("<div class='pop-up-box split vertical-align'>"+
			"<div align=right><button type='button' id='close_button' class='close' onclick='close_this()'><span aria-hidden='true'>&times;</span></button></div>"+
			"<input type='text' placeholder='write something' id='video_status_text' class='chat-text-box'/><hr>"+
			"<div class='cropper-container' id='cropper-container'>"+
			"<video controls='controls'><source src='"+result+"' type='video/mp4'></video>"+
			"</div>"+
			"<hr>"+
			"<button type='button' class='btn purple-button full-width-btn' onclick='uploadVideo()'>Upload</button>"+
		"</div>");
}
function previewAndUpload(){
	$("body").append("<div class='pop-up-box split vertical-align'>"+
	"<div align=right><button type='button' id='close_button' class='close' onclick='close_this()'><span aria-hidden='true'>&times;</span></button></div>"+
	"<input type='text' placeholder='write something' id='photo_status_text'  class='chat-text-box'/><hr>"+
	"<div class='cropper-container' id='cropper-container'>"+	
	"<img src='/views/images/loading2.gif' id='upload_image_src'/>"+
	"</div>"+
	"<hr>"+
	"<button type='button' id='crop_button' class='btn simple-button full-width-btn' onclick='_crop_enable()'>Crop</button>"+
	"<button type='button' id='upload_button' class='btn purple-button full-width-btn' onclick='uploadImage()' disabled>Upload</button>"+
"</div>")
}

function _crop_enable(){
	 $(".cropper-container").append("<div class='cropper' id='cropper'>"+
				"<ul>"+
					"<li class='top-left-resizer'></li>"+
					"<li class='top-right-resizer'></li>"+
					"<li class='bottom-left-resizer'></li>"+
					"<li class='bottom-right-resizer'></li>"+
				"</ul>"+
			"</div>")
			  load();
}
function close_this(){
	$('.pop-up-box').remove();
	$('#image-upload').val("");
	console.log("close clicked")
}
function uploadImage(){
	var img = document.getElementById("upload_image_src");
	console.log(img.offsetHeight+" "+img.offsetWidth);
	console.log($(""))
	var formData = new FormData();
	formData.append("file",file);
	formData.append("status",$("#photo_status_text").val())
	formData.append("startX",cropper?cropper.offsetLeft:0);
	formData.append("startY",cropper?cropper.offsetTop:0)
	formData.append("cropHeight",cropper?cropper.offsetHeight:img.offsetHeight)
	formData.append("cropWidth",cropper?cropper.offsetWidth:img.offsetWidth)
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		 if (xhr.readyState == 4 && xhr.status == 200){
			 var response = JSON.parse(this.responseText);
			 display_notification_popup("uploaded Succesfully");
			 close_this();
			 $(".center").append("<div class='title-content user-content' id='"+response.post.id+"'>"+
				"<div class='post-content-header'>"+
					"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
						"<img height=100% id='nav-bar-profile-picture' width=100% src="+response.user.profileUrl+">"+
					"</div>"+
					"<div><h5><b>"+response.user.firstName+"</b></h5></div>"+
				"</div>"+
				"<div class='post-content'>"+
					"<div class='post-content-container horizontal'>"+
						"<div class='post-content-box'><img alt='' src="+response.post.postImageUrl+" >"+
						"<div class='post-content-footer'>"+
							"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
							"<img width=80% src='/views/images/like.png'>"+
						"</div>"+
					"</div>"+
					"<div class='comment-write-box'>"+
						"<div class='horizontal'><input type='text' placeholder='Add a comment'  class='chat-text-box comment-box'/></div>"+
					"</div>"+
						
						"</div>"+
						"<div class='comment-display-box'>"+
								
						"</div>"+
					"</div>"+
				"</div>"+
			"</div>");
			
		}else if(xhr.readyState == 4 &&xhr.status !== 200){
			 display_notification_popup("Failed");
		}
	}
	xhr.open("POST", "/upload/uploadImage");
	xhr.send(formData);
}

function uploadVideo(){
	var formData = new FormData();
	formData.append("file",file);
	formDate.append("status",$("#video_status_text").val())
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		 if (xhr.readyState == 4 && xhr.status == 200){
			 display_notification_popup("uploaded Succesfully");
			 close_this();
			 $(".center").append("<div class='title-content user-content' id='"+response.post.id+"'>"+
						"<div class='post-content-header'>"+
							"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
								"<img height=100% id='nav-bar-profile-picture' width=100% src="+response.user.profileUrl+">"+
							"</div>"+
							"<div><h5><b>"+response.user.firstName+"</b></h5></div>"+
						"</div>"+
						"<div class='post-content'>"+
							"<div class='post-content-container horizontal'>"+
								"<div class='post-content-box'><video controls='controls'><source src='"+response.post.postImageUrl+"' type='video/mp4'></video>" +
								"<div class='post-content-footer'>"+
									"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
									"<img width=80% src='/views/images/like.png'>"+
								"</div>"+
							"</div>"+
							"<div class='comment-write-box'>"+
								"<div class='horizontal'><input type='text' placeholder='Add a comment'  class='chat-text-box comment-box'/></div>"+
							"</div>"+
								
								"</div>"+
								"<div class='comment-display-box'>"+
										
								"</div>"+
							"</div>"+
						"</div>"+
					"</div>");
		}else if(xhr.readyState == 4 &&xhr.status !== 200){
			 display_notification_popup("Cant upload");
		}
	}
	xhr.open("POST", "/upload/uploadVideo");
	xhr.send(formData);
}
function postComment(postId,comment,userName){
	 var commentRequest = {
			'comment':comment,
			'postId':postId,
			'userName':userName
	}
	httpRequest.post("/comment/insert",commentRequest,function(response){
		console.log(response)
		response = JSON.parse(response);
		if(response.isStatus){
		$('#'+postId).find('.post-content-container').append("<div class='status-comment-display-box'>"+
				"<div class='' align='left'>"+
				"<div class='post-content-header'>"+
					"<div class='comment-header'>"+
						"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
							"<img height=100% id='nav-bar-profile-picture' width=100% src='"+currentUser.profileUrl+"'>"+
						"</div>"+
					"</div>"+
					"<div>"+
					"<p class=''><b>"+response.userName+"</b>&nbsp"+response.message+"</p>"+
					"<div class='navbar-element-icon like-button' id='nav-bar-picture-icon'>"+
						"<img width=70% src='/views/images/like.png'>"+
						"<p>Just Now</p>"+
					"</div>"+
					"</div>"+
				"</div>"+
	"</div>"+
"</div>");
		}else{
			$('#'+postId).find('.comment-display-box').prepend("<div align='left'>"+
					"<div>"+
					"<div class='post-content-header'>"+
						"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
							"<img height='100%' id='nav-bar-profile-picture' width='100%' src='"+currentUser.profileUrl+"'>"+
						"</div>"+
						"<div>"+
							"<h5><b>"+response.userName+"</b></h5>"+
						"</div>"+
					"</div>"+
					"<p>"+response.message+"</p>"+
				"</div>"+
				"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
				"<img width='70%' src='/views/images/like.png'>"+
				"</div>"+
			"</div>");
		}
	});
}
function comment(){
	console.log(this);
	$(this).keypress(function(e){
		var key = e.which;
		if(key==13){
			postComment($(e.target).parents('[id]:last').attr('id'),$(e.target).val(),currentUser.userName)
			return false;
		}
	});
}

$(function () {

	//Upload Image
	$(document).on('change', '#image-upload', function(e) {
		file = e.target.files[0];
		var reader = new FileReader();
		console.log(file.type);
		reader.onloadend = function(){
			previewAndUpload();
			var _img_preview = document.getElementById('upload_image_src');
			var img = document.createElement("img");
			var canvas = document.createElement("canvas");
			img.src = reader.result;
			var MAX_WIDTH = 400;
			var MAX_HEIGHT = 400;
			var height = img.height;
			var width= img.width;
			if (width > height) {
				  if (width > MAX_WIDTH) {
				    height *= MAX_WIDTH / width;
				    width = MAX_WIDTH;
				  }
				} else {
				  if (height > MAX_HEIGHT) {
				    width *= MAX_HEIGHT / height;
				    height = MAX_HEIGHT;
				  }
				}
			canvas.width = width;
			canvas.height = height;
			var ctx = canvas.getContext("2d");
			ctx.drawImage(img, 0, 0,width,height);
			var dataurl = canvas.toDataURL("image/png");
			_img_preview.src = dataurl;
			canvas.toBlob(function(blob) {
				  file = blob; 
				  $('#upload_button').prop('disabled',false);
				});
			
		};
		
		reader.readAsDataURL(file)
	});
	//Upload Video
	$(document).on('change', '#video-upload', function(e) {
		file = e.target.files[0];
		var reader = new FileReader();
		console.log(file.type);
		reader.onloadend = function(){
			viewVideoandUpload(reader.result)
		};
		
		reader.readAsDataURL(file)
	});
	$("#update-user-status").on('focus',function(e){
		$(this).keypress(function(e){
		var key = e.which;
		if(key==13){
			post();
			return false;
		}
	});
	})

	
	httpRequest.get("/dashboard/getPosts",null,function(response){
		response = JSON.parse(response);
		var dashboard_response = '';
		response.some(function(resp){
			if(!resp.post.isStatus){
				dashboard_response = dashboard_response +"<div class='title-content user-content' id='"+resp.post.id+"'>"+
				"<div class='post-content-header'>"+
					"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
						"<img height=100% id='nav-bar-profile-picture' width=100% src="+resp.user.profileUrl+">"+
					"</div>"+
					"<div><h5><b>"+resp.user.firstName+"</b></h5></div>"+
				"</div>"+
				"<div class='post-content'>"+
					"<div class='post-content-container horizontal'>"+
						"<div class='post-content-box'><img alt='' id='post_1' src="+resp.post.postImageUrl+" >"+
						"<div class='post-content-footer'>"+
							"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
							"<button class='like-button' onclick='like(event)'><img alt='like' width=80% src='/views/images/like.png'></button>"+
						"</div>"+
					"</div>"+
					"<div class='comment-write-box'>"+
						"<div class='horizontal'><input type='text' placeholder='Add a comment' onfocus='comment()' class='chat-text-box comment-box'/></div>"+
					"</div>"+
						
						"</div>"+
						"<div class='comment-display-box'>";
					resp.comments.some(function(comment_res){
						dashboard_response = dashboard_response +
						"<div align='left'>"+
							"<div>"+
								"<div class='post-content-header'>"+
									"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
										"<img height='100%' id='nav-bar-profile-picture' width='100%' src='"+comment_res.profileUrl+"'>"+
									"</div>"+
									"<div>"+
										"<h5><b>"+comment_res.userName+"</b></h5>"+
										"<p class='time-ago'>"+comment_res.daysAgo+"</p>"+
									"</div>"+
								"</div>"+
								"<p>"+comment_res.message+"</p>"+
							"</div>"+
							"<div class='navbar-element-icon like-post' id='nav-bar-picture-icon'>"+
							"<button class='like-button' onclick='commentlike(event)'><img alt='like' width=80% src='/views/images/heart.png'></button>"+
							"</div>"+
						"</div>"
					});
					dashboard_response = dashboard_response +	"</div></div></div></div>";
			}else{
			dashboard_response = dashboard_response + "<div class='title-content user-content' id='"+resp.post.id+"'>"+
			"<div class='post-content-header'>"+
				"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
					"<img height=100% id='nav-bar-profile-picture' width=100% src='"+resp.user.profileUrl+"'>"+
				"</div>"+
				"<div><h5><b>"+resp.user.username+"</b></h5></div>"+
			"</div>"+ 
			"<div class='post-content'>"+
			"<div class='post-content-container'>"+
					"<div class='status-content-box'><div align='left' style='margin: 15px;font-size:20px;'>"+resp.post.status+"</div>"+
					"<div class='post-content-footer'>"+
						"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
						"<button class='like-button' onclick='like(event)'><img  class='like-post' alt='like' width=80% src='/views/images/like.png'></button>"+
					"</div>"+
				"</div>"+
				"<div class='comment-write-box'>"+
					"<div class='horizontal'><input type='text' placeholder='Add a comment' onfocus='comment()' class='chat-text-box comment-box'/></div>"+
					"<p align='left' style='margin: 15px;line-height: 0px;'>View All Comments</p>"+
				"</div>"+
				"</div>";
				resp.comments.some(function(comment_res){
					dashboard_response = dashboard_response + "<div class='status-comment-display-box'>"+
					"<div class='' align='left'>"+
					"<div class='post-content-header'>"+
						"<div class='comment-header'>"+
							"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
								"<img height=100% id='nav-bar-profile-picture' width=100% src='"+comment_res.profileUrl+"'>"+
							"</div>"+
						"</div>"+
						"<div>"+
						"<p class=''><b>"+comment_res.userName+"</b>&nbsp"+comment_res.daysAgo+"</p>"+
						"<p class=''>"+comment_res.message+"</p>"+
						"<div class='navbar-element-icon like-button' id='nav-bar-picture-icon'>"+
						"<button class='like-button' onclick='commentlike(event)'><img alt='like' width=80% src='/views/images/heart.png'></button>"+
						"</div>"+
						"</div>"+
					"</div>"+
						"</div>"+
					"</div>"
				});
				dashboard_response = dashboard_response +"</div></div></div>"
			}
		});
		$(".center").append(dashboard_response);
	})
	
});



