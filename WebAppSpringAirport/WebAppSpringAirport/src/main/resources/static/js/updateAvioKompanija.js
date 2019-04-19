/**
 * 
 */
var update_avio_kompaniju_url="api/avioKompanije";

$(document).on('submit', "#updateAvioKompanijuForma", function(e){
	e.preventDefault();
	console.log("dodavanje avio");
	var id=$('#identifikator').val();
	var naziv=$('#naziv').val();
	var adresa=$('#adresa').val();
	var opis=$('#opis').val();
	console.log(naziv);
	$.ajax({
		type:'PUT',
		url:update_avio_kompaniju_url+'/'+id,
		contentType:'application/json',
		dataType:'text',
		data:avioToJSON(id, naziv, adresa, opis),
		success:function(data){
			console.log(data); 
			window.location.replace("prikazAvioKompanija.html");
		}
	});
})


function avioToJSON(id, naziv, adresa, opis){
	return JSON.stringify({
		"id":id,
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis 
	});
}