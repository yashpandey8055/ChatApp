var stompClient = null;

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
				document.getElementById("username").innerHTML = json.username;
				document.getElementById("auth-token").innerHTML = json.id;
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
        stompClient.subscribe('/user/queue/queue1', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/message", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
	
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});