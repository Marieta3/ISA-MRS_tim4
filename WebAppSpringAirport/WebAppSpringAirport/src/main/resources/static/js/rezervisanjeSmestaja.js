/**
 * 
 */
findAllHotels();
function findAllHotels(){
	$.ajax({
		type : 'GET',
		url : 'api/hotels',
		dataType : 'json',
		/*
		 * Milan: jedan nacin da token koji ste dobili prilikom logina posaljete
		 * kroz ajax zahtev kroz header
		 */
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success : renderHoteli
	})
}

function renderHoteli(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	uloga=localStorage.getItem("uloga");
	$('#prikazHotelaTabela').DataTable().clear().destroy();
	$("#prikazHotelaTabela").find("tr:gt(0)").remove();
	$("#prikazHotelaTabela").find("th:gt(5)").remove();
	$.each(list, function(index, hotel){
		var trow=get_row(hotel, "hotel", localStorage.getItem('uloga'), 'nema', 'nema');
		trow.append('<td><button  onclick="selektovanHotel(this)"><input type="hidden" id="'+hotel.id+'">Select</button></td>');
		$('#prikazHotelaTabela').append(trow);
	})
	$('#prikazHotelaTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 }
	                   ]
	  });
}

function selektovanHotel(btn){
	var hotel_id=$(btn).find('input[type=hidden]').attr('id');
	$.ajax({
		type:'GET',
		url:'api/sobeHotela/'+hotel_id,
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderSobe,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
}

function renderSobe(data){
var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	uloga=localStorage.getItem("uloga");
	$('#prikazSobaTabela').DataTable().clear().destroy();
	//findAllByHotel();
	
	$("#prikazSobaTabela").find("tr:gt(0)").remove();
	$("#prikazSobaTabela").find("th:gt(5)").remove();
	$.each(list, function(index, soba){
		var trow=get_row(soba, "room", localStorage.getItem('uloga'), 'nema', 'nema');
		trow.append('<td><input type="checkbox" id="soba_id'+soba.id+'" name="'+soba.opis+'" value="'+soba.id+'" onclick="selektovanaSoba(this)"></td>');
		$('#prikazSobaTabela').append(trow);
	})
	$('#prikazSobaTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 }
	                   ]
	  });
}
function selektovanaSoba(checkbox){
	//ako je cekirano dodati u listu, ako nije onda izbaciti iz liste
	var id_sobe=checkbox.value;
	var opis=checkbox.name;
	if($(checkbox).prop('checked')==true){
		$('#counter-rooms').text(parseInt($('#counter-rooms').text(), 10)+1);
		$('#selected-rooms').append('<li id="room_lista'+id_sobe+'">Room: '+opis+'</li>');
		
	}else{
		$('#counter-rooms').text(parseInt($('#counter-rooms').text(), 10)-1);
		$('#room_lista'+id_sobe).remove()
	}
	
}
function pokupiRezervisaneSobe(){
	var checkedVals = $('input[type=checkbox]:checked').map(function() {
		return this.value;
	}).get();
	console.log(checkedVals);
	$('#cars-tab').click();
}