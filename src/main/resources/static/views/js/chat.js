function HttpRequest(){
	var obj = {};
	
	obj.post = function(url,request){
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.onreadystatechange = function(){
			 if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
				 return this.responseText;
			}
		}
		xmlHttp.open("GET",env+"/users/current");
		xmlHttp.setRequestHeader("Authorization","Bearer "+cookie);
		xmlHttp.send(null);
	}
	return obj;
}
var load = function(){
	var cookie = getCookie();
	if(cookie!=""){
		console.log(cookie);
		env+"/users/current"
		"Authorization","Bearer "+cookie

	
	}else{
		window.location.href = env+"/views/login.html"
	}
};