var httpRequest = new HttpRequest();
var en = "https://ketu.herokuapp.com"
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