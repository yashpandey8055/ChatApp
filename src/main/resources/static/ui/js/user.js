var urlParams = new URLSearchParams(window.location.search);
var user = urlParams.get('user');
function displayUser(user){
	var displayInfo = "<div class='selected-user-info'>"+
	"<div class='user-selected-profile-picture' style='position:relative;'><img id='user-selected-profile-picture' alt=''></div>"+
	"<div class='user-selected-profile-info'>"+
		"<p><b>"+user.firstName+" "+user.lastName+"</b></p>"+
		"<p>"+user.age+","+user.gender+"</p>"+
		"<table><tr><td><img src='/ui/images/icon2.png' height=20px width=20px></td><td>Bangalore,India</td></tr></table>"+
		"<p>"+user.bio+"</p>"+
		"<hr>"+
		"<div style='margin:15px;'>"+
			"<table style='width:80%'>"+
			"<tr><td><b>Approval rating</b></td><td>"+user.approvals+"</td></tr>"+
			"<tr><td><b>Conversations</b></td><td>"+user.disapprovals+"</td></tr>"+
			"</table>"+
		"</div>"+
		"<hr>"+
		"<div>";
			if(user.userName==currentUser.userName){
				displayInfo = displayInfo + "<button type='button' class='btn purple-button full-width-btn' id='edit-profile' onclick='edit_profile()'>Edit Profile</button>" +
						"<button type='button' class='btn simple-btn full-width-btn'>Connections ("+user.followers+")</button>";
			}else{
				displayInfo = displayInfo +"<button type='button' class='btn purple-button full-width-btn' onclick='message()'>Message</button>" +
						"<button type='button' class='btn simple-btn full-width-btn' id='follow-user-btn'><img height=20px width=20px src='/" +
						"ui/images/loading.gif'></button>"
				httpRequest.get("/user/connect/status/"+user.userName,null,function(response){
					response = JSON.parse(response);
					if(response.type === 'Success'){
						if(response.data === 'Connect'){
							$("#follow-user-btn").removeClass("simple-btn").addClass("purple-button");
						}
						$("#follow-user-btn").html(response.data);
					}else{
						$("#follow-user-btn").html("Couldn't Connect");
						$("#follow-user-btn").attr("disabled",true);
					}
				});
			}
			displayInfo = displayInfo +"</div></div>";
			$(".front").prepend(displayInfo);
			var downloadingImage = new Image();
			downloadingImage.onload = function(){
			 $("#user-selected-profile-picture").attr("src",this.src);
			 $("#user-selected-profile-picture").css({"display":"inline"});
			};
			downloadingImage.src = user.profileUrl;
			
}
function uploadImage(){
	$("#upload_button").html("<img height=20px width=20px src='/ui/images/loading.gif'>")
	var img = document.getElementById("upload_image_src");
	var formData = new FormData();
	formData.append("file",file);
	formData.append("startX",cropper?cropper.offsetLeft:0);
	formData.append("startY",cropper?cropper.offsetTop:0)
	formData.append("cropHeight",cropper?cropper.offsetHeight:img.offsetHeight)
	formData.append("cropWidth",cropper?cropper.offsetWidth:img.offsetWidth)
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		 if (xhr.readyState == 4 && xhr.status == 200){
			 display_notification_popup("uploaded Succesfully");
				currentUser.profileUrl=this.responseText;
					$("#edit-profile-picture").attr("src",currentUser.profileUrl)
					$("#edit-profile-picture").css({"display":"inline"});
					$('#edit-profile-picture-box').remove();
				
		}else if(xhr.readyState == 4 &&xhr.status !== 200){
			 display_notification_popup("Failed");
		}
	}
	xhr.open("POST", "/upload/changeprofilepicture");
	xhr.send(formData);
}
function previewAndUpload(dataUrl){
	$("body").append("<div class='pop-up-box split vertical-align' id='edit-profile-picture-box'>"+
	"<div align=right><button type='button' id='close_button' class='close' onclick='close_this(event)'><span aria-hidden='true'>&times;</span></button></div>"+
	"<div class='cropper-container' id='cropper-container'>"+	
	"<img src='"+dataUrl+"' id='upload_image_src'/>"+
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
	"<button type='button' id='upload_button' class='btn purple-button full-width-btn' onclick='uploadImage()' disabled>Upload</button>"+
	"</div>")
	load();
}

function edit_profile(){
	validateField.setEmail(true);
	validateField.setUserName(true);
	validateField.setPhoneNumber(true);
	$(".content-container").css({"opacity":"0.1"});
		$("body").prepend("<div class='pop-up-box vertical-align'>" +
				"<div align=right><button type='button' id='close_button' class='close' onclick='close_this(event)'><span aria-hidden='true'>&times;</span></button></div>"+
				"<input type='file' id='image-upload' style='display:none'/> <div class='user-selected-profile-picture' style='margin:auto;margin-top:15px;margin-bottom:15px;'><img id='edit-profile-picture' " +
				"style='display: inline;' src="+currentUser.profileUrl+" alt=''></div>"+
			"<form>"+
		"<div class='row'>"+
		"<div class='col'>"+
	    "<label for='firstName'>First Name</label>"+
	      "<input type='text' class='form-control' id='firstName' placeholder='First name' value="+currentUser.firstName+">"+
	      "</div>"+
			"<div class='col'>"+
			"<label for='lastName'>Last Name</label>"+
	    " <input type='text' class='form-control' id='lastName' placeholder='Last name' value="+currentUser.lastName+">"+
	    "</div>"+
	    "</div>"+
	    "<div class='row-full'>"+
		   "<div class='col'>"+
		   "<label for='Bio'>UserName</label>"+
		   " <input type='text'  class='form-control' id='userName'  placeholder='userName' value='"+currentUser.userName+"'>"+
		   "<small></small>"+
		   "</div>"+
	  	"</div>" +
  	"<div class='row-full'>"+
	   "<div class='col'>"+
	   "<label for='Bio'>Bio</label>"+
	   " <input type='text'  class='form-control' id='bio' placeholder='bio' value='"+currentUser.bio+"'>"+
	   "</div>"+
  	"</div>" +
  	"<div class='row'>"+
	   "<div class='col'>"+
	   "<label for='emailId'>Email</label>"+
	   " <input type='text' class='form-control' id='emailId' placeholder='Email' value='"+currentUser.email+"'>"+
	   "<small></small>"+
	   "</div>"+
	   "<div class='col'>"+
	   "<label for='phoneNumber'>Phone</label>"+
	   " <input type='text' class='form-control' id='phoneNumber' placeholder='Phone Number' value="+currentUser.phoneNumber+">"+
	   "<small></small>"+
	   "</div>"+
	"</div>" +
	"<div class='row'>"+
	   "<div class='col'>"+
	   "<label for='select-date'>Date</label>"+
	   "<select  class='form-control select-date' id='select-date'>"+
	     "<option selected>Date</option>"+
	      "</select>"+
	   "</div>"+
	   "<div class='col'>"+
	   "<label for='select-month'>Month</label>"+
	   "<select  class='form-control select-month' id='select-month'>"+
	     "<option selected>Month</option>"+
	      "</select>"+
	   "</div>"+
	   "<div class='col'>"+
	   "<label for='select-year'>Year</label>"+
	   " <input type='text' class='form-control' id='select-year' placeholder='year'>"+
	   "</div>"+
	"</div>" +
	"</form>"+
	"<div style='width:100%;margin:auto;'><button class='btn app-btn btnfull' id='register-button' onclick='update_profile()'>Update Account</button></div>");
    	fill_Date_and_year();
}

function update_profile(){
	if(validateField.checkValidation()&&validate_fields()){
	currentUser.firstName = $("#firstName").val();
	currentUser.lastName = $("#lastName").val();
	currentUser.bio = $("#bio").val();
	currentUser.phoneNumber = $("#phoneNumber").val();
	currentUser.userName = $("#userName").val();
	currentUser.email = $("#emailId").val();
	currentUser.dob=$('#select-date').val()+"/"+$('#select-month').val()+"/"+$('#select-year').val();
	currentUser.yearOfBirth=$('#select-year').val();
	httpRequest.put("/users/user/update",currentUser,function(response){
		display_notification_popup("Updated Succesfully");
		response = JSON.parse(response);
		currentUser= response;
		$("#profile-icon").trigger('click');
	});
	close_this();
	}
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
function postComment(postId,comment,userName){
	 var commentRequest = {
			'comment':comment,
			'postId':postId,
			'userName':userName
	}
	httpRequest.post("/comment/insert",commentRequest,function(response){
		console.log(response)
		response = JSON.parse(response).data;
		$('#'+postId).find('.post-content-container').append("<div class='status-comment-display-box'>"+
				"<div class='' align='left'>"+
				"<div class='post-content-header'>"+
					"<div class='comment-header'>"+
						"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
							"<img height=100% id='nav-bar-profile-picture' width=100% src='"+currentUser.profileUrl+"'>"+
						"</div>"+
					"</div>"+
					"<div>"+
					"<p class=''><b>"+response.userName+"</b>&nbsp Just Now</p>"+
					"<p class=''>"+response.message+"</p>"+
					"<div class='navbar-element-icon like-button' id='nav-bar-picture-icon'>"+
					"<button class='like-button' alt='like' onclick='commentlike(event)'><img alt='like' width=50% src='/ui/images/heart.png'></button>"+
					"</div>"+
					"</div>"+
				"</div>"+
	"</div>"+
"</div>");
	});
}
function close_this(event){
	$(event.target).closest('.pop-up-box').remove();
	if(!($('.pop-up-box')[0])){
		$(".content-container").css({"opacity":"1"});
	}
}
$(function () {
	load_CurrentUser(function(){
	
	httpRequest.get("/users/user/"+user,null,function(response){
		response = JSON.parse(response);
		
		displayUser(response.data);
		
		$(document).on("click","#follow-user-btn",function(){
			follow(user);
		})
	})
	$(document).on("click","#edit-profile-picture",function(){
		$("#image-upload").trigger('click');
	})
	$(document).on('change', '#image-upload', function(e) {
		file = e.target.files[0];
		var reader = new FileReader();
		reader.onloadend = function(){
			var img = document.createElement("img");
			var canvas = document.createElement("canvas");
			img.src = reader.result;
			//Fire the function only after image is loaded
			$(img).one("load",function(){
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
				previewAndUpload(dataurl);
				canvas.toBlob(function(blob) {
					  file = blob; 
					  $('#upload_button').prop('disabled',false);
					});
			})
		};
		
		reader.readAsDataURL(file)
	});
		httpRequest.get("/posts/getuserpost/"+user,null,function(response){
		if(response){
			response = JSON.parse(response).data;
		}else{
			response = [];
		}
		var dashboard_response = '';
		if(response.length<1){
			dashboard_response  = dashboard_response +"<div class='title-content user-content' style='border:none;background-color:#f2f2f2;height:400px;'>" +
					"<div style='margin:auto;margin-top:15px;margin-bottom:15px;' class='vertical-align'><img id='edit-profile-picture' " +
				"style='display: inline;' src='/ui/images/empty-icon.png' alt=''><h3>Its so Empty In here"+
		   "</h3></div>"+
		   
		   "</div>";
		}

		response.some(function(resp){
			if(!resp.post.isStatus){
				dashboard_response = dashboard_response + "<div class='title-content user-content' id='"+resp.post.id+"'>"+
				"<div class='post-content-header'>"+
					"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
						"<img height=100% id='nav-bar-profile-picture' width=100% src='"+resp.user.profileUrl+"'>"+
					"</div>"+
					"<div><h5><b>"+resp.user.firstName+"</b></h5><h6>"+resp.daysAgo+"</h6></div>"+
				"</div>"+ 
				"<div class='post-content'>"+
				"<div class='post-content-container'>"+
						"<div class='status-content-box'><div align='left' style='margin: 15px;font-size:20px;'>"+resp.post.status+"</div>";
								if(resp.post.type == 'video'){
								dashboard_response = dashboard_response+"<video controls='controls'><source src='"+resp.post.postImageUrl+"' type='video/mp4'></video>";
							}else{
								dashboard_response = dashboard_response+"<img alt='' src="+resp.post.postImageUrl+" >";
							}
				dashboard_response = dashboard_response+	"<div class='post-content-footer'>"+
							"<div class='navbar-element-icon' id='nav-bar-picture-icon' style='margin:0px'>";
							if(resp.likedByUser){
								dashboard_response = dashboard_response+"<button class='like-button' onclick='like(event)'><img alt='unlike' width=80% src='/ui/images/liked.png'></button>";
							}else{
								dashboard_response= dashboard_response+"<button class='like-button' onclick='like(event)'><img alt='like' width=80% src='/ui/images/like.png'></button>";
							}
							dashboard_response= dashboard_response+
						"</div>"+
						"<div class='likes-count'><b><p class='like-count-number'>"+resp.likesCount+"</p></b>&nbsp<b><p>Likes</p></b></div>"+
					"</div>"+
					"<div class='comment-write-box'>"+
						"<div class='horizontal'><input type='text' placeholder='Add a comment' onfocus='comment()' class='chat-text-box comment-box'/></div>"+
						"<p align='left' style='margin: 15px;line-height: 0px;'>View All Comments</p>"+
					"</div>"+"</div>";
					resp.comments.some(function(comment_res){
						dashboard_response = dashboard_response + "<div class='status-comment-display-box' id="+comment_res.id+">"+
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
							"<div class='navbar-element-icon like-button' id='nav-bar-picture-icon'>";
							if(comment_res.likedByUser){
								dashboard_response = dashboard_response +"<button class='like-button' onclick='commentlike(event)'><img alt='unlike' width=50% src='/ui/images/heart-like.png'></button>";
							}
							else{
								dashboard_response = dashboard_response +"<button class='like-button' onclick='commentlike(event)'><img alt='like' width=50% src='/ui/images/heart.png'></button>";
							}
							dashboard_response = dashboard_response +"</div></div></div></div></div>";
					});
					dashboard_response = dashboard_response +"</div></div></div>"
			}else{
			dashboard_response = dashboard_response + "<div class='title-content user-content' id='"+resp.post.id+"'>"+
			"<div class='post-content-header'>"+
				"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
					"<img height=100% id='nav-bar-profile-picture' width=100% src='"+resp.user.profileUrl+"'>"+
				"</div>"+
				"<div><h5><b>"+resp.user.firstName+"</b></h5><h6>"+resp.daysAgo+"</h6></div>"+
			"</div>"+ 
			"<div class='post-content'>"+
			"<div class='post-content-container'>"+
					"<div class='status-content-box'><div align='left' style='margin: 15px;font-size:20px;'>"+resp.post.status+"</div>"+
					"<div class='post-content-footer'>"+
						"<div class='navbar-element-icon' id='nav-bar-picture-icon' style='margin:0px'>";
						if(resp.likedByUser){
							dashboard_response = dashboard_response+"<button class='like-button' onclick='like(event)'><img alt='unlike' width=80% src='/ui/images/liked.png'></button>";
						}else{
							dashboard_response= dashboard_response+"<button class='like-button' onclick='like(event)'><img alt='like' width=80% src='/ui/images/like.png'></button>";
						}
						dashboard_response= dashboard_response+
					"</div>"+
					"<div class='likes-count'><b><p class='like-count-number'>"+resp.likesCount+"</p></b>&nbsp<b><p>Likes</p></b></div>"+
				"</div>"+
				"<div class='comment-write-box'>"+
					"<div class='horizontal'><input type='text' placeholder='Add a comment' onfocus='comment()' class='chat-text-box comment-box'/></div>"+
					"<p align='left' style='margin: 15px;line-height: 0px;'>View All Comments</p>"+
				"</div>"+"</div>";
				resp.comments.some(function(comment_res){
					dashboard_response = dashboard_response + "<div class='status-comment-display-box' id="+comment_res.id+">"+
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
						"<div class='navbar-element-icon like-button' id='nav-bar-picture-icon'>";
						if(comment_res.likedByUser){
							dashboard_response = dashboard_response +"<button class='like-button' onclick='commentlike(event)'><img alt='unlike' width=50% src='/ui/images/heart-like.png'></button>";
						}
						else{
							dashboard_response = dashboard_response +"<button class='like-button' onclick='commentlike(event)'><img alt='like' width=50% src='/ui/images/heart.png'></button>";
						}
						dashboard_response = dashboard_response +"</div></div></div></div></div>";
				});
				dashboard_response = dashboard_response +"</div></div></div>"
			}
		});
		$(".center").append(dashboard_response);
	})
});
});