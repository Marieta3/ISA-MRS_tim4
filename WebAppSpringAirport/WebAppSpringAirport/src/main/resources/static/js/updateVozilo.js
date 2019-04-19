/**
 * 
 */
var dodaj_car_url="api/cars";

$(document).on('submit', "#updateVoziloForma", function(e){
	e.preventDefault();
	console.log("dodavanje vozila");
	var id= $('#identifikator').val();
	var proizvodjac=$('#proizvodjac').val();
	var model=$('#model').val();
	var godina=$('#godina').val();
	var tablica=$('#tablica').val();
	var cena=$('#cena').val();
	var brojMesta=$('#brojMesta').val();
	console.log(proizvodjac);
	$.ajax({
		type:'PUT',
		url:dodaj_car_url+"/"+id,
		contentType:'application/json',
		dataType:'text',
		data:carToJSON(id, proizvodjac, model, godina, tablica, cena, brojMesta),
		success:function(data){
			console.log(data); 
			window.location.replace("prikazVozila.html");
		}
	});
})


function carToJSON(id, proizvodjac, model, godina, tablica, cena, brojMesta){
	return JSON.stringify({
		"id":id,
		"proizvodjac":proizvodjac,
		"model":model,
		"godina":godina ,
		"tablica":tablica,
		"cena":cena,
		"brojMesta":brojMesta
	});
}