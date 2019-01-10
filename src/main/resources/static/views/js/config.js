var env = "http://localhost:8080"
var token = getCookie();
var currentUser = null;
var currentChattingWithUser = null;
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
function HttpRequest(){
	var obj = {};
	
	//Get Request
	obj.get = function(url,params,callback){
		
		if(params!==null){
			var requestParam = '' ;
			params.forEach(function(value,key){
				requestParam = requestParam+key+'='+value+'&'
			});
			url = env+url+'?'+requestParam;
		}
		
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.onreadystatechange = function(){
			 if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
				 callback(this.responseText);
			}else if(xmlHttp.readyState == 4 &&xmlHttp.status !== 200){
				callback(this.responseText);
			}
		}
		
		xmlHttp.open("GET",url);
		if(token){
			xmlHttp.setRequestHeader("Authorization","Bearer "+token);
		}
		xmlHttp.send(null);
	}
	
	//Post Request
	obj.post = function(url,request,callback){
		
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.onreadystatechange =function(){
			if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
				 callback(this.responseText);
			}else if(xmlHttp.readyState == 4 &&xmlHttp.status !== 200){
				callback(this.responseText);
			}
		}
		xmlHttp.open("POST",env+url,true);
		if(token){
			xmlHttp.setRequestHeader("Authorization","Bearer "+token);
		}
		xmlHttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xmlHttp.send(JSON.stringify(request));
	}
	
	
	return obj;
}
function follow(){
	console.log($("#follow-user-btn").text())
	if($("#follow-user-btn").text()=='Follow'){
		httpRequest.get("/users/follow/"+currentChattingWithUser,null,function(response){
			console.log(response);
			$("#follow-user-btn").html("Unfollow");
		});
	}else if($("#follow-user-btn").text()=='Unfollow'){
		httpRequest.get("/users/unfollow/"+currentChattingWithUser,null,function(response){
			console.log(response);
			$("#follow-user-btn").html("Follow");
		});
	}
	$("#follow-user-btn").html("<img height=20px width=20px src='/views/images/loading.gif'>")
}

function like(event){
	var params = new Map();
	params.set("postId", $(event.target).parents('[id]:last').attr('id'));
	if($(event.target).attr('alt')== 'like'){
		$(event.target).parent().parent().next().find('.like-count-number').text(
				parseInt($(event.target).parent().parent().next().find('.like-count-number').text())+1);
		$(event.target).attr('alt','unlike'); 
		$(event.target).attr('src','/views/images/liked.png');
		httpRequest.get("/posts/like",params,function(response){
		});
	}else{
		$(event.target).parent().parent().next().find('.like-count-number').text(
				parseInt($(event.target).parent().parent().next().find('.like-count-number').text())-1);
		$(event.target).attr('alt','like'); 
		$(event.target).attr('src','/views/images/like.png');
		httpRequest.get("/posts/unlike",params,function(response){
		});
	}
}
function commentlike(event){
	var params = new Map();
	params.set("postId", $(event.target).parents('.status-comment-display-box').attr('id'));
	
	if($(event.target).attr('alt')== 'like'){
		$(event.target).attr('alt','unlike'); 
		$(event.target).attr('src','/views/images/heart-like.png');
		httpRequest.get("/comment/like",params,function(response){
			console.log(response);
		});
	}else{
		$(event.target).attr('alt','like'); 
		$(event.target).attr('src','/views/images/heart.png');
		httpRequest.get("/comment/unlike",params,function(response){
			console.log(response);
		});
	}
}
function _websocket_connect(){
    var socket = new SockJS(env+'/gs-guide-websocket?token='+token);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
       stompClient.subscribe('/user/queue/notification', function (response){
    	   
       });
}
$(function(){
	$(".navbar-nav").load("/views/navbar.html");
	
	$("#nav-bar-picture-icon").on('click',function(){
		console.log('clicked');
	})
	if(token!=""){
		httpRequest.get("/users/current",null,function(response){
			_websocket_connect();
			currentUser = JSON.parse(response);
				var downloadingImage = new Image();
				downloadingImage.onload = function(){
				 $("#nav-bar-profile-picture").attr("src",this.src);
				 $("#nav-bar-profile-picture").css({"display":"inline"});
				};
				downloadingImage.src = currentUser.profileUrl;
			});
	}else{
		document.location.href = env+"/views/login.html";
	}
})