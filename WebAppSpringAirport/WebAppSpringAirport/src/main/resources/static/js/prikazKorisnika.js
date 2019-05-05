/**
 * 
 */
uloga="";

$(document).ready(function(){
	uloga=localStorage.getItem("uloga");
	if(uloga=="ROLE_ADMIN"){
		$("#nav-bar").append('<li><button class="dodajKorisnikaBtn" onclick="otvoriModal(\'id03\')">New Admin</button></li>');
	}
});
findAll();
function findAll(){
	$.ajax({
		type:'GET',
		url:'api/users',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:renderKorisnici
	})
}

function renderKorisnici(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	uloga=localStorage.getItem("uloga");
	if(uloga=="ROLE_ADMIN"){
		var th_nbsp=$('<th colspan="2">&nbsp;</th>');
		$('#prikazKorisnikaTabela').find('tr:eq(0)').append(th_nbsp);
	}
	$("#prikazKorisnikaTabela").find("tr:gt(0)").remove();
	$("#prikazKorisnikaTabela").find("th:gt(6)").remove();
	$.each(list, function(index, korisnik){
		var tr=$('<tr id="korisnik_' + korisnik.id + '"></tr>');
		console.log(korisnik.ime);
		console.log(korisnik.authorities);
        var prom = korisnik.authorities[0].authority;
        var ul="";
		if (korisnik.slika == null){
			korisnik.slika = "../slike/avatar.png";
		}
        if (prom == "ROLE_ADMIN"){
            ul = "SYSTEM ADMIN";
        } else if(prom == "ROLE_USER") {
            ul = "REGISTERED USER"
        } else if(prom == "ROLE_RENT") {
            ul = "ADMIN RENT-A-CAR";
        } else if (prom == "ROLE_HOTEL") {
            ul = "ADMIN HOTEL";
        } else {
            ul = "ADMIN AVIOCOMPANY";
        }
		tr.append('<td align="center" width=100px height=100px>'+ '<div id="divKorisnik">' +
				'<img src=" ' + korisnik.slika +' " id="imgProfilnaKorisnici"> ' + '</div>' +
				'</td>'+ '<td align="center">' + korisnik.ime + '</td>'+ '<td align="center">' + 
				korisnik.prezime + '</td>'+'<td align="center">' + korisnik.korisnickoIme + '</td>' + '<td>'
				+ korisnik.mail + '</td>' + '<td align="center">' + ul + '</td>');
		
		if (uloga == "ROLE_ADMIN") {
			var formaObrisi = $('<form id="formaObrisi"></form>');
			formaObrisi.append('<input type="hidden" value="' + korisnik.id + '">');
			formaObrisi.append('<input id="hiddenKorisnik" type="hidden" value="' + korisnik.ime+" "+korisnik.prezime + ", "+korisnik.korisnickoIme+'">');
			formaObrisi.append('<input type="submit" value="Delete" onclick="otvoriModal(\'id02\')">');
			var td = $('<td></td>');
			td.append(formaObrisi);
			tr.append(td);
		
		}
		
		$('#prikazKorisnikaTabela').append(tr);
		
	})
}


$(document).on('submit', '#formaObrisi', function(e) {
	e.preventDefault();
	var id = $(this).find('input[type=hidden]').val();
	var naziv=$(this).find("#hiddenKorisnik").val();
	console.log(id);
	$("#identifikator").val(id);
	$("#imePrezimeUsername").text(naziv+"?");
	

})

$(document).on('submit', ".modal-content2", function(e){
	e.preventDefault();
	var id=$("#identifikator").val();
	$.ajax({
		type : 'DELETE',
		url : '/api/users/' + id,
		/*
		 * Milan: drugi nacin da token posaljete u zahtevu kroz ajax poziv kroz
		 * header
		 */
		headers : {
			Authorization : "Bearer " + localStorage.getItem("accessToken")
		},
		success : function() {
			console.log('blaa');
			$('#korisnik_' + id).remove();
			$("#id02").css("display", "none");
			$("body").removeClass("modal-open");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("okdosdkaasdd");
			alert(errorThrown);
		}
	})
	
})

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

$(document).on('submit', ".modal-content3", function(e){
	e.preventDefault();
	console.log("dodavanje admina");
	var ime = $('#firstName').val();
	var prezime = $('#lastName').val();
	var korisnickoIme=$('#userName').val();
	var lozinka="$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra"; //sifra je 123
	var mail=$('#email').val();
	if  (!validateEmail(mail)){
		alert("Bad email format");
		return;
	}
	var e = document.getElementById("ulogeDDL");
	var uloga = e.options[e.selectedIndex].value;
	var adminOf = $("#adminOfSel option:selected").val();

	$.ajax({
		type:'POST',
		url:"api/users",
		contentType:'application/json',
		dataType:'json',
		data:korisnikToJSONadd(ime,prezime,korisnickoIme, lozinka, mail, uloga, adminOf),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			window.location.replace("prikazKorisnika.html");
		}
	});
})
function korisnikToJSONadd(ime,prezime,korisnickoIme, lozinka, mail, uloga, adminOf){
	return JSON.stringify({
		"ime":ime,
		"prezime":prezime,
		"korisnickoIme":korisnickoIme,
		"lozinka":lozinka,
		"mail":mail,
		"uloga":uloga,
		"adminOf":adminOf
	});
}



$(document).ready(function(){
	//If parent option is changed
	$("#ulogeDDL").change(function() {
	        var parent = $(this).val(); //get option value from parent
	       
	        switch(parent){ //using switch compare selected option and populate child
	              case 'avio':
	                renderAvioOptions();
	                break;
	              case 'rent':
	            	renderRentOptions();
	                break;             
	              case 'hotel':
	            	renderHotelOptions();
	                break;
	              case 'sis':
	            	renderSisOptions();
	                break;
	            default: //default child option is blank
	                $("#child_selection").html('');  
	                break;
	           }
	});
	
	function renderSisOptions()
	{
	    $("#adminOfSel").html(""); //reset child options
	    $("#adminOfSel").prop('disabled', true);
        $("#adminOfSel").append("<option value=System>System</option>");
	    
	}
	function renderAvioOptions()
	{
	    $("#adminOfSel").html(""); //reset child options
	    $("#adminOfSel").prop('disabled', false);
	    $.ajax({
			type : 'GET',
			url : 'api/avioKompanije',
			dataType : 'json',
			/*
			 * Milan: jedan nacin da token koji ste dobili prilikom logina posaljete
			 * kroz ajax zahtev kroz header
			 */
			beforeSend : function(request) {
				request.setRequestHeader("Authorization", "Bearer "
						+ localStorage.getItem("accessToken"));
			},
			success : function (data){
				var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
				$.each(list, function(index, avio) {
					$("#adminOfSel").append("<option value=\"avio_"+avio.id+"\">"+avio.naziv+"</option>");
					console.log("avio_" + avio.id);
				})
			}
		})
	    
	}
	function renderRentOptions()
	{
	    $("#adminOfSel").html(""); //reset child options
	    $("#adminOfSel").prop('disabled', false);
	    $.ajax({
			type : 'GET',
			url : 'api/rentACars',
			dataType : 'json',
			/*
			 * Milan: jedan nacin da token koji ste dobili prilikom logina posaljete
			 * kroz ajax zahtev kroz header
			 */
			beforeSend : function(request) {
				request.setRequestHeader("Authorization", "Bearer "
						+ localStorage.getItem("accessToken"));
			},
			success : function (data){
				var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
				$.each(list, function(index, rent) {
					$("#adminOfSel").append("<option value=\"rent_"+rent.id+"\">"+rent.naziv+"</option>");
					console.log("rent_" + rent.id);
				})
			}
		})
	    
	}
	function renderHotelOptions()
	{
	    $("#adminOfSel").html(""); //reset child options
	    $("#adminOfSel").prop('disabled', false);
	    $.ajax({
			type : 'GET',
			url : 'api/hotels',
			dataType : 'json',
			/*
			 * Milan: jedan nacin da token koji ste dobili prilikom logina posaljete
			 * kroz ajax zahtev kroz header
			 */
			beforeSend : function(request) {
				request.setRequestHeader("Authorization", "Bearer "
						+ localStorage.getItem("accessToken"));
			},
			success : function (data){
				var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
				$.each(list, function(index, hotel) {
					$("#adminOfSel").append("<option value=\"hotel_"+hotel.id+"\">"+hotel.naziv+"</option>");
					console.log("hotel_" + hotel.id);
				})
			}
		})    
	}
})

