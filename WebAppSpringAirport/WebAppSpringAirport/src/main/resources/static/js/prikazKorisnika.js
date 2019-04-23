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
        var prom = korisnik.authorities[0].authority;
        var ul="";
		if (korisnik.slika == null){
			korisnik.slika = "../slike/avatar.png";
		}
        if (prom == "ROLE_ADMIN"){
            ul = "SYSTEM ADMIN";
        } else if(prom == "ROLE_USER") {
            ul = "REGISTERED USER"
        } else if(prom == "ROLE_RENT") {
            ul = "ADMIN RENT-A-CAR";
        } else if (prom == "ROLE_HOTEL") {
            ul = "ADMIN HOTEL";
        } else {
            ul = "ADMIN AVIOCOMPANY";
        }
		tr.append('<td align="center" width=100px height=100px>'+ '<div id="divKorisnik">' +
				'<img src=" ' + korisnik.slika +' " id="imgProfilnaKorisnici"> ' + '</div>' +
				'</td>'+ '<td align="center">' + korisnik.ime + '</td>'+ '<td align="center">' + 
				korisnik.prezime + '</td>'+'<td align="center">' + korisnik.korisnickoIme + '</td>' + '<td>'
				+ korisnik.mail + '</td>' + '<td align="center">' + ul + '</td>');
		$('#prikazKorisnikaTabela').append(tr);
		
	})
}