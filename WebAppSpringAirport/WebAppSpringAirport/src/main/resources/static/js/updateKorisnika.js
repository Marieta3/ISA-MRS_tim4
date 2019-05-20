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
				{	$.bootstrapGrowl("An error occured!", {
					  ele: 'body', // which element to append to
					  type: 'danger', // (null, 'info', 'danger', 'success')
					  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
					  align: 'right', // ('left', 'right', or 'center')
					  width: 'auto', // (integer, or 'auto')
					  delay: 2000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
					  allow_dismiss: false, // If true then will display a cross to close the popup.
					  stackup_spacing: 10 // spacing between consecutively stacked growls.
					});
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