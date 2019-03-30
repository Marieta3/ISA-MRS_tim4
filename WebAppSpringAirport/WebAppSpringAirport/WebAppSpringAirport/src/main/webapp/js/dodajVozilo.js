/**
 * 
 */
var dodaj_car_url="api/cars";

$(document).on('submit', "#dodajVoziloForma", function(e){
	e.preventDefault();
	console.log("dodavanje vozila");
	var proizvodjac=$('#proizvodjac').val();
	var model=$('#model').val();
	var godina=$('#godina').val();
	var tablica=$('#tablica').val();
	var cena=$('#cena').val();
	console.log(proizvodjac);
	$.ajax({
		type:'POST',
		url:dodaj_car_url,
		contentType:'application/json',
		dataType:'text',
		data:carToJSON(proizvodjac, model, godina, tablica, cena),
		success:function(data){
			console.log(data); 
			window.location.replace("prikazVozila.html");
		}
	});
})


function carToJSON(proizvodjac, model, godina, tablica, cena){
	return JSON.stringify({
		"proizvodjac":proizvodjac,
		"model":model,
		"godina":godina ,
		"tablica":tablica,
		"cena":cena
	});
}