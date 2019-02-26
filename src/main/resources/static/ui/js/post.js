$(function () {
	var urlParams = new URLSearchParams(window.location.search);
	var postId = urlParams.get('postId');
	var param = new Map();
	param.set("postId",postId);
	httpRequest.get("/posts/getPost",param,function(response){
		console.log(response);
		resp = JSON.parse(response);
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
						"<p align='left' style='margin: 15px;line-height: 0px;'>View All Comments</p>"+
					"</div>"+"</div>";
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
					post_response = post_response +"</div></div></div>"
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
					"<p align='left' style='margin: 15px;line-height: 0px;'>View All Comments</p>"+
				"</div>"+"</div>";
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
							post_response = post_response +"<button class='like-button' alt='unlike' onclick='commentlike(event)'><img alt='like' width=50% src='/views/images/heart.png'></button>";
						}
						post_response = post_response +"</div></div></div></div></div>";
				});
				post_response = post_response +"</div></div></div>"
			}
		$(".center").append(post_response);
	})
	
});



