/**
 * 
 */
findAll();
function findAll(){
	$.ajax({
		type:'GET',
		url:'api/cars',
		dataType:'json',
		success:renderCars
	})
}

function renderCars(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
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