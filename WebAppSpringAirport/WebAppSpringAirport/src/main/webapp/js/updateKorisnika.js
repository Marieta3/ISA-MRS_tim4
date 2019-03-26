/**
 * 
 */

var update_korisnika_url="api/users";

$(document).on('submit', "#updateKorisnikaForma", function(e){
	e.preventDefault();
	console.log("update korisnika");
	var id = $('#identifikator').val();
	var korisnickoIme=$('#korisnickoIme').val();
	var lozinka=$('#lozinka').val();
	var mail=$('#mail').val();
	console.log("aaaaaaaaa");
	console.log(id);
	console.log("aaaaaaaaa");
	$.ajax({
		type:'PUT',
		url:update_korisnika_url+"/"+id,
		contentType:'application/json',
		dataType:'text',
		data:hotelToJSON(id,korisnickoIme, lozinka, mail),
		success:function(data){
			console.log(data); 
			console.log("EVO MEEEEEE")
			window.location.replace("prikazKorisnika.html");
		}
			, error: function(XMLHttpRequest,textStatus, errorThrown) 
				{alert("ERRORR!"+errorThrown);
			}
	});
});

function hotelToJSON(id,korisnickoIme, lozinka, mail){
	return JSON.stringify({
		"id":id,
		"korisnickoIme":korisnickoIme,
		"lozinka":lozinka,
		"mail":mail 
	});
}