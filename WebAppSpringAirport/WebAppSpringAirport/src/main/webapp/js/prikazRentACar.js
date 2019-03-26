/**
 * 
 */
findAll();
function findAll(){
	$.ajax({
		type:'GET',
		url:'api/rentACars',
		dataType:'json',
		success:renderRentACars
	})
}

function renderRentACars(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	$.each(list, function(index, rentACar){
		var tr=$('<tr></tr>');
		tr.append('<td>' + rentACar.id + '</td>'+'<td>' + rentACar.naziv + '</td>' + '<td>'
				+ rentACar.adresa + '</td>' + '<td>'
				+ rentACar.opis + '</td>'  );
		$('#prikazRentACarTabela').append(tr);
		
	})
}