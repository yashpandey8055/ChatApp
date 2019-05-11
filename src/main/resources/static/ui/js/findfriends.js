
var turn = 180;
function displayUserInformation(user){
	//$(".flipper").css({"transform":"rotateY("+turn+"deg)"});
	$(".selected-user-info").empty();
	//$(".selected-user-info").css({"transform":"rotateY("+turn+"deg)"});
	setTimeout(function(){
		var displayInfo = "<div class='selected-user-info'>"+
		"<div class='user-selected-profile-picture'><img id='user-selected-profile-picture' alt=''></div>"+
		"<div class='user-selected-profile-info'>"+
			"<p><b>"+user.userName+"</b></p>"+
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
				if(user.username==currentUser.username){
					displayInfo = displayInfo + "<button type='button' class='btn simple-btn full-width-btn'>Followers ("+user.followers+")</button>";
				}else{
					displayInfo = displayInfo +"<button type='button' class='btn purple-button full-width-btn' id='follow-user-btn' onclick='follow()'><img height=20px width=20px src='/ui/images/loading.gif'></button>"
					httpRequest.get("/users/follow/isfollowing/"+user.username,null,function(response){
						if(response == 'true'){
							$("#follow-user-btn").html("Unfollow");
						}else{
							$("#follow-user-btn").html("Follow");
						}
					});
				}
				displayInfo = displayInfo +"</div></div>";
				$(".selected-user-info").append(displayInfo);
				var downloadingImage = new Image();
				downloadingImage.onload = function(){
				 $("#user-selected-profile-picture").attr("src",this.src);
				 $("#user-selected-profile-picture").css({"display":"inline"});
				};
				downloadingImage.src = user.profileUrl;
				
			}, 200);
//	turn=turn+180;
//	if(turn==360){
//		turn = 0;
//	}
}


var currentChattingWithUser = null;
var isConversationLoadComplete = false;
var currentOnlineUsers = new Map();
var stompClient;
function connect() {
    var socket = new SockJS('http://localhost:8080/ketu-socket?token='+token);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
       stompClient.subscribe('/user/queue/message', function (message){
    	   message = JSON.parse(message.body);
    	   if(currentChattingWithUser==message.sender){
    		   $("#chatbox_"+message.sender).append("<div class='right-message chat-message' align='right'><div><p class='chat-message-text'>"+message.message+"</p></div><p class='chat-message-time'>11.36 am</p></div>") 
    		   $("#chatbox_"+currentChattingWithUser).scrollTop(function() { return this.scrollHeight; });
    	   }
    	   
       });
       stompClient.subscribe('/user/queue/fetchuser', function (message){
    	   var resp = JSON.parse(message.body);
    	   currentChattingWithUser = resp.username;
    	   if(resp.status === 'Connected'){
	    	   if(currentChattingWithUser){
	    		   $(".chat-box").append("<div class='box chat-display-box' id='chatbox_"+currentChattingWithUser+"'></div>");
	    	   }
    	   }
    	   if(resp.status === 'Disconnected'){
	    	   if(currentChattingWithUser){
	    		   $("#chatbox_"+currentChattingWithUser).append("<button type='button' class='btn simple-btn full-width-btn' onclick='close_chat()'>Disconnected. Close Chat?</button>") 
	     		  
	    	   }
    	   }
       });
        
    });
    
}
function close_chat(){
	$("#start_chat_button").prop("disabled",false);
	$("#start_chat_button").text("Start a new Chat");
	$("#chatbox_"+currentChattingWithUser).remove();
	currentChattingWithUser = null;
	
}
function start_chat(){
	$("#start_chat_button").html("<img src='/ui/images/loading.gif' height=20px width=20px>");
	$("#start_chat_button").prop("disabled",true);
	/**
     * Fetch a user for the current user
     * @param response
     * @returns
     */
    httpRequest.get("/findfriend/register",null,function(response){
    	 httpRequest.get("/findfriend/find",null,function(response){
    	    	console.log(response);
    	    	 $("#chatbox_"+currentChattingWithUser).remove();
    	    	response = JSON.parse(response);
    	    		currentChattingWithUser = response.data;
    	    		 if(currentChattingWithUser){
    	      		   $(".chat-box").append("<div class='box chat-display-box' id='chatbox_"+currentChattingWithUser+"'></div>");
    	      	   }
    	    		
    		  });
	  });
}
function send(){
	stompClient.send("/app/message", {}, JSON.stringify({'message': $('#chat-text-box').val()
    	,'receiver':currentChattingWithUser,'sender':currentUser.userName}));
	 $("#chatbox_"+currentChattingWithUser).append("<div class='left-message chat-message' align='left'><div><p class='chat-message-text'>"+$('#chat-text-box').val()+"</p></div><p class='chat-message-time'>Just Now</p></div>");
		$("#chatbox_"+currentChattingWithUser).scrollTop(function() { return this.scrollHeight; });
		$("#chat-text-box").val('');
}


function prependMessages(selectedUser,bucket){
	var params = new Map();
	params.set("receiver",selectedUser);
	params.set("bucket",bucket);
	httpRequest.get("/getMessages",params,function(response){
		var messages = "";
		response = JSON.parse(response);
		if(response.length ==0){
			isConversationLoadComplete =true;
		}
		response.forEach(function(message){
			if(currentUser.username==message.sender){
				messages= messages +"<div class='left-message chat-message' align='left'><div><p class='chat-message-text'>"+message.message+"</p></div><p class='chat-message-time'>11.36 am</p></div>"
			}else{
				messages= messages +"<div class='right-message chat-message' align='right'><div><p class='chat-message-text'>"+message.message+"</p></div><p class='chat-message-time'>11.36 am</p></div>"
			}
		})
			$("#chatbox_"+selectedUser).prepend(messages);
	});
}

$(function () {
	httpRequest.get("/users/current",null,function(response){
		var thisUser = JSON.parse(response);
			displayUserInformation(thisUser.data);
			connect();
		});
	
	
	$("#chat-text-box").keypress(function(e){
		var key = e.which;
		if(key==13){
			$("#send_button").click();
			$("#chat-text-box").val('');
			return false;
		}
	});
	
	 $("#logout").click(function(){
	    	document.cookie = 'token' + '=; expires=Thu, 01-Jan-70 00:00:01 GMT;';
	    	window.location.href = "/ui/login"
	    });
});