
var turn = 180;
function displayUserInformation(user){
	//$(".flipper").css({"transform":"rotateY("+turn+"deg)"});
	$(".selected-user-info").empty();
	//$(".selected-user-info").css({"transform":"rotateY("+turn+"deg)"});
	setTimeout(function(){
				$(".selected-user-info").append(
						"<div class='selected-user-info'>"+
						"<div class='user-selected-profile-picture'><img id='user-selected-profile-picture' alt=''></div>"+
						"<div class='user-selected-profile-info'>"+
							"<p><b>"+user.firstName+" "+user.lastName+"</b></p>"+
							"<p>"+user.age+","+user.gender+"</p>"+
							"<table><tr><td><img src='/views/images/icon2.png' height=20px width=20px></td><td>Bangalore,India</td></tr></table>"+
							"<p>"+user.bio+"</p>"+
							"<hr>"+
							"<div style='margin:15px;'>"+
								"<table style='width:80%'>"+
								"<tr><td><b>Conversations</b></td><td>"+user.conversationPts+"</td></tr>"+
								"<tr><td><b>Points</b></td><td>"+user.followers+"</td></tr>"+
								"</table>"+
							"</div>"+
							"<hr>"+
							"<div>"+
								"<table style='width:90%'>"+
								"<tr>"+
								"<td><button type='button' class='btn approve-btn'>Approve ("+user.approvals+")</button></td>"+
								"<td><button type='button' class='btn disapprove-btn'>Disapprove ("+user.disapprovals+")</button></td>"+
								"</tr>"+
								
								"</table>"+
							"</div>"+
						"</div>"
						);
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

var token = getCookie();
var stompClient = null;
var currentUser = null;
var currentChattingWithUser = null;
var isConversationLoadComplete = false;
var currentOnlineUsers = new Map();
const httpRequest = new HttpRequest();
function getCookie(){
	 var name = "token=";
	    var decodedCookie = decodeURIComponent(document.cookie);
	    var ca = decodedCookie.split(';');
	    for(var i = 0; i < ca.length; i++) {
	        var c = ca[i];
	        while (c.charAt(0) == ' ') {
	            c = c.substring(1);
	        }
	        if (c.indexOf(name) == 0) {
	            return c.substring(name.length, c.length);
	        }
	    }
	    return "";
}

function connect() {
    var socket = new SockJS(env+'/gs-guide-websocket?token='+token);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
       stompClient.subscribe('/user/queue/message', function (message){
    	   message = JSON.parse(message.body);
    	   if(currentChattingWithUser==message.sender){
    		   $("#chatbox_"+message.sender).append("<div class='right-message chat-message' align='right'><div><p class='chat-message-text'>"+message.message+"</p></div><p class='chat-message-time'>11.36 am</p></div>") 
    		   $("#chatbox_"+currentChattingWithUser).scrollTop(function() { return this.scrollHeight; });
    	   }
    	   else{
    		  var count = $("#notification-user-"+message.sender).text();
    		  $("#notification-user-"+message.sender).text(parseInt(count)+1);
    		  $("#notification-user-"+message.sender).css({"background-color":"red","color":"white"});
    	   }
    	   $("#chat-conversation-"+message.sender).remove();
    	   
    	   $("#conversations").prepend("<div class='single-conversation' id='chat-conversation-"+message.sender+"'>"+
					"<div class='conversation-profile-picture'><div class='profile-picture'><img src='"+message.senderProfileUrl+"'></div></div>"+
					"<div class='conversation-content'>"+
						"<p align='left' style='overflow:hidden;'>"+message.message+" </p>"+
					"</div>"+
					"<div class='conversation-date'>Just Now</div>"+
				"</div>");
       });
        stompClient.subscribe('/queue/online', function (message) {
        	var messageBody = JSON.parse(message.body);
        	var user = messageBody.user;
        	var action = messageBody.action;
        	if(action == 'CONNECTED' && !currentOnlineUsers.get(user.username)){
        		currentOnlineUsers.set(user.username,user);
        		$("#online_users").append("<div id='"+user.username+"' class='users-online profile-picture' ><img src='"+user.profileUrl+"'><p class='notification-number' id='notification-user-"+user.username+"'>0</p></div>");
        	}else if( action== 'DISCONNECTED'){
        		currentOnlineUsers.delete(user.username);
        		$("#"+user.username).remove();
        	}
        	
        });
        
    });
    httpRequest.get("/users/connected",null,token,function(response){
		  var connectedusers = JSON.parse(response);
				 connectedusers.some(function(user){
					 if(currentUser.id != user.id){
						 currentOnlineUsers.set(user.username,user);
						 $("#online_users").append("<div id='"+user.username+"' class='users-online profile-picture'><img src='"+user.profileUrl+"'><p class='notification-number' id='notification-user-"+user.username+"'>0</p></div>");
					 }
			});
	  });
}
function send(){
	stompClient.send("/app/message", {}, JSON.stringify({'message': $('#chat-text-box').val()
    	,'receiver':currentChattingWithUser,'sender':currentUser.username}));
	 $("#chatbox_"+currentChattingWithUser).append("<div class='left-message chat-message' align='left'><div><p class='chat-message-text'>"+$('#chat-text-box').val()+"</p></div><p class='chat-message-time'>Just Now</p></div>");
		$("#chatbox_"+currentChattingWithUser).scrollTop(function() { return this.scrollHeight; });
		$("#chat-text-box").val('');
}
function prepareBox(selectedUser){
	var bucket=1;
	var params = new Map();
	params.set("receiver",selectedUser);
	params.set("bucket",bucket);
	httpRequest.get("/getMessages",params,token,function(response){
		var messages = "";
		response = JSON.parse(response);
		response.some(function(message){
			if(currentUser.username==message.sender){
				messages= messages +"<div class='left-message chat-message' align='left'><div><p class='chat-message-text'>"+message.message+"</p></div><p class='chat-message-time'>11.36 am</p></div>"
			}else{
				messages= messages +"<div class='right-message chat-message' align='right'><div><p class='chat-message-text'>"+message.message+"</p></div><p class='chat-message-time'>11.36 am</p></div>"
			}
		})
		if(currentChattingWithUser==selectedUser){
			$("#chatbox_"+selectedUser).prepend(messages);
		}else{
			$("#chatbox_"+currentChattingWithUser).remove();
			$(".chat-box").append("<div class='box chat-display-box' id='chatbox_"+selectedUser+"'>"+messages+"</div>");
	    	currentChattingWithUser = selectedUser;
		}
		$("#chatbox_"+selectedUser).scrollTop(function() { return this.scrollHeight; });
		$("#chatbox_"+selectedUser).on('scroll',function() {
			if(this.scrollTop==0&&!isConversationLoadComplete){
				prependMessages(selectedUser,++bucket);
			}

  		});
        	
	})
}

function prependMessages(selectedUser,bucket){
	var params = new Map();
	params.set("receiver",selectedUser);
	params.set("bucket",bucket);
	httpRequest.get("/getMessages",params,token,function(response){
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
	if(token!=""){
			httpRequest.get("/users/current",null,token,function(response){
				currentUser = JSON.parse(response);
				 connect();
					var downloadingImage = new Image();
					downloadingImage.onload = function(){
					 $("#nav-bar-profile-picture").attr("src",this.src);
					 $("#nav-bar-profile-picture").css({"display":"inline"});
					};
				downloadingImage.src = currentUser.profileUrl;
				 displayUserInformation(currentUser);
			});
	}else{
		window.location.href = env+"/views/login.html"
	}


	httpRequest.get("/pastConversations",null,token,function(response){
		response = JSON.parse(response);
		response.forEach(function(message){
			$("#conversations").append("<div class='single-conversation' id='chat-conversation-"+message.sender+"'>"+
					"<div class='conversation-profile-picture'><div class='profile-picture'><img src='"+message.profileUrl+"'></div></div>"+
					"<div class='conversation-content'>"+
						"<h5 align='left'><b>"+message.sender+"</b></h5>"+
						"<p align='left' style='overflow:hidden;'>"+message.message+" </p>"+
					"</div>"+
					"<div class='conversation-date'>"+message.daysAgo+"</div>"+
				"</div>");
		});
		
	});
	
	$("#chat-text-box").keypress(function(e){
		var key = e.which;
		if(key==13){
			$("#send_button").click();
			$("#chat-text-box").val('');
			return false;
		}
	});

	
	$("#online_users").on("click","div",function(event){
        var id = $(this).closest("div").prop("id");
    	var user = currentOnlineUsers.get(id);
    	$("#"+currentChattingWithUser).prop("disabled",false);
    	$("#"+user.username).prop("disabled",true);
    	displayUserInformation(user);
		$("#send_button").prop("disabled",false);
		$("#heading-name").html("<b>"+user.firstName+" "+user.lastName+"</b>");
        $("#heading-username").html(user.username);
        $("#notification-user-"+user.username).text(0);
  		$("#notification-user-"+user.username).css({"background-color":"transparent","color":"transparent"});
  		prepareBox(user.username);
    });
	 $("#logout").click(function(){
	    	document.cookie = 'token' + '=; expires=Thu, 01-Jan-70 00:00:01 GMT;';
	    	window.location.href = env+"/views/login.html"
	    });
});