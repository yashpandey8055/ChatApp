var register = function(){
			var xmlHttp = new XMLHttpRequest();
    	    xmlHttp.onreadystatechange = function() { 
    	        if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
    	        	document.getElementById("test").innerHTML =
    	        	      this.responseText;
    	        console.log("response is:"+this.responseText)
    	        document.cookie="token="+ this.responseText;
    	        window.location.href = "http://localhost:8080/views/index.html";
    	        }
    	    }
    	    var username = document.getElementById("username").value;
    	    var password = document.getElementById("password").value;
    	    xmlHttp.open("POST", "http://localhost:8080/public/users/login?username="+username+"&password="+password, true); // true for asynchronous 
    	    xmlHttp.send(null);
    	    
    	}