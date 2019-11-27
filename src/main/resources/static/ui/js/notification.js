$(function(){
	var xhr = new XMLHttpRequest();
	   xhr.onreadystatechange = function(){
		   if (xhr.readyState == 4 && xhr.status == 200){
			   console.log(this.responseText);
			   var response = JSON.parse(this.responseText);
			   response.some(function(res){
				   $("#notification-box-page-display").prepend(add_notification(res));
			   })
		   }
	   }
	   
	   xhr.open("GET", "/notification/get");
		if(token){
			xhr.setRequestHeader("Authorization","Bearer "+token);
		}
		xhr.setRequestHeader("content-type","application/json");
		xhr.send(null);
})