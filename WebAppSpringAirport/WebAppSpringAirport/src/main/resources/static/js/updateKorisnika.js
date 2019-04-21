/**
 * 
 */

var update_korisnika_url="api/users";

$(document).on('submit', "#updateKorisnikaForma", function(e){
	e.preventDefault();
	console.log("update korisnika");
	var id = $('#identifikator').val();
	var ime = $('#ime').val();
	var prezime = $('#prezime').val();
	var korisnickoIme=$('#korisnickoIme').val();
	var lozinka=$('#lozinka').val();
	var mail=$('#mail').val();
	console.log("aaaaaaaaa");
	console.log(id);
	console.log(ime);
	console.log("aaaaaaaaa");
	$.ajax({
		type:'PUT',
		url:update_korisnika_url+"/"+id,
		contentType:'application/json',
		dataType:'text',
		data:korisnikToJSON(id,ime, prezime, korisnickoIme, lozinka, mail),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			console.log("EVO MEEEEEE")
			window.location.replace("prikazKorisnika.html");
		}
			, error: function(XMLHttpRequest,textStatus, errorThrown) 
				{alert("Ne postoji korisnik sa datom ID-om!"+errorThrown);
			}
	});
});

function korisnikToJSON(id, ime, prezime, korisnickoIme, lozinka, mail){
	return JSON.stringify({
		"id":id,
		"ime":ime,
		"prezime":prezime,
		"korisnickoIme":korisnickoIme,
		"lozinka":lozinka,
		"mail":mail 
	});
}