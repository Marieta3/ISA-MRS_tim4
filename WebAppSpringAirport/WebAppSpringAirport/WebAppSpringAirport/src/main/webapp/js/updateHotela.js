/**
 * 
 */

var update_hotela_url="api/hotels";

$(document).on('submit', "#updateHotelaForma", function(e){
	e.preventDefault();
	console.log("update hotel");
	var id = $('#identifikator').val();
	var naziv=$('#naziv').val();
	var adresa=$('#adresa').val();
	var opis=$('#opis').val();
	console.log("aaaaaaaaa");
	console.log(id);
	console.log("aaaaaaaaa");
	$.ajax({
		type:'PUT',
		url:update_hotela_url+"/"+id,
		contentType:'application/json',
		dataType:'text',
		data:hotelToJSON(id,naziv, adresa, opis),
		success:function(data){
			console.log(data); 
			console.log("EVO MEEEEEE")
			window.location.replace("prikazHotela.html");
		}
			, error: function(XMLHttpRequest,textStatus, errorThrown) 
				{alert("Ne postoji hotel sa datom ID-om!"+errorThrown);
			}
	});
});

function hotelToJSON(id,naziv, adresa, opis){
	return JSON.stringify({
		"id":id,
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis 
	});
}