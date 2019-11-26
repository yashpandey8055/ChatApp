
$(function () {
$("#hamburger-icon").on('click',function(){
			$("#sidebar").toggle(function(){
				$("#findfriends-sidebar-icon").on('click',function(){
					console.log(1);
				});
				
			});
		});
});
		