var httpRequest = new HttpRequest();
var something = function(){
			var xmlHttp = new XMLHttpRequest();
    	    xmlHttp.onreadystatechange = function() { 
    	        if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
    	        	document.getElementById("test").innerHTML =
    	        	      this.responseText;
    	       
    	        document.cookie="token="+ this.responseText;
    	        window.location.href = env+"/views/chat.html";
    	        }else if(xmlHttp.readyState == 4 &&xmlHttp.status == 404){
    	        	document.getElementById("test").innerHTML =
  	        	      this.responseText;
    	        }
    	    }
    	    var username = document.getElementById("username").value;
    	    var password = document.getElementById("password").value;
    	    xmlHttp.open("GET", env+"/public/users/login?userName="+username, true); // true for asynchronous 
    	    xmlHttp.send(null);
    	    
    	}

var login = function(){
	var param = new Map();
	param.set("userName",$("#username").val());
	httpRequest.get(env+"/public/users/login",param,null,function(response){
		if (response!==null){
	        document.cookie="token="+ response;
	        window.location.href = env+"/views/chat.html";
        }else {
        	$(".error-message").text("UserName/Password does not match.");
        	$(".error-message").css({"color":"#b30000"});
        }
});
}