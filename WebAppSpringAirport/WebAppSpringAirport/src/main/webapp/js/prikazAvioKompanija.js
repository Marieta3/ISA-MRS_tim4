/**
 * 
 */
findAll();
function findAll(){
	$.ajax({
		type:'GET',
		url:'api/avioKompanije',
		dataType:'json',
		success:renderAvioKompanije
	})
}

function renderAvioKompanije(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	$.each(list, function(index, avioKompanija){
		var tr=$('<tr></tr>');
		tr.append('<td>' + avioKompanija.id + '</td>'+'<td>' + avioKompanija.naziv + '</td>' + '<td>'
				+ avioKompanija.adresa + '</td>' + '<td>'
				+ avioKompanija.opis + '</td>'  );
		$('#prikazAvioKompanijaTabela').append(tr);
		
	})
}