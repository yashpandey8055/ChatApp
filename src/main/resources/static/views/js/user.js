var urlParams = new URLSearchParams(window.location.search);
var user = urlParams.get('user');
var currentChattingWithUser;
function displayUser(user){
	var displayInfo = "<div class='selected-user-info'>"+
	"<div class='user-selected-profile-picture'><img id='user-selected-profile-picture' alt=''></div>"+
	"<div class='user-selected-profile-info'>"+
		"<p><b>"+user.firstName+" "+user.lastName+"</b></p>"+
		"<p>"+user.age+","+user.gender+"</p>"+
		"<table><tr><td><img src='/views/images/icon2.png' height=20px width=20px></td><td>Bangalore,India</td></tr></table>"+
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
				displayInfo = displayInfo + "<button type='button' class='btn simple-btn full-width-btn'>Followers ("+user.followers+")</button>";
			}else{
				displayInfo = displayInfo +"<button type='button' class='btn purple-button full-width-btn' id='follow-user-btn' onclick='follow()'><img height=20px width=20px src='/views/images/loading.gif'></button>"
				httpRequest.get("/users/follow/isfollowing/"+user.username,null,function(response){
					if(response == 'true'){
						$("#follow-user-btn").html("Unfollow");
					}else{
						$("#follow-user-btn").html("Follow");
					}
				});
			}
			displayInfo = displayInfo +"</div></div>";
			$(".front").prepend(displayInfo);
			$(".front").append(
			"<div class='selected-user-info'><h3><b>About</b></h3><hr><p>Experienced Application Developer with a demonstrated history of working in the computer " +
			"hardware industry Skilled in Java,Nodejs Agile Methodologies, Cloud Technologies and Linux."+
			  " Strong engineering professional with a Bachelor’s Degree focused in from National Institute of Technology, Mizoram."+
			"Interests lies in field of Cloud Computing and the rising era of Quantum Computing.</p><hr></div>");
			var downloadingImage = new Image();
			downloadingImage.onload = function(){
			 $("#user-selected-profile-picture").attr("src",this.src);
			 $("#user-selected-profile-picture").css({"display":"inline"});
			};
			downloadingImage.src = user.profileUrl;
			
}

function edit_profile(){
	$(".content-container").css({"opacity":"0.1"});
		$("body").prepend("<div class='pop-up-box  vertical-align' style='top:40%;width:40%;'>" +
				"<div align=right><button type='button' id='close_button' class='close' onclick='close_this()'><span aria-hidden='true'>&times;</span></button></div>"+
				"<h3>Update Profile</h3>"+
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
		   " <input type='text'  class='form-control' id='userName' placeholder='userName' value='"+currentUser.userName+"'>"+
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
	   " <input type='text' class='form-control' id='emailId' placeholder='Email' value="+currentUser.email+">"+
	   "</div>"+
	   "<div class='col'>"+
	   "<label for='emailId'>Phone</label>"+
	   " <input type='text' class='form-control' id='phoneNumber' placeholder='Phone Number' value="+currentUser.phoneNumber+">"+
	   "</div>"+
	"</div>" +
	"<div class='row'>"+
	   "<div class='col'>"+
	   "<label for='select-date'>Date</label>"+
	   " <input type='text' class='form-control' id='select-date' placeholder='Date'>"+
	   "</div>"+
	   "<div class='col'>"+
	   "<label for='select-month'>Month</label>"+
	   " <input type='text' class='form-control' id='select-month' placeholder='Month'>"+
	   "</div>"+
	   "<div class='col'>"+
	   "<label for='select-year'>Year</label>"+
	   " <input type='text' class='form-control' id='select-year' placeholder='year'>"+
	   "</div>"+
	"</div>" +
	"</form>"+
	"<div style='width:100%;margin:auto;'><button class='btn app-btn btnfull' id='register-button' onclick='update_profile()'>Update Account</button></div>");
}

function update_profile(){
	if(validate_fields()){
	currentUser.firstName = $("#firstName").val();
	currentUser.lastName = $("#lastName").val();
	currentUser.bio = $("#bio").val();
	currentUser.phoneNumber = $("#phoneNumber").val();
	currentUser.userName = $("#userName").val();
	currentUser.email = $("#emailId").val();
	currentUser.dob=$('#select-date').val()+"/"+$('#select-month').val()+"/"+$('#select-year').val();
	currentUser.yearOfBirth=$('#select-year').val();
	httpRequest.post("/users/update/",currentUser,function(response){
		var response = JSON.parse(this.responseText);
		currentUser= response;
		document.location.href = env+"/user?user="+response.userName;
	});
	close_this();
	}
}
function close_this(){
	$(".content-container").css({"opacity":"1"});
	$('.pop-up-box').remove();
}
$(function () {
	$("#userName").on("focus",function(){
		var param = new Map();
		param.set("key","userName");
		param.set("value",$("#userName").val());
		httpRequest.get("/public/exist",param,null,function(response){
			if (response=='true'||$("#userName").val()!==currentUser.userName){
				validateField.setUserName(false);
	    		$("#userName").css({"border":"1px solid #b30000"})
	    		$("#userName").next().html("<small class='error-message-display'>Username already taken. Try another<small>")
	        }else {
				validateField.setUserName(true);
				$("#register-button").attr({'disabled':false})
	        	$("#userName").css({"border":"1px solid #008080"})
	    		$("#userName").next().html("<small class='success-message-display'>valid UserName<small>");
	        }
	});
	})
	load_CurrentUser(function(){
	httpRequest.get(env+"/users/user/"+user,null,function(response){
		response = JSON.parse(response);
		displayUser(response);
		currentChattingWithUser = response.username;
		var downloadingImage = new Image();
		downloadingImage.onload = function(){
			var res='';
			res = res +"<div class='title-content'>"+
			"<table>"+
			"<tbody>"+
				"<tr>"+
				"<td>"+
				"<div class='user-selected-profile-info'>"+
					"<p><b>"+response.firstName+" "+response.lastName+"</b></p><p>"+response.age+","+response.gender+"</p>"+
					"<table>"+
					"<tbody>"+
						"<tr>"+
						"<td><img src='/views/images/icon2.png' height='20px' width='20px'></td>"+
							"<td>"+response.city+","+response.country+"</td>"+
						"</tr>"+
					"</tbody>"+
					"</table>"+
					"<p>"+response.bio+"</p>";
			if(user==currentUser.userName){
				res = res +	"<button type='button' class='btn purple-button full-width-btn' id='edit-profile' onclick='edit_profile()'>Edit Profile</button>";
			}else{
				res = res +	"<button type='button' class='btn purple-button full-width-btn' id='follow-user-btn' onclick='message()'>Message</button>";
			}
			res = res +		"</div>"+
				"</td>"+
				"</tr>"+
			"</tbody>"+
		"</table>"+
		"</div>";
			$(".center").prepend(res);
			$("#user-selected-profile-picture").css({"display":"inline"});
		};
		downloadingImage.src = response.profileUrl;
		console.log(JSON.stringify(response));
	})
	
		httpRequest.get("/users/getPosts/"+user,null,function(response){
		response = JSON.parse(response);
		var dashboard_response = '';
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
								dashboard_response = dashboard_response+"<button class='like-button' onclick='like(event)'><img alt='unlike' width=80% src='/views/images/liked.png'></button>";
							}else{
								dashboard_response= dashboard_response+"<button class='like-button' onclick='like(event)'><img alt='like' width=80% src='/views/images/like.png'></button>";
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
								dashboard_response = dashboard_response +"<button class='like-button' onclick='commentlike(event)'><img alt='unlike' width=50% src='/views/images/heart-like.png'></button>";
							}
							else{
								dashboard_response = dashboard_response +"<button class='like-button' onclick='commentlike(event)'><img alt='like' width=50% src='/views/images/heart.png'></button>";
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
							dashboard_response = dashboard_response+"<button class='like-button' onclick='like(event)'><img alt='unlike' width=80% src='/views/images/liked.png'></button>";
						}else{
							dashboard_response= dashboard_response+"<button class='like-button' onclick='like(event)'><img alt='like' width=80% src='/views/images/like.png'></button>";
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
							dashboard_response = dashboard_response +"<button class='like-button' onclick='commentlike(event)'><img alt='unlike' width=50% src='/views/images/heart-like.png'></button>";
						}
						else{
							dashboard_response = dashboard_response +"<button class='like-button' onclick='commentlike(event)'><img alt='like' width=50% src='/views/images/heart.png'></button>";
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