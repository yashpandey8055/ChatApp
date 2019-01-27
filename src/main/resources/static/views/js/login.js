var env = "http://localhost:8080"
const httpRequest = new HttpRequest();
var login = function(){
	var param = new Map();
	param.set("userName",$("#username").val());
	httpRequest.get("/public/users/login",param,null,function(response){
		if (response!==null){
	        document.cookie="token="+ response;
	        window.location.href = env+"/chat";
        }else {
        	$(".error-message").text(response);
        	$(".error-message").css({"color":"#b30000"});
        }
});
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
