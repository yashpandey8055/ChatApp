function register(){
    	var request = {
    			'userName' :$('#userName').val(),
    			'firstName':$('#firstName').val(),
    			'lastName':$('#lastName').val(),
    			'bio':$('#bio').val(),
    			'password' :$('#password').val(),
    			'dob':$('#select-date').val()+"/"+$('#select-month').val()+"/"+$('#select-year').val(),
    			'yearOfBirth':$('#select-year').val(),
    			'gender':$("#gender").val(),
    			'phoneNumber':$("#phoneNumber").val(),  
    			'email':$("#email").val()  
    	}
    	var xmlHttp = new XMLHttpRequest();
		xmlHttp.onreadystatechange =function(){
			if(xmlHttp.readyState == 4 && xmlHttp.status == 200){
				document.cookie="token="+ this.responseText;
				logon(this.responseText);
			}
		}
		xmlHttp.open("POST",env+"/public/users/register",true);
		xmlHttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xmlHttp.send(JSON.stringify(request));
    	
    }
    
    function logon(token){
    	var xmlHttp = new XMLHttpRequest();
		xmlHttp.onreadystatechange =function(){
			if(xmlHttp.readyState == 4 && xmlHttp.status == 200){
				var response = JSON.parse(this.responseText);
				document.location.href = env+"/user?user="+response.userName;
			}
		}
		xmlHttp.open("GET",env+"/public/users/getUser?token="+token,true);
		xmlHttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xmlHttp.send(null);
    }
    $(function(){
    	for(var i=1;i<=31;i++){
    		$(".select-date").append("<option>"+i+"</option>");
    	}
    	for(var i=1;i<=12;i++){
    		$(".select-month").append("<option>"+i+"</option>");
    	}
    })