$(function(){
var xhr = new XMLHttpRequest();
				xhr.onreadystatechange = function(){
					 if (xhr.readyState == 4 && xhr.status == 200){
						var response = JSON.parse(this.responseText);
						var connection_request = '';
						var constant_accept = 'Accept';
						var constant_reject= 'Reject';
						if(response.data.length>0){
							response.data.some(function(resp){
								connection_request = connection_request +
								"<li class='unread-notification  connection_items'>"+
								"<div class='navbar-element-icon' id='nav-bar-picture-icon'><img height=100% id='nav-bar-profile-picture' src='"+resp.requesterProfileUrl+"' width=100% alt=''></div>"+
								"<div class='connection-display-name'><p><b>"+resp.requester+"</b></p></div>"+
								"<div style='display:flex;flex-direction:row'>"+
									"<div class='connection-display-btn'>"+
									"<button class='btn btnfull' onclick='acceptReject(\""+resp.requester+"\",\""+constant_accept+"\",event)'>Accept</button>"+
									"</div>"+
									"<div class='connection-display-btn'>"+
									"<button class='btn btnfull connection-white-btn' onclick='acceptReject(\""+resp.requester+"\",\""+constant_reject+"\",event)'>Reject</button>"+
									"</div>"+
								"</div>"+
							"</li>" +
							"<hr class='connection-bottom-line connection_items'>"
								
							}); 
							$("#connections-box-page-display").empty();
							$("#connections-box-page-display").prepend("<li class='unread-notification'>"+
									"<p><b><a href='/connection/requests'>See More Requests</a></b></p>"+
												 "</li>")
							 $("#connections-box-page-display").prepend(connection_request);
						}else{
							$("#connections-box-page-display").empty();
							 $("#connections-box-page-display").prepend("<li class='unread-notification'>"+
				"<p><b>No Requests</b></p>"+
							 "</li>")
						}
						
					}
				}
				xhr.open("GET", "/user/connectionrequests");
				if(token){
					xhr.setRequestHeader("Authorization","Bearer "+token);
				}
				xhr.setRequestHeader("content-type","application/json");
				xhr.send(null);
})