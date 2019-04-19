/**
 * 
 */
/*
 * Milan: promenjena ruta koja se gadja prva kada se radi logovanje
 */
var users_url="/auth/login";

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
		dataType:'json',
		data:userToJSON(username,password),
		success:function(data){
			console.log(data.accessToken);
			/*
			 * Milan: Kada vam createAuthenticationToken metoda iz AuthenticationController klase vrati jwt token kao objekat UserTokenState iz domain paketa
			 * sacuvajte token u localStorage da biste ga kasnije slali kroz header u svim zahtevima
			 */
			localStorage.setItem("accessToken", data.accessToken);
			window.location.replace("index.html");
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

/*
 * Milan: Dodao sam logout cisto kao primer da kada se korisnik izloguje treba da se ukloni njegov token iz localStorage
 */
$(document).on('click', '.logout', function(e){
	e.preventDefault();
	localStorage.removeItem('accessToken');
    document.location.replace("/");
})



$(window).click(function(e){
	
	if(e.target==document.getElementById("id01")){
		$("#id01").css("display", "none");
	}else if(e.target==document.getElementById("id02")){
		$("#id02").css("display", "none");
	}
})