
var stompClient = null;
var currentUser;
var currentOnlineUsers = new Map();


function Stack(){
	var obj = {};
	var array = [];
	obj.append = function(username) {
		if(hasElement(username)){
		    array.push(username);
			$('.chat-stack').append("<li id=chatelement_'"+username+"'><div class='box chat-display-box' id='chatbox_"+username+"'></div></li>");
		}
	};
	obj.prepend = function(username){
		if(hasElement(username)){
			for(var i =array.length-1;i>0;i--){
				array[i+1] = array[i];
			}
			array[0] = username;
			$('.chat-stack').prepend("<li id=chatelement_'"+username+"'><div class='box chat-display-box' id='chatbox_"+username+"'></div></li>");
		}
	}  
	obj.hasElement = function(username){
		for(var i=0;i<array.length-1;i++){
			if(array[i]==username){
				return true;
			}
		}
		return false;
	}
	obj.switchToFirst = function(username){
		for(var i=0;i<array.length-1;i++){
			if(array[i]==username){
				array[i] = array[0];
				array[0] = username;
				document.getElementById(username)
			}
		}
	}
	return obj;
}
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
	}
function connect() {
	var token = getCookie();
    var socket = new SockJS(env+'/gs-guide-websocket?token='+token);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        
        stompClient.subscribe('/user/queue/message', function (message){
        	if(document.getElementById('chatelement_'+id)){
        		$('.chat-stack').prepend("<li id=chatelement_'"+id+"'><div class='box chat-display-box' id='chatbox_"+id+"'></div></li>");
        	}
        }
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
   request();
}
function request(){
	var token = getCookie();
	 var xmlHttp = new XMLHttpRequest();
	 xmlHttp.onreadystatechange = function(){
		 if(xmlHttp.readyState == 4 && xmlHttp.status == 200){
			 var connectedusers = JSON.parse(this.responseText);
			 	connectedusers.some(function(user){
				 if(currentUser.username != user.username){
					 currentOnlineUsers.set(user.username,{"username":user.username,"firstName":user.firstName,"lastName":user.lastName});
					 $("#online_users").append("<div id='"+user.username+"' class='users-online profile-picture'><img src='"+user.profileUrl+"'></div>");
				 }
				});
		 }
	 } 
	 	xmlHttp.open("GET",env+"/users/connected");
		xmlHttp.setRequestHeader("Authorization","Bearer "+token);
		xmlHttp.send(null);
}

function addChatBox(id){
	if(document.getElementById('chatelement_'+id)){
		$('.chat-stack').append("<li id=chatelement_'"+id+"'><div class='box chat-display-box' id='chatbox_"+id+"'></div></li>");
	}
}
$(function () {
	load();
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#online_users").on("click","div",function(event){
        var id = $(this).closest("div").prop("id");
        	var user = currentOnlineUsers.get(id);
        	addChatBox(user.username);
    });
    $("#logout").click(function(){
    	document.cookie = 'token' + '=; expires=Thu, 01-Jan-70 00:00:01 GMT;';
    	window.location.href = env+"/views/login.html"
    });
});