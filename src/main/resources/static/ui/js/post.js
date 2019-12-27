
var pageNum = 0;
var pageSize = 3;
function load_more_comments(postId){
	var param = new Map();
	param.set("postId",postId);
	param.set("pageSize",pageSize);
	param.set("pageNum",pageNum);
	httpRequest.get("/comment/get/bucket",param,function(response){
		var comment_resp = JSON.parse(response).data;
         var comment_response = '';
         comment_resp.some(function(comment_res){
        	 comment_response = comment_response + "<div class='status-comment-display-box' id="+comment_res.id+">"+
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
						comment_response = comment_response +"<button class='like-button' alt='unlike' onclick='commentlike(event)'><img alt='unlike' width=50% src='/ui/images/heart-like.png'></button>";
					}
					else{
						comment_response = comment_response +"<button class='like-button' alt='unlike' onclick='commentlike(event)'><img alt='like' width=50% src='/ui/images/heart.png'></button>";
					}
					comment_response = comment_response +"</div></div></div></div></div>";
			});
         pageNum = pageNum+1;
		$(".comment-display-container").prepend(comment_response)
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

$(function () {
	var urlParams = new URLSearchParams(window.location.search);
	var postId = urlParams.get('postId');
	var param = new Map();
	param.set("postId",postId);
	httpRequest.get("/posts/getPost",param,function(response){
		console.log(response);
		resp = JSON.parse(response).data;
		var post_response = '';
			if(!resp.post.isStatus){
				post_response = post_response + "<div class='title-content user-content' id='"+resp.post.id+"'>"+
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
									post_response = post_response+"<video controls='controls'><source src='"+resp.post.postImageUrl+"' type='video/mp4'></video>";
							}else{
								post_response = post_response+"<img alt='' src="+resp.post.postImageUrl+" >";
							}
				post_response = post_response+	"<div class='post-content-footer'>"+
							"<div class='navbar-element-icon' id='nav-bar-picture-icon' style='margin:0px'>";
							if(resp.likedByUser){
								post_response = post_response+"<button class='like-button' alt='unlike' onclick='like(event)'><img alt='unlike' width=80% src='/ui/images/liked.png'></button>";
							}else{
								post_response= post_response+"<button class='like-button' alt='like' onclick='like(event)'><img alt='like' width=80% src='/ui/images/like.png'></button>";
							}
							post_response= post_response+
						"</div>"+
						"<div class='likes-count'><b><p class='like-count-number'>"+resp.likesCount+"</p></b>&nbsp<b><p>Likes</p></b></div>"+
					"</div>"+
					"<div class='comment-write-box'>"+
						"<div class='horizontal'><input type='text' placeholder='Add a comment' onfocus='comment()' class='chat-text-box comment-box'/></div>"+
						"<p align='left' style='cursor: pointer;margin: 15px;line-height: 0px;' onclick=load_more_comments('"+resp.post.id+"')>Load More Comments</p>"+
					"</div>"+"</div>"+
					"<div class='comment-display-container'>";
					resp.comments.some(function(comment_res){
						post_response = post_response + "<div class='status-comment-display-box' id="+comment_res.id+">"+
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
								post_response = post_response +"<button class='like-button' alt='unlike' onclick='commentlike(event)'><img alt='unlike' width=50% src='/views/images/heart-like.png'></button>";
							}
							else{
								post_response = post_response +"<button class='like-button' alt='like' onclick='commentlike(event)'><img alt='like' width=50% src='/views/images/heart.png'></button>";
							}
							post_response = post_response +"</div></div></div></div></div>";
					});
					post_response = post_response +"</div></div></div></div>"
					
			}else{
			post_response = post_response + "<div class='title-content user-content' id='"+resp.post.id+"'>"+
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
							post_response = post_response+"<button class='like-button' alt='unlike' onclick='like(event)'><img alt='unlike' width=80% src='/views/images/liked.png'></button>";
						}else{
							post_response= post_response+"<button class='like-button' alt='like' onclick='like(event)'><img alt='like' width=80% src='/views/images/like.png'></button>";
						}
						post_response= post_response+
					"</div>"+
					"<div class='likes-count'><b><p class='like-count-number'>"+resp.likesCount+"</p></b>&nbsp<b><p>Likes</p></b></div>"+
				"</div>"+
				"<div class='comment-write-box'>"+
					"<div class='horizontal'><input type='text' placeholder='Add a comment' onfocus='comment()' class='chat-text-box comment-box'/></div>"+
					"<p align='left' style='cursor: pointer;margin: 15px;line-height: 0px;' onclick=load_more_comments('"+resp.post.id+"')>Load More Comments</p>"+
				"</div>"+"</div>" +
						"<div class='comment-display-container'>";
				resp.comments.some(function(comment_res){
					post_response = post_response + "<div class='status-comment-display-box' id="+comment_res.id+">"+
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
							post_response = post_response +"<button class='like-button' alt='unlike' onclick='commentlike(event)'><img alt='unlike' width=50% src='/ui/images/heart-like.png'></button>";
						}
						else{
							post_response = post_response +"<button class='like-button' alt='unlike' onclick='commentlike(event)'><img alt='like' width=50% src='/ui/images/heart.png'></button>";
						}
						post_response = post_response +"</div></div></div></div></div>";
				});
				post_response = post_response +"</div></div></div></div>"
			}
		$(".center").append(post_response);
	})
	
});



