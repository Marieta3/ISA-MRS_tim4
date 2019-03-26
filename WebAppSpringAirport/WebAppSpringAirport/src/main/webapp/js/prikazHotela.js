/**
 * 
 */
findAll();
function findAll(){
	$.ajax({
		type:'GET',
		url:'api/hotels',
		dataType:'json',
		success:renderHoteli
	})
}

function renderHoteli(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	$.each(list, function(index, hotel){
		var tr=$('<tr></tr>');
		tr.append('<td>' + hotel.id + '</td>'+'<td>' + hotel.naziv + '</td>' + '<td>'
				+ hotel.adresa + '</td>' + '<td>'
				+ hotel.opis + '</td>'  );
		$('#prikazHotelaTabela').append(tr);
		
	})
}