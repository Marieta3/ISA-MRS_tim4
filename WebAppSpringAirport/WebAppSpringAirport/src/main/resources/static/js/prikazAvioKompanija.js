/**
 * 
 */
uloga="";

$(document).ready(function(){
	uloga=localStorage.getItem("uloga");
	if(uloga=="ROLE_ADMIN"){
		$("#nav-bar").append('<li><a href="dodajAvioKompaniju.html">New Airline</a></li>');
	}
});

findAll();
function findAll(){
	$.ajax({
		type:'GET',
		url:'api/avioKompanije',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:renderAvioKompanije
	})
}

function renderAvioKompanije(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	$.each(list, function(index, avioKompanija){
		var tr=$('<tr></tr>');
		if (avioKompanija.slika == null){
			avioKompanija.slika = "../slike/aereo2.jpg";
		}
		tr.append('<td align="center" width=100px height=100px>'+ '<div id="divAviokompanija">' +
				'<img src=" ' + avioKompanija.slika +' " id="imgProfilnaAviokompanija"> ' + '</div>' +
				'</td>'+'<td>' + avioKompanija.naziv + '</td>' + '<td>'
				+ avioKompanija.adresa + '</td>' + '<td>'
				+ avioKompanija.opis + '</td>'  );
		$('#prikazAvioKompanijaTabela').append(tr);
		
	})
}