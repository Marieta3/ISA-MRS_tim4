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
	if(list.length == 0){
		console.log("Not found data");
		notify("No hotels found!", 'info');
		//return;
	}
	
	$('#prikazHotelaTabela').DataTable().clear().destroy();
	$("#prikazHotelaTabela").find("tr:gt(0)").remove();
	$("#prikazHotelaTabela").find("th:gt(5)").remove();
	$.each(list, function(index, hotel){
		var trow=get_row(hotel, "hotel", localStorage.getItem('uloga'), 'nema', 'nema');
		trow.append('<td><a href="#prikaz-soba"><button  onclick="selektovanHotel(this)"><input type="hidden" id="'+hotel.id+'">Select</button></a></td>');
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
	$('#selected-rooms').empty();
	$('#counter-rooms').text('0');
	$('#total-rooms').text('0');
	$('#selektovan-hotel-id').val(hotel_id);
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
	$('#hotel-naziv-adresa').text(data[0].hotel.naziv+', '+data[0].hotel.adresa);
	
	$("#prikazSobaTabela").find("tr:gt(0)").remove();
	$("#prikazSobaTabela").find("th:gt(6)").remove();
	$.each(list, function(index, soba){
		var trow=get_row(soba, "room", localStorage.getItem('uloga'), 'nema', 'nema');
		trow.append('<td><input type="checkbox" class="cart-item-room" id="soba_id'+soba.id+'" name="'+soba.opis+', '+soba.cena+'" value="'+soba.id+'" onclick="selektovanaSoba(this)"></td>');
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

$(document).on('submit', '#pretragaSoba', function(e){
	e.preventDefault();
	var dolazak=$('#datepicker6').val();
	var broj_nocenja=$('#broj_nocenja').val();
	var hotel_id=$('#selektovan-hotel-id').val();
	$.ajax({
		type:'POST',
		url:'api/sobeHotela/pretraga/'+hotel_id,
		contentType: 'application/json',
		dataType : 'json',
		data : sobaToJSONsearch(dolazak, broj_nocenja),
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success : renderSobe
	})
});
function sobaToJSONsearch(dolazak, broj_nocenja) {
	return JSON.stringify({
		"vremeDolaska" : dolazak,
		"brojNocenja" : broj_nocenja
	});
}
function selektovanaSoba(checkbox){
	//ako je cekirano dodati u listu, ako nije onda izbaciti iz liste
	var id_sobe=checkbox.value;
	var tokens=checkbox.name.split(', ');
	var opis= tokens[0];
	var cena=tokens[1];
	console.log(cena);
	if($(checkbox).prop('checked')==true){
		$('#counter-rooms').text(parseInt($('#counter-rooms').text(), 10)+1);
		$('#selected-rooms').append('<li id="room_lista'+id_sobe+'">Room: '+opis+' <a href="#selected-rooms" class="cancel-cart-item-room">[cancel]</a></li>');
		$('#total-rooms').text(parseInt($('#total-rooms').text(), 10)+parseInt(cena, 10));
	}else{
		$('#counter-rooms').text(parseInt($('#counter-rooms').text(), 10)-1);
		$('#total-rooms').text(parseInt($('#total-rooms').text(), 10)-parseInt(cena, 10));
		$('#room_lista'+id_sobe).remove()
	}
	
}
//this will handle "[cancel]" link clicks
/*$('#selected-rooms').on('click', '.cancel-cart-item-room', function () {
	//let's just trigger Click event on the appropriate seat, so we don't have to repeat the logic here
	//sc.get($(this).parents('li:first').data('seatId')).click();
	console.log("cancel this"+this.id);
	console.log("cancel $this"+$(this).parent().attr('id'));
	var room_lista=$(this).parent().attr('id');
	var tokens=room_lista.split('lista');
	var id_sobe=tokens[1];
	$(this).parent().remove();
	$('#counter-rooms').text(parseInt($('#counter-rooms').text(), 10)-1);
	$('#total-rooms').text(parseInt($('#total-rooms').text(), 10)-parseInt(cena, 10));
	$('#soba_id'+id_sobe).prop('checked', false);
});*/
function pokupiRezervisaneSobe(){
	var checkedVals = $('input[type=checkbox]:checked').map(function() {
		return this.value;
	}).get();
	console.log(checkedVals);
	//$('#cars-tab').click();
	notify("Successfully reserved flight and hotel room!", 'info');
}