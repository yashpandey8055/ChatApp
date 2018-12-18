
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
		$(".center").append("<div class='title-content user-content'>"+
			"<div class='post-content-header'>"+
				"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
					"<img height=100% id='nav-bar-profile-picture' width=100% src='https://s3.ap-south-1.amazonaws.com/ketu-user-profile-pictures/yash.jpg'>"+
				"</div>"+
				"<div><h5><b>imyash8055</b></h5></div>"+
			"</div>"+
			"<div class='post-content'>"+
			"<div class='post-content-container'>"+
					"<div class='status-content-box'><div align='left' style='margin: 15px;'>What </div>"+
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
					"<div class='status-comment-display-box'>"+
							"<div class='' align='left'>"+
								"<div class='post-content-header'>"+
									"<div class='comment-header'>"+
										"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
											"<img height=100% id='nav-bar-profile-picture' width=100% src='https://s3.ap-south-1.amazonaws.com/ketu-user-profile-pictures/yash.jpg'>"+
										"</div>"+
										"<div><h5><b>imyash8055</b></h5></div>"+
									"</div>"+
									"<div>"+
									"<p class=''>What has should have been all apologies asdasdas dasdasdasdasd"+
									"</p>"+
									"<div class='navbar-element-icon like-button' id='nav-bar-picture-icon'>"+
										"<img width=50% src='/views/images/heart-2.png'>"+
									"</div>"+
									"</div>"+
								"</div>"+
					"</div>"+
				"</div>"+
				"<div class='status-comment-display-box'>"+
							"<div class='' align='left'>"+
								"<div class='post-content-header'>"+
									"<div class='comment-header'>"+
										"<div class='navbar-element-icon' id='nav-bar-picture-icon'>"+
											"<img height=100% id='nav-bar-profile-picture' width=100% src='https://s3.ap-south-1.amazonaws.com/ketu-user-profile-pictures/yash.jpg'>"+
										"</div>"+
										"<div><h5><b>imyash8055</b></h5></div>"+
									"</div>"+
									"<div>"+
									"<p class=''>What has  </p>"+
									"<div class='navbar-element-icon like-button' id='nav-bar-picture-icon'>"+
										"<img width=50% src='/views/images/heart-2.png'>"+
									"</div>"+
									"</div>"+
								"</div>"+
					"</div>"+
				"</div>"+
			"</div>"+
		"</div>"+
		"</div>")
	})
}

$(function () {
var downloadingImage = new Image();
				downloadingImage.onload = function(){
					var url = this.src;
						$("#post_1").attr("src",url).fadeIn(2000);

				};
	downloadingImage.src = "https://s3.ap-south-1.amazonaws.com/ketu-user-profile-pictures/test.jpg";
});