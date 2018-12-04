var httpRequest = new HttpRequest();
var env = "http://localhost:8080";
var login = function(){
	var param = new Map();
	param.set("userName",$("#username").val());
	httpRequest.get("/public/users/login",param,null,function(response){
		if (response!==null){
	        document.cookie="token="+ response;
	        window.location.href = env+"/views/chat.html";
        }else {
        	$(".error-message").text(response);
        	$(".error-message").css({"color":"#b30000"});
        }
});
}