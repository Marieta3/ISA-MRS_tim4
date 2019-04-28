/**
 * 
 */
/*
 * Milan: promenjena ruta koja se gadja prva kada se radi logovanje
 */
var users_url="/auth/login";
var register_url="api/register";
uloga="";

//findAllRent();


function findAllAvio(){
	$.ajax({
		type:'GET',
		url:'api/avioKompanije',
		dataType:'json',
		success:renderAvio
	})
}

function findAllHotels(){
	$.ajax({
		type:'GET',
		url:'api/hotels',
		dataType:'json',
		success:renderHoteli
	})
}

function findAllRent(){
	$.ajax({
		type:'GET',
		url:'api/rentACars',
		dataType:'json',
		success:renderRent
	})
}

function renderAvio(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	$.each(list, function(index, avio){
		var li=$('<li data-target="#myCarousel1" data-slide-to="'+index+'"></li>');
		$('#lista1').append(li);
		
		var div1=$('<div class="item"></div>');
		div1.append('<img src="slike/Airplane-1.png" alt="'+avio.naziv+'" style="width:40%;">');
		var div2=$('<div class="carousel-caption"></div>');
		div2.append('<h3>'+avio.naziv+'</h3>');
		div2.append('<p>'+avio.opis+'</br>'+avio.adresa+'</p>');
		div1.append(div2);
		
		$('.container1 .carousel-inner').append(div1);
	})
	$('#lista1').find('li:eq(0)').attr("class", "active");
	$('.container1 .carousel-inner').find('div:eq(0)').attr("class", "item active");
	$('.container1').attr("style", "background-color:black;");
}

function renderHoteli(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	$.each(list, function(index, hotel){
		var li=$('<li data-target="#myCarousel2" data-slide-to="'+index+'"></li>');
		$('#lista2').append(li);
		
		var div1=$('<div class="item"></div>');
		div1.append('<img src="slike/hotel.jpg" alt="'+hotel.naziv+'" style="width:20%;">');
		var div2=$('<div class="carousel-caption"></div>');
		div2.append('<h3>'+hotel.naziv+'</h3>');
		div2.append('<p>'+hotel.opis+'</br>'+hotel.adresa+'</p>');
		div1.append(div2);
		
		$('.container2 .carousel-inner').append(div1);
	})
	$('#lista2').find('li:eq(0)').attr("class", "active");
	$('.container2 .carousel-inner').find('div:eq(0)').attr("class", "item active");
	$('.container2').attr("style", "background-color:black;");
}

function renderRent(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	$.each(list, function(index, rent){
		var li=$('<li data-target="#myCarousel1" data-slide-to="'+index+'"></li>');
		$('#lista1').append(li);
		
		var div1=$('<div class="item"></div>');
		div1.append('<img src="slike/Airplane-1.png" alt="'+rent.naziv+'" style="width:100%;">');
		var div2=$('<div class="carousel-caption"></div>');
		div2.append('<h3>'+rent.naziv+'</h3>');
		div2.append('<p>'+rent.opis+'</br>'+rent.adresa+'</p>');
		div1.append(div2);
		
		$('.container1 .carousel-inner').append(div1);
	})
	$('#lista1').find('li:eq(0)').attr("class", "active");
	$('.container1 .carousel-inner').find('div:eq(0)').attr("class", "item active");
	$('.container1').attr("style", "background-color:black;");
}

$(document).on('submit', ".modal-content1", function(e){
	e.preventDefault();
	//console.log("login uspesan");
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
			console.log("hello")
			/*
			 * Milan: Kada vam createAuthenticationToken metoda iz AuthenticationController klase vrati jwt token kao objekat UserTokenState iz domain paketa
			 * sacuvajte token u localStorage da biste ga kasnije slali kroz header u svim zahtevima
			 */
			localStorage.setItem("accessToken", data.accessToken);
			
			//window.location.replace("index.html");
			$.ajax({
				type:'GET',
				url:'api/whoami',
				beforeSend: function(request) {
		            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
		        },
		        success:function(data){
		        	console.log("get role: "+data.authorities[0].authority);
		        	console.log(data.enabled==0);
		        	console.log(data);
		        	//alert(data.ulogovanPrviPut);
		        	
		        	if(data.enabled){
		        		localStorage.setItem("uloga", data.authorities[0].authority);
	        			uloga= localStorage.getItem("uloga");
	        			console.log(data.UlogovanPrviPut);
	        			console.log(data.ulogovanPrviPut);
		        		if(data.ulogovanPrviPut){
		        			window.location.replace("profil"+uloga+".html");
		        		}else{
		        			localStorage.setItem("prvaPromena", "prvaPromena");
		        			window.location.replace("prvaPromenaLozinke.html");
		        		}
		        	}else{
		        		localStorage.removeItem("accessToken");
		        		alert("Check your e-mail for verification!");
		        	}
		        }
		        	
		        
			})
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

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

//za registraciju korisnika modal
$(document).on('submit', '.modal-content2', function(e){
	e.preventDefault();
	var username = $('#username1').val();
	var password = $('#password1').val();
	var rptpassword = $('#passwordrpt').val();
	var firstname = $('#firstname').val();
	var lastname = $('#lastname').val();
	var email = $('#email').val();
	
	console.log(username);
	console.log(password);
	console.log(rptpassword);
	console.log(firstname);
	console.log(lastname);
	console.log(email);
	
	if  (!validateEmail(email)){
		alert("Bad email format");
		return;
	}
	if (password !== rptpassword){
		alert('Error! The passwords are not identical');
		return;
	}

	$.ajax({
		type:"POST",
		url:register_url,
		contentType:'application/json',
		data:registeringUserToJSON(username,password,firstname,lastname,email),
		success:function(data){
			window.location.replace("thanksForRegistration.html");
		},error:function(XMLHttpRequest,textStatus, errorThrown){
			console.log("GRESKAAAAAA  ");
			alert(errorThrown);
		}	
	//provera da li vec postoji po korisnickom imenu
	
	});
	console.log("submitovan register");
})

function registeringUserToJSON(username,password,firstname,lastname,email){
		return JSON.stringify({
			"korisnickoIme":username,
			"lozinka":password,
			"ime":firstname,
			"prezime":lastname,
			"mail":email
		});
}

$(document).on('click', '.close', function(e){
	$("#id01").css("display", "none");
	$("#id02").css("display", "none");
	$("body").removeClass("modal-open");
})


function getUserRole(){
	uloga=localStorage.getItem("uloga");
}

$(window).click(function(e){
	
	if(e.target==document.getElementById("id01")){
		$("#id01").css("display", "none");
		$("body").removeClass("modal-open");
	}else if(e.target==document.getElementById("id02")){
		$("#id02").css("display", "none");
		$("body").removeClass("modal-open");
	}
	
})