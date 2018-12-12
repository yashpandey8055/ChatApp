$(function () {
var downloadingImage = new Image();
				downloadingImage.onload = function(){
					var url = this.src;
						$("#post_1").attr("src",url).fadeIn(2000);

				};
	downloadingImage.src = "https://s3.ap-south-1.amazonaws.com/ketu-user-profile-pictures/test.jpg";
});