/**
 * 
 */
findAll();
function findAll(){
	$.ajax({
		type:'GET',
		url:'api/users',
		dataType:'json',
		success:renderKorisnici
	})
}

function renderKorisnici(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	$.each(list, function(index, korisnik){
		var tr=$('<tr></tr>');
		tr.append('<td>' + korisnik.id + '</td>'+'<td>' + korisnik.korisnickoIme + '</td>' + '<td>'
				+ korisnik.lozinka + '</td>' + '<td>'
				+ korisnik.mail + '</td>'  );
		$('#prikazKorisnikaTabela').append(tr);
		
	})
}