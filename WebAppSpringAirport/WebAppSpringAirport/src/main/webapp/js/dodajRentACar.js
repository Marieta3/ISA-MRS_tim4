/**
 * 
 */
var dodaj_rentacar_url="api/rentACars";

$(document).on('submit', "#dodajRentacarForma", function(e){
	e.preventDefault();
	console.log("dodavanje rentacar");
	var naziv=$('#naziv').val();
	var adresa=$('#adresa').val();
	var opis=$('#opis').val();
	console.log(naziv);
	$.ajax({
		type:'POST',
		url:dodaj_rentacar_url,
		contentType:'application/json',
		dataType:'text',
		data:rentacarToJSON(naziv, adresa, opis),
		success:function(data){
			console.log(data); 
			window.location.replace("prikazRentACar.html");
		}
	});
})


function rentacarToJSON(naziv, adresa, opis){
	return JSON.stringify({
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis 
	});
}