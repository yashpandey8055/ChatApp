
var token = getCookie();
var currentUser = null;
const httpRequest = new HttpRequest();
var env = 'http://localhost:8080'
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
			url = url+'?'+requestParam;
		}
		
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.onreadystatechange = function(){
			 if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
				 callback(this.responseText);
			}else if(xmlHttp.readyState == 4 &&xmlHttp.status !== 200){
				callback();
			}
		}
		
		xmlHttp.open("GET",url);
			xmlHttp.setRequestHeader("Authorization","Bearer "+token);
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
		xmlHttp.open("POST",url,true);
		if(token){
			xmlHttp.setRequestHeader("Authorization","Bearer "+token);
		}
		xmlHttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xmlHttp.send(JSON.stringify(request));
	}
	obj.put = function(url,request,callback){
		
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.onreadystatechange =function(){
			if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
				 callback(this.responseText);
			}else if(xmlHttp.readyState == 4 &&xmlHttp.status !== 200){
				callback(this.responseText);
			}
		}
		xmlHttp.open("PUT",url,true);
		if(token){
			xmlHttp.setRequestHeader("Authorization","Bearer "+token);
		}
		xmlHttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xmlHttp.send(JSON.stringify(request));
	}
	
	return obj;
}
function follow(user){
	if($("#follow-user-btn").text()=='Connect'){
		httpRequest.get("/user/connect/"+user,null,function(response){
			console.log(response);
			$("#follow-user-btn").removeClass("purple-button").addClass("simple-btn");
			$("#follow-user-btn").html("Requested. Undo?");
		});
	}else if($("#follow-user-btn").text()=='Disconnect'||$("#follow-user-btn").text()=='Requested. Undo?'){
		httpRequest.get("/user/disconnect/"+user,null,function(response){
			console.log(response);
			$("#follow-user-btn").removeClass("simple-btn").addClass("purple-button");
			$("#follow-user-btn").html("Connect");
		});
	}
	$("#follow-user-btn").html("<img height=20px width=20px src='/ui/images/loading.gif'>")
}

function acceptReject(user,action,event){
	if(action==='Accept'){
		httpRequest.get("/user/connect/accept/"+user,null,function(response){
			$(event.target).text('Connected');
			$(event.target).parent().next().remove();
			$(event.target).attr('disabled','true');
			$(event.target).css({'width':'100px','background-image': 'linear-gradient(to left ,white,whitesmoke)','color':'black','border': '1px solid #46377b'});
		});
	}else if(action==='Reject'){
		httpRequest.get("/user/disconnect/reject/"+user,null,function(response){
			$(event.target).text('Rejected')
			$(event.target).parent().parent().children()[0].remove();
			$(event.target).attr('disabled','true');
			$(event.target).css({'width':'100px','background-image': 'linear-gradient(to left ,white,whitesmoke)','color':'black','border': '1px solid #46377b'});
		
		});
	}
}

function like(event){
	var params = new Map();
	params.set("postId", $(event.target).parents('[id]:last').attr('id'));
	if($(event.target).attr('alt')== 'like'){
		if($(event.target).parent().parent().next().find('.like-count-number').text()){
			$(event.target).parent().parent().next().find('.like-count-number').text(
					parseInt($(event.target).parent().parent().next().find('.like-count-number').text())+1);
			$(event.target).attr('src','/ui/images/liked.png');
			$(event.target).children().attr('alt','unlike');
		}else{
			$(event.target).parent().next().find('.like-count-number').text(
				parseInt($(event.target).parent().next().find('.like-count-number').text())+1);
			$(event.target).children().attr('src','/ui/images/liked.png');
		}
		$(event.target).attr('alt','unlike'); 
		httpRequest.get("/like/post",params,function(response){
		});
	}else{
		if($(event.target).parent().parent().next().find('.like-count-number').text()){
			$(event.target).parent().parent().next().find('.like-count-number').text(
					parseInt($(event.target).parent().parent().next().find('.like-count-number').text())-1);
			$(event.target).attr('src','/ui/images/like.png');
			$(event.target).children().attr('alt','like');
		}else{
			$(event.target).parent().next().find('.like-count-number').text(
				parseInt($(event.target).parent().next().find('.like-count-number').text())-1);
			$(event.target).children().attr('src','/ui/images/like.png');
		}
		$(event.target).attr('alt','like'); 
		httpRequest.get("/unlike/post",params,function(response){
		});
	}
}
function commentlike(event){
	var params = new Map();
	params.set("postId", $(event.target).parents('.status-comment-display-box').attr('id'));
	
	if($(event.target).attr('alt')== 'like'||$(event.target).child().attr('alt')){
		$(event.target).attr('alt','unlike'); 
		$(event.target).children().attr('alt','like')
		$(event.target).attr('src','/ui/images/heart-like.png');
		httpRequest.get("/unlike/comment",params,function(response){
		});
	}else{
		$(event.target).attr('alt','like'); 
		$(event.target).children().attr('alt','like'); 
		$(event.target).attr('src','/ui/images/heart.png');
		httpRequest.get("/like/comment",params,function(response){
		});
	}
}
function open_post(postId){
	document.location.href = "/post?postId="+postId;
}
function add_notification(response){
	var notification= "<li onclick= open_post('"+response.postId+"') class='unread-notification' id='notification-"+response.postId+"-"+response.type+"'>"
			+"<div  id='nav-bar-picture-icon' style='position:relative;width: 40px;' >"
			+"<img id='nav-bar-profile-picture'  alt=''"
				+" src='"+response.pictureUrls[0]+"' style='border-radius:50%;position:absolute;display: inline;'>";
			if(response.pictureUrls[1]){
			notification = notification+"	<img id='nav-bar-profile-picture'  alt=''"
			 				+" src='"+response.pictureUrls[1]+"' style='border-radius:50%;position:absolute;display: inline;margin-left:-10px;'>"
			}
			notification = notification + "</div>"
			+"<div style='display: flex;flex-direction:column;margin-left:5px;'><h5><b>"+response.message+"</b></h5><h6>"+response.timeAgo+"</h6></div>"
		+"</li>";
		if($("#notification-"+response.postId+"-"+response.type)){
			$("#notification-"+response.postId).remove()
		}
		$("#notification-box-display").prepend(notification);
}
function _websocket_connect(){
    var socket = new SockJS(env+'/ketu-socket?token='+token);
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
      // stompClient.subscribe('/user/queue/notification', function (response){
    //	   add_notification(JSON.parse(response.body));
    //	   $("#notification-nav-bar").text( parseInt($('#notification-nav-bar').text())+1);
    //	   $("#notification-nav-bar").css({"background-color":"red","color":"white"});
     //  });
    });
}
function load_CurrentUser(callback){
	if(token!=""){
		httpRequest.get("/users/current",null,function(response){
			_websocket_connect();
			callback();
			httpRequest.get("/get/notification",null,function(response){
				response = JSON.parse(response);
				response.some(function(res){
					add_notification(res);
				})
			})
			currentUser = JSON.parse(response).data;
				var downloadingImage = new Image();
				downloadingImage.onload = function(){
				 $("#nav-bar-profile-picture").attr("src",this.src);
				 $("#nav-bar-profile-picture").css({"display":"inline"});
				};
				downloadingImage.src = currentUser.profileUrl;
			});
	}else{
		document.location.href = "/ui/login";
	}
}
$(function(){
	$(".navbar-nav").load("/ui/navbar.html");
	
	load_CurrentUser(function(){});
	
})