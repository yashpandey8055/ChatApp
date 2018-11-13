var cookie = getCookie();
function HttpRequest(){
	var obj = {};
	
	obj.get = function(url,params){
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.onreadystatechange = function(){
			 if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
				 return this.responseText;
			}
		}
		xmlHttp.open("GET",url);
		xmlHttp.setRequestHeader("Authorization","Bearer "+cookie);
		xmlHttp.send(null);
	}
	return obj;
}
var load = function(){
	if(cookie!=""){
		var httpRequest = new httpRequest();
		var currentUser = JSON.parse(httpRequest.get(env+"/users/current",null));
		console.log(JSON.stringify(currentUser));
	}else{
		window.location.href = env+"/views/login.html"
	}
};

$(function () {
	load();
});