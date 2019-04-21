/**
 * 
 */
var dodaj_avio_kompaniju_url="api/avioKompanije";

$(document).on('submit', "#dodajAvioKompanijuForma", function(e){
	e.preventDefault();
	console.log("dodavanje avio");
	var naziv=$('#naziv').val();
	var adresa=$('#adresa').val();
	var opis=$('#opis').val();
	console.log(naziv);
	$.ajax({
		type:'POST',
		url:dodaj_avio_kompaniju_url,
		contentType:'application/json',
		dataType:'text',
		data:avioToJSON(naziv, adresa, opis),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			window.location.replace("prikazAvioKompanija.html");
		}
	});
})


function avioToJSON(naziv, adresa, opis){
	return JSON.stringify({
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis 
	});
}