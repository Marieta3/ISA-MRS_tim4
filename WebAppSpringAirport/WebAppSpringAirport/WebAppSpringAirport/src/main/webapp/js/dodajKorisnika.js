/**
 * 
 */

var dodaj_korisnika_url="api/users";

$(document).on('submit', "#dodajKorisnikaForma", function(e){
	e.preventDefault();
	console.log("dodavanje korisnika");
	var korisnickoIme=$('#korisnickoIme').val();
	var lozinka=$('#lozinka').val();
	var mail=$('#mail').val();
	console.log(korisnickoIme);
	console.log(lozinka);
	console.log(mail);
	$.ajax({
		type:'POST',
		url:dodaj_korisnika_url,
		contentType:'application/json',
		dataType:'text',
		data:korisnikToJSON(korisnickoIme, lozinka, mail),
		success:function(data){
			console.log(data); 
			window.location.replace("prikazKorisnika.html");
		}
	});
})


function korisnikToJSON(korisnickoIme, lozinka, mail){
	return JSON.stringify({
		"korisnickoIme":korisnickoIme,
		"lozinka":lozinka,
		"mail":mail 
	});
}