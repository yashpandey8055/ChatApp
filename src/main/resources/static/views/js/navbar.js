$(function(){
	$('body').click(function(e) {                    
		   if(e.target == $('#nav-bar-profile-picture')[0] )
		   {
			   $(".display-options-box").toggle();              
		   }else{
			   $('.display-options-box').hide();  
		   }
		});
})