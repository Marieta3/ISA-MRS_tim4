/**
 * 
 */
uloga="";

$(document).ready(function(){
	uloga=localStorage.getItem("uloga");
	if(uloga=="ROLE_RENT"){
		$("#nav-bar").append('<li><a href="dodajVozilo.html">New Vehicle</a></li>');
	}
});
findAll();
function findAll(){
	$.ajax({
		type:'GET',
		url:'api/cars',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:renderCars
	})
}

function renderCars(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	//$("#prikazRentACarTabela").find("tr:gt(0)").remove();
	//$("#prikazRentACarTabela").find("th:gt(4)").remove();
	$.each(list, function(index, car){
		var tr=$('<tr></tr>');
		tr.append('<td>' + car.id + '</td>'+'<td>' + car.proizvodjac + '</td>' + '<td>'
				+ car.model + '</td>' + '<td>'
				+ car.godina + '</td>' + '<td>'
				+ car.tablica + '</td>' + '<td>'
				+ car.cena + '</td>' + '<td>'
				+ car.brojMesta + '</td>'  );
		$('#prikazVozilaTabela').append(tr);
		
	})
}