

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
    			'email':$("#emailId").val()  
    	}
    	if(validate_fields()){
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
    
    function validate_fields(){
    	var d = new Date();
    	var validated = true;
    	var currentYear = d.getFullYear();
    	if(!$("#phoneNumber").val().match('^[0-9]{10}$')){
    		$("#phoneNumber").css({"border":"1px solid #b30000"})
    		$("#phoneNumber").parent().parent().append("<small class='error-message-display'>*Not valid Phone Number. Phone number is 10 digits.</small>")
    		validated = false;
    	}
    	var year = parseInt($('#select-year').val());
    	if(year<1900||year>=parseInt(currentYear)){
    		$("#select-year").parent().parent().parent().append("<small class='error-message-display'>*Hey, there is no way you are born in "+$('#select-year').val()+"</small>")
    		$("#select-year").css({"border":"1px solid #b30000"})
    		validated = false;
    	}
    	if(!$('#password').val().match('^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d]).{9,}$')){
    		$('#password').next().css({"color":"#b30000"})
    		$("#password").css({"border":"1px solid #b30000"})
    		validated = false;
    	}
    	if(!$('#emailId').val().match( '/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/')){
    		$('#emailId').next().text("*Not a valid email.")
    		$('#emailId').next().css({"color":"#b30000"})
    		$("#emailId").css({"border":"1px solid #b30000"})
    		validated = false;
    	}
    	if($('#select-date').val()=='Date'){
    		$("#select-date").css({"border":"1px solid #b30000"})
    		validated = false;
    	}
    	if($('#select-month').val()=='Month'){
    		$("#select-month").css({"border":"1px solid #b30000"})
    		validated = false;
    	}
       	if($('#userName').val()==''){
    		$("#userName").css({"border":"1px solid #b30000"})
    		validated = false;
    	}
       	if($('#firstName').val()==''){
    		$("#firstName").css({"border":"1px solid #b30000"})
    		validated = false;
    	}
       	if($('#lastName').val()==''){
    		$("#lastName").css({"border":"1px solid #b30000"})
    		validated = false;
    	}
       	if($('#select-year').val()==''){
    		$("#select-year").css({"border":"1px solid #b30000"})
    		validated = false;
    	}
    	return validated;
    }
    $(function(){
    	for(var i=1;i<=31;i++){
    		$(".select-date").append("<option>"+i+"</option>");
    	}
    	for(var i=1;i<=12;i++){
    		$(".select-month").append("<option>"+i+"</option>");
    	}
    })