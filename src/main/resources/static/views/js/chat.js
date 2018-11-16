function HttpRequest(){
	var obj = {};
	
	obj.get = function(url,params,callback){
		
		if(params!==null){
			var requestParam = '?' ;
			params.forEach(function(value,key){
				requestParam = requestParam+key+'='+value+'&'
			});
			url = url+requestParam;
		}
		
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


var token = getCookie();
var stompClient = null;
var currentUser = null;
var currentChattingWithUser = null;
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
    	   if(currentChattingWithUser==message.receiver){
    		   $("#chatbox_"+selectedUser).prepend("<div class='left-message chat-message' align='left'><div><p class='chat-message-title'><b>"+message.sender+"</b></p><p class='chat-message-text'>"+message.message+"</p></div><p class='chat-message-time'>11.36 am</p></div>") 
    	   }
    	   else{
    		   
    		  var count = $("#notification-user-"+message.sender).text();
    		  $("#notification-user-"+message.sender).text(parseInt(count)+1);
    		  $("#notification-user-"+message.sender).css({"background-color":"red","color":"white"});
    	   }
       });
        stompClient.subscribe('/queue/online', function (message) {
        	var messageBody = JSON.parse(message.body);
        	var user = messageBody.user;
        	var action = messageBody.action;
        	if(action == 'CONNECTED' && !currentOnlineUsers.get(user.username)){
        		currentOnlineUsers.set(user.username,{"username":user.username,"firstName":user.firstName,"lastName":user.lastName});
        		$("#online_users").append("<div id='"+user.username+"' class='users-online profile-picture' ><img src='"+user.profileUrl+"'><p class='notification-number' id='notification-user-"+user.username+"'>0</p></div>");
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
						 $("#online_users").append("<div id='"+user.username+"' class='users-online profile-picture'><img src='"+user.profileUrl+"'><p class='notification-number' id='notification-user-"+user.username+"'>0</p></div>");
					 }
			});
	  });
}
function send(){
	stompClient.send("/app/message", {}, JSON.stringify({'message': $('#chat-text-box').val()
    	,'receiver':currentChattingWithUser,'sender':currentUser.username}));
	 $("#chatbox_"+currentChattingWithUser).append("<div class='left-message chat-message' align='left'><div><p class='chat-message-title'><b>You</b></p><p class='chat-message-text'>"+$('#chat-text-box').val()+"</p></div><p class='chat-message-time'>Just Now</p></div>");
}
function prepareBox(selectedUser){
	var params = new Map();
	params.set("receiver",selectedUser);
	httpRequest.get(env+"/getMessages",params,function(response){
		response = JSON.parse(response);
		var messages = "";
		response.forEach(function(message){
			if(currentUser.username==message.sender){
				messages= messages +"<div class='left-message chat-message' align='left'><div><p class='chat-message-title'><b>You</b></p><p class='chat-message-text'>"+message.message+"</p></div><p class='chat-message-time'>11.36 am</p></div>"
			}else{
				messages= messages +"<div class='right-message chat-message' align='left'><div><p class='chat-message-title'><b>"+selectedUser+"</b></p><p class='chat-message-text'>"+message.message+"</p></div><p class='chat-message-time'>11.36 am</p></div>"
			}
		})
		if(currentChattingWithUser==selectedUser){
			$("#chatbox_"+selectedUser).prepend(messages);
		}else{
			$("#chatbox_"+currentChattingWithUser).remove();
			$(".chat-box").append("<div class='box chat-display-box' id='chatbox_"+selectedUser+"'>"+messages+"</div>");
	    	currentChattingWithUser = selectedUser;
		}
	})
	
}

$(function () {
	if(token!=""){
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
        	$("#notification-user-"+user.username).text(0);
  		  $("#notification-user-"+user.username).css({"background-color":"transparent","color":"transparent"});
        	prepareBox(user.username);
    });
	 $("#logout").click(function(){
	    	document.cookie = 'token' + '=; expires=Thu, 01-Jan-70 00:00:01 GMT;';
	    	window.location.href = env+"/views/login.html"
	    });
});