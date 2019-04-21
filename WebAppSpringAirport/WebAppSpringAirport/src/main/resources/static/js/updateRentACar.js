/**
 * 
 */

var update_rentacar_url="api/rentACars";

$(document).on('submit', "#updateRentACarForma", function(e){
	e.preventDefault();
	console.log("update rent-a-car");
	var id = $('#identifikator').val();
	var naziv=$('#naziv').val();
	var adresa=$('#adresa').val();
	var opis=$('#opis').val();
	console.log("aaaaaaaaa");
	console.log(id);
	console.log("aaaaaaaaa");
	$.ajax({
		type:'PUT',
		url:update_rentacar_url+"/"+id,
		contentType:'application/json',
		dataType:'text',
		data:rentacarToJSON(id,naziv, adresa, opis),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			window.location.replace("prikazRentACar.html");
		}
			, error: function(XMLHttpRequest,textStatus, errorThrown) 
				{alert("Ne postoji rent-a-car sa datom ID-om!"+errorThrown);
			}
	});
});

function rentacarToJSON(id,naziv, adresa, opis){
	return JSON.stringify({
		"id":id,
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis 
	});
}