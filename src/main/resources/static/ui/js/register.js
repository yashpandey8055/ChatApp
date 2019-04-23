var currentUser
const email_regex = '^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$'
const invalid_email_msg = "*Not a valid email."
const password_regex = '^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d]).{9,}$'
const phonenumber_regex = '^[0-9]{10}$'
	var validateField ={	
		userName : false,
		phoneNumber : false,
		email : false,
	
	setUserName(isSet){
		userName= isSet;
	},

	setPhoneNumber(isSet){
		phoneNumber= isSet;
	},
	
	setEmail(isSet){
		email= isSet;
	},
	
	checkValidation(){
		return userName&&email&&phoneNumber
	}
}
function register(){
    	var request = {
    			'username' :$('#userName').val(),
    			'firstName':$('#firstName').val(),
    			'lastName':$('#lastName').val(),
    			'bio':$('#bio').val(),
    			'password' :$('#password').val(),
    			'birthDate':$('#select-date').val(),
    			'birthYear':$('#select-year').val(),
    			'birthMonth':$('#select-month').val(),
    			'gender':$("#gender").val(),
    			'phoneNumber':$("#phoneNumber").val(),  
    			'email':$("#emailId").val()  
    	}
    	if(validateField.checkValidation()&&validate_fields()){
        	var xmlHttp = new XMLHttpRequest();
    		xmlHttp.onreadystatechange =function(){
    			if(xmlHttp.readyState == 4 && xmlHttp.status == 200){
    				var response = JSON.parse(this.responseText);
    				if(response.type==="Success"){
	    				document.cookie="token="+ response.data+"; path=/";
	    				logon(response.data);
    				}
    			}
    		}
    		xmlHttp.open("POST","/secure/users/register",true);
    		xmlHttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    		xmlHttp.send(JSON.stringify(request));
    	}

    	
    }
    
    function logon(token){
    	var xmlHttp = new XMLHttpRequest();
		xmlHttp.onreadystatechange =function(){
			if(xmlHttp.readyState == 4 && xmlHttp.status == 200){
				var response = JSON.parse(this.responseText);
				if(response.type==="Success"){
					document.location.href = "/ui/user?user="+response.data.userName;
				}
			}
		}
		xmlHttp.open("GET","/users/current",true);
		xmlHttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xmlHttp.setRequestHeader("Authorization","Bearer "+token);
		xmlHttp.withCredentials = true
		xmlHttp.send(null);
    }
    
   function fill_Date_and_year(){
	   	for(var i=1;i<=31;i++){
    		$(".select-date").append("<option>"+i+"</option>");
    		if(i<=12){
    			$(".select-month").append("<option>"+i+"</option>");
    		}
    	}
   } 
    function validate_fields(){
    	var d = new Date();
    	var validated = true;
    	var currentYear = d.getFullYear();
    	if(!$("#phoneNumber").val().match('^[0-9]{10}$')){
    		$("#phoneNumber").css({"border":"1px solid #b30000"})
    		$("#phoneNumber").parent().next().text()
    		$("#phoneNumber").parent().next().css({"color":"#b30000"})
    		validated = false;
    	}
    	var year = parseInt($('#select-year').val());
    	if(year<1900||year>=parseInt(currentYear)){
    		$("#select-year").css({"border":"1px solid #b30000"})
    		validated = false;
    	}else{
    		$("#select-year").css({"border":"1px solid #008080"})
    	}
    	if($('#select-date').val()=='Date'){
    		$("#select-date").css({"border":"1px solid #b30000"})
    		validated = false;
    	}
    	if($('#select-month').val()=='Month'){
    		$("#select-month").css({"border":"1px solid #b30000"})
    		validated = false;
    	}
 
    	validateRegexField('#emailId',email_regex,validated,invalid_email_msg)
    	validateRegexField('#password',password_regex,validated,'')
    	
    	validateEmptyField('#userName',validated)
    	validateEmptyField('#firstName',validated);
    	validateEmptyField('#lastName',validated);
    	validateEmptyField('#select-year',validated);
    	return validated;
    }
    
    function validateEmptyField(selector,flag){
    	if($(selector).val()==''){
    		$(selector).css({"border":"1px solid #b30000"})
    		flag = false;
    	}
    }
    
    function validateRegexField(selector,regex,flag,message){
    	if(!$(selector).val().match(regex)){
    		$(selector).next().text(message)
    		$(selector).next().css({"color":"#b30000"})
    		$(selector).css({"border":"1px solid #b30000"})
    		flag = false;
    	}else{
    		$(selector).css({"border":"1px solid #008080"})
    	}
    }
    
 
    $(function(){
    	fill_Date_and_year();
    	$(".form-control").on("focus",function(){
    		$(this).css({"border":"1px solid #46377b"})
    	})
    	$(".form-control").on("focusout",function(){
    		$(this).css({"border":"1px solid #ced4da"})
    	})

    	$(document).on("focusout","#userName",function(){
    		var param = new Map();
    		param.set("key","username");
    		param.set("value",$("#userName").val());
    		httpRequest.get("/secure/exist",param,function(response){
    			if (response=='true'){
    				validateField.setUserName(false);
    	    		$("#userName").css({"border":"1px solid #b30000"})
    	    		$("#userName").next().html("<small class='error-message-display'>Username already taken. Try another<small>")
    	        }else {
    				validateField.setUserName(true);
    				$("#register-button").attr({'disabled':false})
    	        	$("#userName").css({"border":"1px solid #008080"})
    	    		$("#userName").next().html("<small class='success-message-display'>valid UserName<small>");
    	        }
    			if(currentUser){
    			if (currentUser.userName !==$("#userName").val()&&response=='true'){
    				validateField.setUserName(false);
    	    		$("#userName").css({"border":"1px solid #b30000"})
    	    		$("#userName").next().html("<small class='error-message-display'>Username already taken. Try another<small>")
    	        }else {
    				validateField.setUserName(true);
    				$("#register-button").attr({'disabled':false})
    	        	$("#userName").css({"border":"1px solid #008080"})
    	    		$("#userName").next().html("<small class='success-message-display'>valid UserName<small>");
    	        }
    			}
    	});
    	})
    	
    	$(document).on("focusout","#emailId",function(){
    		var param = new Map();
    		param.set("key","email");
    		param.set("value",$("#emailId").val());
    		httpRequest.get("/secure/exist",param,function(response){
    			if (response=='true'){
    				validateField.setEmail(false);
    	    		$("#emailId").css({"border":"1px solid #b30000"})
    	    		$("#emailId").next().html("<small class='error-message-display'>Email Id Already Registered<small>")
    	        }else {
    				validateField.setEmail(true);
    	        	$("#emailId").css({"border":"1px solid #008080"})
    	    		$("#emailId").next().html("<small class='success-message-display'>valid email<small>");
    	        }
    			if(currentUser){
	    			if (currentUser.email !==$("#emailId").val()&&response=='true'){
	    				validateField.setEmail(false);
	    	    		$("#emailId").css({"border":"1px solid #b30000"})
	    	    		$("#emailId").next().html("<small class='error-message-display'>Email Id Already Registered<small>")
	    	        }else {
	    				validateField.setEmail(true);
	    	        	$("#emailId").css({"border":"1px solid #008080"})
	    	    		$("#emailId").next().html("<small class='success-message-display'>valid email<small>");
	    	        }
    			}
    	});
    	})
    	
    	$(document).on("focusout","#phoneNumber",function(){
    		var param = new Map();
    		param.set("key","phoneNumber");
    		param.set("value",$("#phoneNumber").val());
    		httpRequest.get("/secure/exist",param,function(response){
    			if (response=='true'){
    				validateField.setPhoneNumber(false);
    	    		$("#phoneNumber").css({"border":"1px solid #b30000"})
    	    		$("#phoneNumber").next().html("<small class='error-message-display'>Phone Number Already Registered<small>")
    	    		$("#phoneNumber").parent().next().html("<small class='error-message-display'>Phone Number Already Registered<small>")
    	        }else {
    				validateField.setPhoneNumber(true);
    	        	$("#phoneNumber").css({"border":"1px solid #008080"})
    	    		$("#phoneNumber").parent().next().html("<small class='success-message-display'>valid Phone Number<small>");
    	        	$("#phoneNumber").next().html("<small class='success-message-display'>valid Phone Number<small>");
    	        }
    			if(currentUser){
        			if (currentUser.phoneNumber !==parseInt($("#phoneNumber").val())&&response=='true'){
		    				validateField.setPhoneNumber(false);
		    	    		$("#phoneNumber").css({"border":"1px solid #b30000"})
		    	    		$("#phoneNumber").next().html("<small class='error-message-display'>Phone Number Already Registered<small>")
		    	        }else {
		    				validateField.setPhoneNumber(true);
		    	        	$("#phoneNumber").css({"border":"1px solid #008080"})
		    	    		$("#phoneNumber").parent().next().html("<small class='success-message-display'>valid Phone Number<small>");
		    	        	$("#phoneNumber").next().html("<small class='success-message-display'>valid Phone Number<small>");
		    	        }
        		}
    	});
    	})
    	
    })