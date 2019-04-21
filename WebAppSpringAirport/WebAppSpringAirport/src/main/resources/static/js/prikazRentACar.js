/**
 * 
 */

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
		tr.append('<td>' + rentACar.id + '</td>' + '<td>' + rentACar.naziv
				+ '</td>' + '<td>' + rentACar.adresa + '</td>' + '<td>'
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