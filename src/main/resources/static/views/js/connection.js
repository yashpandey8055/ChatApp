var stompClient = null;
var user = null;
var userList = new Array();
var chatWithUser = null;
var chatStack = new Array();
var load = function(){
	var cookie = getCookie();
	if(cookie!=""){
		console.log(cookie);
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.onreadystatechange = function(){
			console.log("something:"+xmlHttp.readystate+" "+xmlHttp.status)
			 if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
				var json = JSON.parse(this.responseText);
				user = json.username;
				document.getElementById("username").innerHTML = user;
				//Automatic connect to Websocket
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
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
	var token = getCookie();
    var socket = new SockJS(env+'/gs-guide-websocket?token='+token);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
       //This is user specific queue. This will be used in the chat where a specific user want
        //to send message to specific user. This will receive the message display.
        stompClient.subscribe('/user/queue/message', function (message) {
        	var chatMessage = JSON.parse(message.body);
        		displayChatBox(chatMessage.sender,false);
        	showMessage(JSON.parse(message.body));
        });
        
        // This is a notification queue which receives the user who logged in and display 
        stompClient.subscribe('/queue/online', function (message) {
        	var userInfo= JSON.parse(message.body);
        	console.log("The details of Online user:"+JSON.stringify(userInfo));
        	if(userList.indexOf(userInfo.user)<=-1&&userInfo.action=='CONNECTED'){
        		userList.push(userInfo);
        	}else if(userInfo.action=='DISCONNECTED'){
        		userList.some(function(entry){
        			if(entry.username==userInfo.user.username){
        				userList.pop(entry);
        				return false;
        			}
        		});
        	}
        	addUserToOnlineUser(userInfo);
        });
        
    });
    request();
   
}
function request(){
	var token = getCookie();
	 var xmlHttp = new XMLHttpRequest();
	 xmlHttp.onreadystatechange = function(){
		 if(xmlHttp.readyState == 4 && xmlHttp.status == 200){
			 var list = JSON.parse(this.responseText);
			 list.forEach(function(entry){
				 var message = {'user':entry,'action':'CONNECTED'};
					 userList.push(message);
				
				addUserToOnlineUser(message);
			 });
		 }
	 } 
	 	xmlHttp.open("GET",env+"/users/connected");
		xmlHttp.setRequestHeader("Authorization","Bearer "+token);
		xmlHttp.send(null);
}
function addUserToOnlineUser(message){
	var onlineUser = message.user;
	var action = message.action;
	if(onlineUser){
		if(onlineUser.username!=user&&!ifAlreadyAdded(onlineUser.username)&&action=='CONNECTED'){
			$("#online_users").append("<li id="+onlineUser.username+">"
				+"<div class='user_profile' >"
					+"<img src='download.png' align='middle' class='profile_picture'/><br>"
					+"<p><b>"+onlineUser.username+"</b></p>"
					+"<p>My Bio</p>"
				+"</div>"+
				"</li>");
			}
			if(action=='DISCONNECTED'){
				$("#"+onlineUser.username).remove();
			}
	}
}
function ifAlreadyAdded(user){
	var parent = document.getElementById("online_users");
	var child = parent.firstChild
	while(child) {
		console.log(child.nodeValue);
		if(user==child.innerHTML&&child.nodeType == 1){
			return true;
		}
	    child = child.nextSibling;
	}
}
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}
function send(){
	
	stompClient.send("/app/message", {}, JSON.stringify({'message': $('#message_'+chatWithUser).val()
    	,'receiver':chatWithUser,'sender':user}));
    $("#chatbox_"+chatWithUser).append("<p align='right'>"+$('#message_'+chatWithUser).val()+"</p>");
}

function showMessage(message) {
	$("#chatbox_"+message.sender).append("<p align='left'>"+ message.message +"</p>");
}
function displayChatBox(id,userClickedList){
	userList.some(function(entry){
	 if(entry.user.username == id&&!($.inArray(id, chatStack ) > -1)){
		 if(document.getElementById("chat_"+entry.user.username)&&userClickedList){
			 $(".chatterBox").css("z-index",0);
			$("#chat_"+entry.user.username).css("z-index",1); 
		 }else if(!document.getElementById("chat_"+entry.user.username)) {
			 $("#chatarea").prepend(createChatBox(entry));
		 }
		 chatWithUser = id;
		 
	 }
});
}

function createChatBox(entry){
	return "<div id='chat_"+entry.user.username+"' class='chatterBox'>"+
			"<div class='chat_topbar'>"+
			"<div style='margin:0px;padding:5px;'>"+
				"<div style='float:left;display:inline-block;margin-left:5px;'><img src='download.png' align='middle' class='profile_picture'/></div>"+
				"<div style='margin-top:5px;margin-left:5px;display:inline-block'><h4 style='margin-top:2px;margin-bottom:0px;'>"+entry.user.firstName+" "+entry.user.lastName+"</h4><p>"+entry.user.username+"</p></div>"+
			"</div>"+
				"</div>"+
				"<hr>"+
				"<div class='chatbox' id='chatbox_"+entry.user.username+"'>"+
					
				"</div>"+
				"<div class='chattext'>"+
					"<input type='text' id='message_"+entry.user.username+"' name='chattext_"+entry.user.username+"' style='height:50%; width:100%'>"+
					"<button type='button' class='btn btn-primary' onclick='send()'>Send</button>"+
				"</div>"+
					"</div>"
}
$(function () {
	load();
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#online_users").on("click","li",function(event){
        var id = $(this).closest("li").prop("id");
        displayChatBox(id,true);
    });
    
    $("#logout").click(function(){
    	document.cookie = 'token' + '=; expires=Thu, 01-Jan-70 00:00:01 GMT;';
    	window.location.href = env+"/views/login.html"
    });
});