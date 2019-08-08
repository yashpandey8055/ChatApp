function display_notification_popup(text){
	$(".notification-bar").append("<p class='notification-content' style=''><b>"+text+"</b></p>")
	$(".notification-bar").stop().animate({height:40,width:$(".notification-content").width()+20,padding:10}, "fast");
	
	setTimeout(function(){
		$(".notification-content").remove();
		$(".notification-bar").stop().animate({height:0,width:0,padding:0}, "fast");
		},2000)
	
}

function getCookie(){
	 var name = "token=";
	    var decodedCookie = decodeURIComponent(document.cookie);
	    var ca = decodedCookie.split(';');
	    for(var i = 0; i < ca.length; i++) {
	        var c = ca[i];
	        while (c.charAt(0) == ' ') {
	            c = c.substring(1);
	        }
	        if (c.indexOf(name) == 0) {
	            return c.substring(name.length, c.length);
	        }
	    }
	    return "";
}
