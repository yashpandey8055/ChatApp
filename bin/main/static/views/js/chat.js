var token = getCookie();
var stompClient = null;
var currentUser = null;
var currentChattingWithUser = null;
var currentOnlineUsers = new Map();
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
function HttpRequest(){
	var obj = {};
	
	obj.get = function(url,params,callback){
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.onreadystatechange = function(){
			 if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
				 callback(this.responseText);
			}
		}
		xmlHttp.open("GET",url);
		xmlHttp.setRequestHeader("Authorization","Bearer "+token);
		xmlHttp.send(null);
	}
	return obj;
}
function connect() {
	var httpRequest = new HttpRequest();
    var socket = new SockJS(env+'/gs-guide-websocket?token='+token);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
       stompClient.subscribe('/user/queue/message', function (message){
        	
       });
        stompClient.subscribe('/queue/online', function (message) {
        	var messageBody = JSON.parse(message.body);
        	var user = messageBody.user;
        	var action = messageBody.action;
        	if(action == 'CONNECTED' && !currentOnlineUsers.get(user.username)){
        		currentOnlineUsers.set(user.username,{"username":user.username,"firstName":user.firstName,"lastName":user.lastName});
        		$("#online_users").append("<div id='"+user.username+"' class='users-online profile-picture' ><img src='"+user.profileUrl+"'></div>");
        	}else if( action== 'DISCONNECTED'){
        		currentOnlineUsers.delete(user.username);
        		$("#"+user.username).remove();
        	}
        	
        });
        
    });
    httpRequest.get(env+"/users/connected",null,function(response){
		  var connectedusers = JSON.parse(response);
				 connectedusers.some(function(user){
					 if(currentUser.id != user.id){
						 currentOnlineUsers.set(user.username,{"username":user.username,"firstName":user.firstName,"lastName":user.lastName});
						 $("#online_users").append("<div id='"+user.id+"' class='users-online profile-picture'><img src='"+user.profileUrl+"'></div>");
					 }
			});
	  });
}
function prepareBox(currentChattingWithUser){
	$(".chat-box").append("<div class='box chat-display-box' id='chatbox_"+id+"'>"+
					"<div class='left-message chat-message' align='left'><div><p class='chat-message-title'><b>Abhishek</b></p><p class='chat-message-text'>Hello How are you</p></div><p class='chat-message-time'>11.36 am</p></div>"+
					"<div class='right-message chat-message' align='left'><div><p class='chat-message-title'><b>Yash</b></p><p class='chat-message-text'>Seems your browser doesn't support Javascript! Websocket relies on Javascript being"+
			    "enabled. Please enable"+
			   "Javascript and reload this page!</p></div><p class='chat-message-time'>11.36 am</p></div>"+
				
				"</div>")
}

$(function () {
	if(token!=""){
		var httpRequest = new HttpRequest();
			httpRequest.get(env+"/users/current",null,function(response){
				currentUser = JSON.parse(response);
				 connect();
			});
	}else{
		window.location.href = env+"/views/login.html"
	}
	
	$("#online_users").on("click","div",function(event){
        var id = $(this).closest("div").prop("id");
        	var user = currentOnlineUsers.get(id);
        	currentChattingWithUser = user.username;
        	prepareBox(currentChattingWithUser);
    });
});