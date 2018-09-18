var stompClient = null;
var currentOnlineUsers = new Array();
var load = function(){
	var cookie = getCookie();
	if(cookie!=""){
		console.log(cookie);
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.onreadystatechange = function(){
			 if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
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

function connect() {
	var token = getCookie();
    var socket = new SockJS(env+'/gs-guide-websocket?token='+token);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        
        stompClient.subscribe('/queue/online', function (message) {
        	var messageBody = JSON.parse(message.body);
        	var user = messageBody.user;
        	var action = messageBody.action;
        	if(action == 'CONNECTED' && currentOnlineUsers.indexOf(user.id)<=-1){
        		currentOnlineUsers.push(user.id);
        		$("#online_users").append("<div id='"+user.id+"' class='users-online profile-picture'><img src='"+user.profileUrl+"'></div>");
        	}else if( action== 'DISCONNECTED'){
        		currentOnlineUsers.pop(user.id);
        		$("#"+user.id).remove();
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
			 console.log(this.responseText);
			 
		 }
	 } 
	 	xmlHttp.open("GET",env+"/users/connected");
		xmlHttp.setRequestHeader("Authorization","Bearer "+token);
		xmlHttp.send(null);
}
$(function () {
	load();
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    
    $("#logout").click(function(){
    	document.cookie = 'token' + '=; expires=Thu, 01-Jan-70 00:00:01 GMT;';
    	window.location.href = env+"/views/login.html"
    });
});