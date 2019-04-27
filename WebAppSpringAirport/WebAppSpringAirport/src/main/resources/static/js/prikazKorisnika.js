/**
 * 
 */
uloga="";

$(document).ready(function(){
	uloga=localStorage.getItem("uloga");
	if(uloga=="ROLE_ADMIN"){
		$("#nav-bar").append('<li><button class="dodajKorisnikaBtn">New Admin</button></li>');
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
			formaObrisi.append('<input type="submit" value="Delete">');
			var td = $('<td></td>');
			td.append(formaObrisi);
			tr.append(td);
		
		}
		
		$('#prikazKorisnikaTabela').append(tr);
		
	})
}

$(document).on('click', '.dodajKorisnikaBtn', function(e){
	e.preventDefault();
	console.log("dodaj korisnika btn");
	$("#id03").css("display", "block");
	$("body").addClass("modal-open");
})

$(document).on('submit', '#formaObrisiggg', function(e) {
	e.preventDefault();
	var id = $(this).find('input[type=hidden]').val();
	var naziv=$(this).find("#hiddenKorisnik").val();
	console.log(id);
	$("#id02").css("display", "block");
	$("body").addClass("modal-open");
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

$(document).on('submit', ".modal-content3", function(e){
	e.preventDefault();
	console.log("dodavanje admina");
	var ime = $('#firstName').val();
	var prezime = $('#lastName').val();
	var korisnickoIme=$('#userName').val();
	var lozinka="$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra"; //sifra je 123
	var mail=$('#email').val();
	var e = document.getElementById("ulogeDDL");
	var uloga = e.options[e.selectedIndex].value;

	$.ajax({
		type:'POST',
		url:"api/users",
		contentType:'application/json',
		dataType:'json',
		data:korisnikToJSONadd(ime,prezime,korisnickoIme, lozinka, mail, uloga),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			//$("#id03").css("display", "none");
			//$("body").removeClass("modal-open");
			window.location.replace("prikazKorisnika.html");
		}
	});
})
function korisnikToJSONadd(ime,prezime,korisnickoIme, lozinka, mail, uloga){
	return JSON.stringify({
		"ime":ime,
		"prezime":prezime,
		"korisnickoIme":korisnickoIme,
		"lozinka":lozinka,
		"mail":mail,
		"uloga":uloga
	});
}

$(window).click(function(e){
	
	if(e.target==document.getElementById("id01")){
		$("#id01").css("display", "none");
		$("body").removeClass("modal-open");
	}else if(e.target==document.getElementById("id02")){
		$("#id02").css("display", "none");
		$("body").removeClass("modal-open");
	}else if(e.target==document.getElementById("id03")){
		$("#id03").css("display", "none");
		$("body").removeClass("modal-open");
	}
	
})

$(document).on('click', '.close', function(e){
	$("#id01").css("display", "none");
	$("#id02").css("display", "none");
	$("#id03").css("display", "none");
	$("body").removeClass("modal-open");
})
