var stompClient = null;
var user = null;
var load = function(){
	console.log("windows loaded");
	var cookie = getCookie();
	if(cookie!=""){
		console.log(cookie);
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.onreadystatechange = function(){
			console.log("something:"+xmlHttp.readystate+" "+xmlHttp.status)
			 if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
				console.log("something:"+this.responseText)
				var json = JSON.parse(this.responseText);
				user = json.username;
				document.getElementById("username").innerHTML = user;
				//Automatic connect to Websocket
				connect();
			    
			}
		}
		xmlHttp.open("GET","http://localhost:8080/users/current");
		xmlHttp.setRequestHeader("Authorization","Bearer "+cookie);
		xmlHttp.send(null);
	}else{
		console.log(cookie);
		window.location.href = "http://localhost:8080/views/login.html"
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
	};
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
    var socket = new SockJS('/gs-guide-websocket?token='+token);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
       //This is user specific queue. This will be used in the chat where a specific user want
        //to send message to specific user. This will receive the message display.
        stompClient.subscribe('/user/queue/queue1', function (message) {
        	var onlineUser = JSON.parse(message.body).online;
        	console.log(message.body)
        	if(user!=onlineUser&&onlineUser&&!ifAlreadyAdded(onlineUser)){
        		 $("#online_users").append("<li>" +JSON.parse(message.body).online+"</li>");
        	}else{
        		showMessage(JSON.parse(message.body));
        	}
        });
        
        // This is a notification queue which receives the user who logged in and display 
        stompClient.subscribe('/queue/online', function (message) {
        	var onlineUser = JSON.parse(message.body).online;
        	
        	// tell the logged in user that this user is also online
        	stompClient.send("/app/message", {}, JSON.stringify({'online': user,'receiver':JSON.parse(message.body).online}));
        	if(onlineUser!=user&&onlineUser&&!ifAlreadyAdded(onlineUser)){
        		$("#online_users").append("<li>" +JSON.parse(message.body).online+"</li>");
        	}
        });
        
        //Send the notification to all user that current user is online
        stompClient.send("/app/online", {}, JSON.stringify({'online': user}));
    });
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

function sendName() {
    stompClient.send("/app/message", {}, JSON.stringify({'message': $("#message").val()
    	,'receiver':$('#receiver').val(),'sender':user}));
    $("#chat").append("<tr><td>" +user+" : "+ $("#message").val() + "</td></tr>");
}

function showMessage(message) {
    $("#chat").append("<tr><td>" +message.sender+" : "+ message.message + "</td></tr>");
}

$(function () {
	
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { sendName(); });
});