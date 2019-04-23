/**
 * 
 */
uloga="";

$(document).ready(function(){
	uloga=localStorage.getItem("uloga");
	if(uloga=="ROLE_ADMIN"){
		$("#nav-bar").append('<li><a href="dodajRentACar.html">New Rent-A-Car</a></li>');
	}
});

findAll();
function findAll() {
	$.ajax({
		type : 'GET',
		url : 'api/rentACars',
		dataType : 'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success : renderRentACars
	})
}

function renderRentACars(data) {
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	if(list.length == 0){
		console.log("Not found data");
		alert("No rent-a-car found!")
		return;
	}

	$("#prikazRentACarTabela tbody").empty();
	$.each(list, function(index, rentACar) {
		var tr = $('<tr></tr>');
		if (rentACar.slika == null) {
			rentACar.slika = "../slike/rent_car.jpg"
		}
		tr.append('<td align="center" width=100px height=100px>'+ '<div id="divRent">' +
				'<img src=" ' + rentACar.slika +' " id="imgProfilnaRent"> ' + '</div>' +
				'</td>' +'<td>'+ rentACar.naziv
				+ '</td>' + '<td>' + rentACar.adresa + '</td>' + '<td width=100px>'
				+ rentACar.opis + '</td>');
		$('#prikazRentACarTabela tbody').append(tr);

	})
}

$('document').ready(function() {
	$('#btnSearch').bind('click', searchCars);
});

function searchCars() {
	var naziv = $('#searchName').val();
	console.log(naziv);

	if (naziv) {
		$.ajax({
			type : 'GET',
			url : 'api/rentACars/search/' + naziv,
			dataType : 'json',
			success : renderRentACars
		});
	} else{
		findAll();
	}
}