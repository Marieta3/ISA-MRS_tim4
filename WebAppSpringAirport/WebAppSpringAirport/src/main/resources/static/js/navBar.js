/**
 * 
 */

$(document).ready(function(){
	var role=localStorage.getItem("uloga");
	console.log(role);
	if(role==null){
		console.log($("#nav-bar"));
		console.log($("#lista1"));
		var li1=$('<li><button class="loginBtn">Login</button></li>');
		console.log(li1);
		$("#nav-bar").append(li1);
		$("#nav-bar").append('<li><button class="registerBtn">Register</button></li>');
	}else{
		//ovo dugme ce biti disabled dok se ne promeni lozinka prvi put
		$("#nav-bar").append('<li><button class="profileBtn" id="chgPswBtn">Profile</button></li>');
		//ovo dugme ce jedino biti enabled 
		$("#nav-bar").append('<li><button class="logout" >Logout</button></li>');
	}
})

$(document).on('click', '.logout', function(e){
	e.preventDefault();
	localStorage.removeItem('accessToken');
	localStorage.removeItem('uloga');
	localStorage.removeItem("prvaPromena");
    document.location.replace("/");
})

$(document).on('click', '.whoami', function(e){
	e.preventDefault();
	console.log("uloga: "+localStorage.getItem("uloga"));
})

$(document).on('click', '.registerBtn', function(e){
	e.preventDefault();
	console.log("register");
	$("#id02").css("display", "block");
	$("body").addClass("modal-open");
})

$(document).on('click', '.loginBtn', function(e){
	e.preventDefault();
	console.log("login");
	$("#id01").css("display", "block");
	$("body").addClass("modal-open");
})



$(document).on('click', '.profileBtn', function(e){
	e.preventDefault();
	uloga=localStorage.getItem("uloga");
	if(uloga!=null){
		window.location.replace("profil"+uloga+".html");
	}else{
		$("#id01").css("display", "block");
		$("body").addClass("modal-open");
	}
})