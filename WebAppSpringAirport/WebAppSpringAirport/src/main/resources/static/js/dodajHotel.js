/**
 * 
 */
var dodaj_hotel_url="api/hotels";

$(document).on('submit', "#dodajHotelForma", function(e){
	e.preventDefault();
	console.log("dodavanje hotela");
	var naziv=$('#naziv').val();
	var adresa=$('#adresa').val();
	var opis=$('#opis').val();
	console.log(naziv);
	$.ajax({
		type:'POST',
		url:dodaj_hotel_url,
		contentType:'application/json',
		dataType:'json',
		data:hotelToJSON(naziv, adresa, opis),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			window.location.replace("prikazHotela.html");
		}
	});
})


function hotelToJSON(naziv, adresa, opis){
	return JSON.stringify({
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis 
	});
}