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
	$("#follow-user-btn").html("<img height=20px width=20px src='/views/images/loading-3.gif'>")
}

$(function(){
	$("#profile-icon").on('click',function(){
		window.location.href = env+"/user";
	});
	$("#message-icon").on('click',function(){
		window.location.href = env+"/chat";
	});
	$('.display-options-box').hide();  
	
	$('#nav-bar-picture-icon').click(function(e) {                              
	   $('.display-options-box').toggle();  
	});

	if(token!=""){
		httpRequest.get("/users/current",null,function(response){
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