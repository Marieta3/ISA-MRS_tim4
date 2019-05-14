
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
	uloga=localStorage.getItem("uloga");

	$("#friendsTable").find("tr:gt(0)").remove();
	$("#friendsTable").find("th:gt(6)").remove();
	$.each(list, function(index, korisnik){
		var tr=$('<tr></tr>');
		console.log(korisnik.ime);
		console.log(korisnik.authorities);
        var prom = korisnik.authorities[0].authority;
        var ul="";
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
		tr.append('<td>' + korisnik.ime + '</td>'+ '<td>' + korisnik.prezime + '</td>'+'<td>' + korisnik.korisnickoIme + '</td>' + '<td>'
				+ korisnik.mail + '</td>' + '<td>' + ul + '</td>');
		
		if (uloga == "ROLE_ADMIN") {
			var formaObrisi = $('<form id="formaObrisi" onsubmit="formaObrisi(event, this, \'identifikator\', \'imePrezimeUsername\')"></form>');
			formaObrisi.append('<input type="hidden" value="' + korisnik.id + '">');
			formaObrisi.append('<input id="hiddenKorisnik" type="hidden" value="' + korisnik.ime+" "+korisnik.prezime + ", "+korisnik.korisnickoIme+'">');
			formaObrisi.append('<input type="submit" value="Delete" onclick="otvoriModal(\'id02\')">');
			var td = $('<td></td>');
			td.append(formaObrisi);
			tr.append(td);
		
		}
		if (uloga == "ROLE_USER") {
			
			tr.append("<td align=\'center\'><button value= \'test\'>Test</button></td>");
		
		}
		$("#friendsTable").find("tbody").append(tr);
		
	})
}


$(document).ready(function() {
  $('#friendsTable').DataTable({
      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
      "iDisplayLength": 5
  });
});