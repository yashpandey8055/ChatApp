var env = "http://localhost:8080"
var token = getCookie();
var currentUser = null;
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
	
	obj.get = function(url,params,token,callback){
		
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
	return obj;
}

$(function(){
	$('.display-options-box').hide();  
	$('#nav-bar-picture-icon').click(function(e) {                              
	   $('.display-options-box').toggle();  
	});
	if(token!=""){
		httpRequest.get("/users/current",null,token,function(response){
			currentUser = JSON.parse(response);
				var downloadingImage = new Image();
				downloadingImage.onload = function(){
				 $("#nav-bar-profile-picture").attr("src",this.src);
				 $("#nav-bar-profile-picture").css({"display":"inline"});
				};
				downloadingImage.src = currentUser.profileUrl;
				displayUserInformation(currentUser);
			});
	}
})