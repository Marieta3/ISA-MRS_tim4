/**
 * 
 */
var users_url="/api/usersDTO";

$(document).on('submit', "#loginForm", function(e){
	e.preventDefault();
	console.log("login uspesan");
	var username = $('#username').val();
	var password = $('#password').val();
	console.log(password);
	console.log(username);
	$.ajax({
		type:'POST',
		url:users_url,
		contentType:'application/json',
		dataType:'text',
		data:userToJSON(username,password),
		success:function(data){
			console.log(data); 
			window.location.replace("prikazHotela.html");
		},error:function(XMLHttpRequest,textStatus, errorThrown){
			//console.log("GRESKAAAAAA  ");
			//alert(errorThrown);
			$.bootstrapGrowl("Error while logging in!", {
				  ele: 'body', // which element to append to
				  type: 'danger', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'center', // ('left', 'right', or 'center')
				  width: 400, // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		}
	});
})

function userToJSON(username, password){
	return JSON.stringify({
		"username":username,
		"password":password
	});
}

