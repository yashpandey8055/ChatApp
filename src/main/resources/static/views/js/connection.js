var stompClient = null;
var currentUser;
var currentChattingWithUser;
var chatStack = new Array();
var currentOnlineUsers = new Map();
var load = function(){
	var cookie = getCookie();
	if(cookie!=""){
		console.log(cookie);
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.onreadystatechange = function(){
			 if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
				 currentUser = JSON.parse(this.responseText);
				connect();
			}
		}
		xmlHttp.open("GET",env+"/users/current");
		xmlHttp.setRequestHeader("Authorization","Bearer "+cookie);
		xmlHttp.send(null);
	}else{
		window.location.href = env+"/views/login.html"
	}
};
	
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

function send(){
	stompClient.send("/app/message", {}, JSON.stringify({'message': $('#chat-text-box').val()
    	,'receiver':currentChattingWithUser,'sender':currentUser.username}));
	 $("#chatbox_"+currentChattingWithUser).append("<div class='left-message chat-message' align='left'><div><p class='chat-message-title'><b>"+currentUser.firstName+"</b></p><p class='chat-message-text'>"+$('#chat-text-box').val()+"</p></div><p class='chat-message-time'>Just Now</p></div>");
}
function connect() {
	var token = getCookie();
    var socket = new SockJS(env+'/gs-guide-websocket?token='+token);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        
        stompClient.subscribe('/user/queue/message', function (message){
        	chatStack.push()
        	var messageBody = JSON.parse(message.body);
        	addChatBox(messageBody.sender);
        })
        stompClient.subscribe('/queue/online', function (message) {
        	var messageBody = JSON.parse(message.body);
        	var user = messageBody.user;
        	var action = messageBody.action;
        	if(action == 'CONNECTED' && !currentOnlineUsers.get(user.username)){
        		currentOnlineUsers.set(user.id,{"username":user.username,"firstName":user.firstName,"lastName":user.lastName});
        		$("#online_users").append("<div id='"+user.username+"' class='users-online profile-picture' ><img src='"+user.profileUrl+"'></div>");
        	}else if( action== 'DISCONNECTED'){
        		currentOnlineUsers.delete(user.username);
        		$("#"+user.username).remove();
        	}
        	
        });
        
    });
   request();
}
function request(){
	var token = getCookie();
	 var xmlHttp = new XMLHttpRequest();
	 xmlHttp.onreadystatechange = function(){
		 if(xmlHttp.readyState == 4 && xmlHttp.status == 200){
			 var connectedusers = JSON.parse(this.responseText);
			 connectedusers.some(function(user){
				 if(currentUser.id != user.id){
					 currentOnlineUsers.set(user.username,{"username":user.username,"firstName":user.firstName,"lastName":user.lastName});
					 $("#online_users").append("<div id='"+user.id+"' class='users-online profile-picture'><img src='"+user.profileUrl+"'></div>");
				 }
				});
		 }
	 } 
	 	xmlHttp.open("GET",env+"/users/connected");
		xmlHttp.setRequestHeader("Authorization","Bearer "+token);
		xmlHttp.send(null);
}

function addChatBox(id){
	$('.chat-stack').append("<li id='"+id+"'>"+
				"<div class='box chat-display-box' id='chatbox_"+id+"'>"+
					"<div class='left-message chat-message' align='left'><div><p class='chat-message-title'><b>Abhishek</b></p><p class='chat-message-text'>Hello How are you</p></div><p class='chat-message-time'>11.36 am</p></div>"+
					"<div class='right-message chat-message' align='left'><div><p class='chat-message-title'><b>Yash</b></p><p class='chat-message-text'>Seems your browser doesn't support Javascript! Websocket relies on Javascript being"+
			    "enabled. Please enable"+
			   "Javascript and reload this page!</p></div><p class='chat-message-time'>11.36 am</p></div>"+
				
				"</div>"+
				"</li>")
}
$(function () {
	load();
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $(".chat-close-button").click(function(){
    	chatStack.pop($('.chat-stack li:last-child').attr("id"));
    	var next = chatStack[chatStack.length - 1];
    	var user = currentOnlineUsers.get(next);
    	if(user){
    		currentChattingWithUser = user.username;
	    	$("#heading-name").html("<b>"+user.firstName+" "+user.lastName+"</b>");
	    	$("#heading-username").text(user.username);
    	}else{
    		currentChattingWithUser = null;
    		$("#heading-name").html("<b></b>");
	    	$("#heading-username").text("");
    	}
	    	$('.chat-stack li:last-child').remove();
    	
    });
    $("#online_users").on("click","div",function(event){
        var id = $(this).closest("div").prop("id");
        if(chatStack.indexOf(id)<=-1){
        	var user = currentOnlineUsers.get(id);
        	currentChattingWithUser = user.username;
        	$("#heading-name").html("<b>"+user.firstName+" "+user.lastName+"</b>");
        	$("#heading-username").text(user.username);
        	addChatBox(user.username);
        	chatStack.push(user.username);
        }
       
    });
    $("#logout").click(function(){
    	document.cookie = 'token' + '=; expires=Thu, 01-Jan-70 00:00:01 GMT;';
    	window.location.href = env+"/views/login.html"
    });
});