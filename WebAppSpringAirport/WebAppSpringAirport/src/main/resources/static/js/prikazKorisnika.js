/**
 * 
 */
uloga="";

$(document).ready(function(){
	uloga=localStorage.getItem("uloga");
	if(uloga=="ROLE_ADMIN"){
		$("#nav-bar").append('<li><a href="dodajKorisnika.html">New Admin</a></li>');
	}
});
findAll();
function findAll(){
	$.ajax({
		type:'GET',
		url:'api/users',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
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