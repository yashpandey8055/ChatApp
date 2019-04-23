
const ERROR = "Not found";
const ERROR_MESSAGE = "Username/password incorrect"
const httpRequest = new HttpRequest();
var login = function(){
	var param = new Map();
	param.set("userName",$("#username").val());
	param.set("password",$("#password").val());
	httpRequest.get("/public/users/login",param,function(response){
		if (response!==ERROR){
	        document.cookie="token="+ response;
	        window.location.href = "/dashboard";
        }else {
        	$(".error-message").html("<p>"+ERROR_MESSAGE+"</p>" );
        	$(".error-message").css({"color":"#b30000"});
        }
});
}

function HttpRequest(){
	var obj = {};
	
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
				callback(this.responseText);
			}
		}
		
		xmlHttp.open("GET",url);
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
		xmlHttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xmlHttp.send(JSON.stringify(request));
	}
	
	//Post Request
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
		xmlHttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xmlHttp.send(JSON.stringify(request));
	}
	
	return obj;
}
