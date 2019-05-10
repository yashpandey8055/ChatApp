function update_password(){
		if($('#new_password').val()==$('#confirm_password').val()){
			if($("#new_password").val()&&!$('#new_password').val().match('^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d]).{9,}$')){
	    		$('#new_password').next().css({"color":"#b30000"})
	    		$("#new_password").css({"border":"1px solid #b30000"})
	    		$('#confirm_password').next().text("*Password should contain atleast one lower, one upper, one special character and should be minimum 9 characters long");
	    	}else{
	    		$('#confirm_password').next().text("");
	    		$("#new_password").css({"border":"1px solid #008080"})
				var request ={
					'oldPassword' :$('#old_password').val(),
	    			'newPassword':$('#new_password').val()
	    		}
				var xhr = new XMLHttpRequest();
				xhr.onreadystatechange = function(){
					 if (xhr.readyState == 4 && xhr.status == 200){
							$('.pop-up-box').remove();
							display_notification_popup("Password Changed Succesfully!");
					}else if(xhr.readyState == 4 &&xhr.status !== 200){
						$('#old_password').next().text("*Incorrect Password.");
					}
				}
				xhr.open("POST", "/users/changepwd");
				if(token){
					xhr.setRequestHeader("Authorization","Bearer "+token);
				}
				xhr.setRequestHeader("content-type","application/json");
				xhr.send(JSON.stringify(request));
	    	}
		}else{
			$('#confirm_password').next().text("*Password don't match");
		}
	}
		$("#profile-icon").on('click',function(){
			window.location.href = "/user?user="+currentUser.userName;
		});
		$("#message-icon").on('click',function(){
			window.location.href = "/chat";
		});
		$("#dashboard-icon").on('click',function(){
			window.location.href = "/dashboard";
		});
		$("#findfriends-icon").on('click',function(){
			window.location.href = "/findfriends";
		});
		$('.display-options-box').hide(); 
		
		$('#nav-bar-picture-icon').click(function(e) {                              
		   $('#options-pop-box').toggle();  
		});
		 $("#logout").click(function(){
		    	document.cookie = 'token' + '=; expires=Thu, 01-Jan-70 00:00:01 GMT;';
		    	window.location.href = "/ui/login"
		 });
		 $("#change_password").click(function(){
				$(".content-container").css({"opacity":"0.1"});
				$('.display-options-box').hide(); 
			 $("body").prepend("<div class='pop-up-box vertical-align' style='top:40%;width:40%;'>" +
				"<div align=right><button type='button' id='close_button' class='close' onclick='close_this(event)'><span aria-hidden='true'>&times;</span></button></div>"+
			"<form>"+
		"<div class='row-full'>"+
		"<div class='col'><label for='old_password'>Old Password</label>"+
	      "<input type='text' class='form-control' id='old_password' placeholder='Old Password'>"+
	      "<small style='color:#b30000'></small></div>"+
	    "</div>"+
		"<div class='row-full'>"+
		"<div class='col'>"+
		"<label for='new_password'>New Password</label>"+
	    " <input type='text' class='form-control' id='new_password' placeholder='New Password'>"+
		"</div>"+
	    "</div>"+
	    "<div class='row-full'>"+
	    "<div class='col'>"+
		"<label for='confirm_password'>Confirm Password</label>"+
   	 	"<input type='text' class='form-control' id='confirm_password' placeholder='Confirm Password'>"+
	      "<small style='color:#b30000'></small>"+
	    "</div>"+
   		 "</div>"+
	"</form>"+
	"<div style='width:100%;margin:auto;'><button class='btn app-btn btnfull' id='register-button' onclick='update_password()'>Update Password</button></div>");
		 });
		$('#notification-icon').click(function(e) {                              
			   $('#notification-pop-box').toggle(); 
			   $('#notification-nav-bar').text(0);
			   $('#notification-nav-bar').css({"background":"transparent","color":"transparent"});
			});