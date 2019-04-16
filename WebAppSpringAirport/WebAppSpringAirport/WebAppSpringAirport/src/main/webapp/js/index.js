/**
 * 
 */
var users_url="api/usersDTO";

$(document).on('submit', ".modal-content1", function(e){
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
			window.location.replace("/prikazHotela.html");
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




$(document).on('submit', '.modal-content2', function(e){
	e.preventDefault();
	
	console.log("submitovan register");
})



$(document).on('click', '.loginBtn', function(e){
	e.preventDefault();
	console.log("login");
	$("#id01").css("display", "block");
})

$(document).on('click', '.registerBtn', function(e){
	e.preventDefault();
	console.log("register");
	$("#id02").css("display", "block");
})

$(document).on('click', '.close', function(e){
	$("#id01").css("display", "none");
	$("#id02").css("display", "none");
})



$(window).click(function(e){
	
	if(e.target==document.getElementById("id01")){
		$("#id01").css("display", "none");
	}else if(e.target==document.getElementById("id02")){
		$("#id02").css("display", "none");
	}
})