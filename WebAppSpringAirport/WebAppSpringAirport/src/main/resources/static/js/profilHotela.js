$(document).ready(function () {
    var hotel_id = localStorage.getItem("profil_hotel")
    if(hotel_id == null){
        window.location.replace("prikazHotela.html")
    }
    
    findProfile();
    findAllRoomsByHotel();
    findAllServicesByHotel();
});

function findProfile(){
	$.ajax({
		type:'GET',
		url:'/api/hotels/' + localStorage.getItem("profil_hotel"),
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderProfil
	});
}

function findAllRoomsByHotel(){
	$.ajax({
		type:'GET',
		url:'api/sobeHotela/' + localStorage.getItem("profil_hotel"),
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderSobe
	});
}

function findAllServicesByHotel(){
	$.ajax({
		type:'GET',
		url:'/api/uslugeHotela/' + localStorage.getItem("profil_hotel"),
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderUsluge
	});
}

function renderProfil(data){
	console.log(data);
	var rating=data.ocena;
	
	$('.cornerimage').css("width", (rating/5)*100+"%");
	$('#rating_hotel').text(rating);
	$("#naziv").val(data.naziv);
	$("#adresa").val(data.adresa);
	$("#opis").val(data.opis);
}

function renderSobe(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	uloga=localStorage.getItem("uloga");
	
	$("#prikazSobaTabela").find("tr:gt(0)").remove();
	$("#prikazSobaTabela").find("th:gt(5)").remove();
	slika = "slike/room.jpg"
	$.each(list, function(index, soba){
		var tr=$('<tr id="room_' + soba.id + '"></tr>');
		tr.append('<td align="center" width=100px height=100px><div class="divEntitet"><img class="imgEntitet" src="'+slika+'"></div></td>');
		tr.append('<td align="center">' + soba.brojKreveta + '</td>'+ '<td align="center">' + 
				soba.opis + '</td>'+'<td align="center">' + soba.ocena + '</td>' + '<td>'
				+ soba.cena + '</td>');
		$('#prikazSobaTabela').append(tr);
	})
	
	if ( ! $.fn.DataTable.isDataTable( '#prikazSobaTabela' ) ) {
	$('#prikazSobaTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "order":[[1,'asc']],
	      "columnDefs": [
	                     { "orderable": false, "targets": 0 }
	                   ]
	  });
	}
}

function renderUsluge(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	console.log(data);
	uloga=localStorage.getItem("uloga");
	/*
	$("#prikazUslugaTabela").find("tr:gt(0)").remove();
	$("#prikazUslugaTabela").find("th:gt(1)").remove();
	*/
	$.each(list, function(index, usluga){
		$('#prikazUslugaTabela tbody').append(get_row(usluga, "service", localStorage.getItem('uloga'), null, null));
	})
	if ( ! $.fn.DataTable.isDataTable( '#prikazUslugaTabela' ) ) {
		$('#prikazUslugaTabela').DataTable({
		      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
		      "iDisplayLength": 5
		  });
	}
}