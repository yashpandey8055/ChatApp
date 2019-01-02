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
					"<div class='status-content-box'><div align='left' style='margin: 15px;'>"+response.post.status+"</div>"+
					"<div class='post-content-footer'>"+
						"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
						"<img width=100% src='/views/images/heart-2.png'>"+
					"</div>"+
				"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
					"<img width=100% src='/views/images/message-icon.png'>"+
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
function previewAndUpload(result){
	$("body").append("<div class='pop-up-box split vertical-align'>"+
	"<div align=right><button type='button' id='close_button' class='close' onclick='close_this()'><span aria-hidden='true'>&times;</span></button></div>"+
	"<input type='text' placeholder='write something' id='photo_status_text'  class='chat-text-box'/><hr>"+
	"<div class='cropper-container' id='cropper-container'>"+
		"<img src='"+result+"' style='max-width: 300px;max-height: 300px;'/>"+
		"<div class='cropper' id='cropper'>"+
			"<ul>"+
				"<li class='top-left-resizer'></li>"+
				"<li class='top-right-resizer'></li>"+
				"<li class='bottom-left-resizer'></li>"+
				"<li class='bottom-right-resizer'></li>"+
			"</ul>"+
		"</div>"+
	"</div>"+
	"<hr>"+
	"<button type='button' class='btn purple-button full-width-btn' onclick='uploadImage()'>Upload</button>"+
"</div>")
}
function close_this(){
	$('.pop-up-box').remove();
	$('#image-upload').val("");
	console.log("close clicked")
}
function uploadImage(){
	var formData = new FormData();
	formData.append("file",file);
	formData.append("status",$("#photo_status_text").val())
	formData.append("startX",cropper.offsetLeft);
	formData.append("startY",cropper.offsetTop)
	formData.append("cropHeight",cropper.offsetHeight)
	formData.append("cropWidth",cropper.offsetWidth)
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		 if (xhr.readyState == 4 && xhr.status == 200){
			 var response = JSON.parse(this.responseText);
			 display_notification_popup("uploaded Succesfully");
			 close_this();
			 $(".center").append("<div class='title-content user-content'>"+
				"<div class='post-content-header'>"+
					"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
						"<img height=100% id='nav-bar-profile-picture' width=100% src="+response.user.profileUrl+">"+
					"</div>"+
					"<div><h5><b>"+response.user.firstName+"</b></h5></div>"+
				"</div>"+
				"<div class='post-content'>"+
					"<div class='post-content-container horizontal'>"+
						"<div class='post-content-box'><img alt='' id='post_1' src="+response.post.postImageUrl+" >"+
						"<div class='post-content-footer'>"+
							"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
							"<img width=100% src='/views/images/heart-2.png'>"+
						"</div>"+
					"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
						"<img width=100% src='/views/images/message-icon.png'>"+
					"</div>"+
					"</div>"+
					"<div class='comment-write-box'>"+
						"<div class='horizontal'><input type='text' placeholder='Add a comment'  class='chat-text-box comment-box'/></div>"+
					"</div>"+
						
						"</div>"+
						"<div class='comment-display-box'>"+
								"<div class='' align='left'><div>"+
									"<div class='post-content-header'>"+
										"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
											"<img height=100% id='nav-bar-profile-picture' width=100% src='https://s3.ap-south-1.amazonaws.com/ketu-user-profile-pictures/yash.jpg'>"+
										"</div>"+
										"<div><h5><b>imyash8055</b></h5></div>"+
									"</div>"+
									"<p class=''>What has should have been all apologies asdasdas dasdasdasdasd </p></div>"+
								"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
									"<img width=50% src='/views/images/heart-2.png'>"+
								"</div>"+
								"</div>"+
								"<div class='' align='left'><div>"+
									"<div class='post-content-header'>"+
										"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
											"<img height=100% id='nav-bar-profile-picture' width=100% src='https://s3.ap-south-1.amazonaws.com/ketu-user-profile-pictures/yash.jpg'>"+
										"</div>"+
										"<div><h5><b>imyash8055</b></h5></div>"+
									"</div>"+
									"<p class=''>What has should have been all apologies asdasdas dasdasdasdasd </p></div>"+
								"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
									"<img width=50% src='/views/images/heart-2.png'>"+
								"</div>"+
								"</div>"+
						"</div>"+
					"</div>"+
				"</div>"+
			"</div>");
			
		}else if(xhr.readyState == 4 &&xhr.status !== 200){
			 display_notification_popup("Suck My dick");
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
		}else if(xhr.readyState == 4 &&xhr.status !== 200){
			 display_notification_popup("Suck My dick");
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
						"<img width=50% src='/views/images/heart-2.png'>"+
					"</div>"+
					"</div>"+
				"</div>"+
	"</div>"+
"</div>");
	});
}

$(function () {

	//Upload Image
	$(document).on('change', '#image-upload', function(e) {
		file = e.target.files[0];
		var reader = new FileReader();
		console.log(file.type);
		reader.onloadend = function(){
			previewAndUpload(reader.result)
			load();
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
	//Enter Comment
	//Key 13 is enter key
	$(this).keypress(function(e){
		var key = e.which;
		if(key==13){
			postComment($(e.target).parents('[id]:last').attr('id'),$(e.target).val(),currentUser.userName)
			return false;
		}
	});
	
	httpRequest.get("/dashboard/getPosts",null,function(response){
		response = JSON.parse(response);
		var dashboard_response = '';
		response.some(function(resp){
			dashboard_response = dashboard_response + "<div class='title-content user-content' id='"+resp.post.id+"'>"+
			"<div class='post-content-header'>"+
				"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
					"<img height=100% id='nav-bar-profile-picture' width=100% src='"+resp.user.profileUrl+"'>"+
				"</div>"+
				"<div><h5><b>"+resp.user.username+"</b></h5></div>"+
			"</div>"+ 
			"<div class='post-content'>"+
			"<div class='post-content-container'>"+
					"<div class='status-content-box'><div align='left' style='margin: 15px;'>"+resp.post.status+"</div>"+
					"<div class='post-content-footer'>"+
						"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
						"<img width=100% src='/views/images/heart-2.png'>"+
					"</div>"+
				"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
					"<img width=100% src='/views/images/message-icon.png'>"+
				"</div>"+
				"</div>"+
				"<div class='comment-write-box'>"+
					"<div class='horizontal'><input type='text' placeholder='Add a comment'  class='chat-text-box comment-box'/></div>"+
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
						"<p class=''><b>"+comment_res.userName+"</b>&nbsp"+comment_res.message+"</p>"+
						"<div class='navbar-element-icon like-button' id='nav-bar-picture-icon'>"+
							"<img width=50% src='/views/images/heart-2.png'>"+
						"</div>"+
						"</div>"+
					"</div>"+
						"</div>"+
					"</div>"
				});
				dashboard_response = dashboard_response +"</div></div></div>"
		});
	$(".center").append(dashboard_response);
	})
	
});



