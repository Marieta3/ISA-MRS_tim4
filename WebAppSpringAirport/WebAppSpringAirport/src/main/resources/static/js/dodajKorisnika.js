/**
 * 
 */

var dodaj_korisnika_url="api/users";

$(document).on('submit', "#dodajKorisnikaForma", function(e){
	e.preventDefault();
	console.log("dodavanje korisnika");
	var ime = $('#ime').val();
	var prezime = $('#prezime').val();
	var korisnickoIme=$('#korisnickoIme').val();
	var lozinka="$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra"; //sifra je 123
	var mail=$('#mail').val();
	var e = document.getElementById("ulogeDDL");
	var uloga = e.options[e.selectedIndex].value;
	console.log(korisnickoIme);
	console.log(lozinka);
	console.log(mail);
	console.log(uloga);
	$.ajax({
		type:'POST',
		url:dodaj_korisnika_url,
		contentType:'application/json',
		data:korisnikToJSON(ime, prezime, korisnickoIme, lozinka, mail, uloga),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			window.location.replace("prikazKorisnika.html");
		}
	});
})


function korisnikToJSON(ime,prezime,korisnickoIme, lozinka, mail, uloga){
	return JSON.stringify({
		"ime":ime,
		"prezime":prezime,
		"korisnickoIme":korisnickoIme,
		"lozinka":lozinka,
		"mail":mail,
		"uloga":uloga
	});
}