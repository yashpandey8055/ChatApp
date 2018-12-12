var urlParams = new URLSearchParams(window.location.search);
currentChattingWithUser 
var user = urlParams.get('user');
$(function () {
	httpRequest.get(env+"/users/user/"+user,null,function(response){
		response = JSON.parse(response);
		currentChattingWithUser = response.username;
		var downloadingImage = new Image();
		downloadingImage.onload = function(){
			$(".center").prepend("<div class='title-content'>"+
					"<table>"+
					"<tbody>"+
						"<tr>"+
						"<td><div class='user-selected-profile-picture'><img id='user-selected-profile-picture' src='"+this.src+"'/></div></td>"+
						"<td>"+
						"<div class='user-selected-profile-info'>"+
							"<p><b>"+response.firstName+" "+response.lastName+"</b></p><p>"+response.age+","+response.gender+"</p>"+
							"<table>"+
							"<tbody>"+
								"<tr>"+
								"<td><img src='/views/images/icon2.png' height='20px' width='20px'></td>"+
									"<td>Bangalore,India</td>"+
								"</tr>"+
							"</tbody>"+
							"</table>"+
							"<p>"+response.bio+"</p>"+
							"<button type='button' class='btn purple-button full-width-btn' id='follow-user-btn' onclick='follow()'>Follow</button>"+
							"</div>"+
						"</td>"+
						"</tr>"+
					"</tbody>"+
				"</table>"+
				"</div>");
			$("#user-selected-profile-picture").css({"display":"inline"});
		};
		downloadingImage.src = response.profileUrl;
		console.log(JSON.stringify(response));
	})

});