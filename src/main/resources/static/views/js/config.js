var env = "http://localhost:8080"
	
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
			}
		}
		
		xmlHttp.open("GET",url);
		xmlHttp.setRequestHeader("Authorization","Bearer "+token);
		xmlHttp.send(null);
	}
	return obj;
}