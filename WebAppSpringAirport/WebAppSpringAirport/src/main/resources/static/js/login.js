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
			console.log("GRESKAAAAAA  ");
			alert(errorThrown);
		}
	});
})

function userToJSON(username, password){
	return JSON.stringify({
		"username":username,
		"password":password
	});
}

