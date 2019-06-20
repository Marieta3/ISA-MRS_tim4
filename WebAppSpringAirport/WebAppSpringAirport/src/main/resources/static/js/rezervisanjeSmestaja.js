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
	      "order":[[1,'asc']],
	      "columnDefs": [
	                     { "orderable": false, "targets": 0 },
	                     { "orderable": false, "targets": 5 }
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
	$('#selected-rooms').empty();
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
	      "order":[[1,'asc']],
	      "columnDefs": [
	                     { "orderable": false, "targets": 0 },
	                     { "orderable": false, "targets": 6 }
	                   ]
	  });
}

function pretragaSoba(e){
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
}
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
	var broj_nocenja=$('#broj_nocenja').val();
	if(broj_nocenja == null || broj_nocenja == 0){
		broj_nocenja=1;
		
	}
	console.log(cena);
	if($(checkbox).prop('checked')==true){
		$('#counter-rooms').text(parseInt($('#counter-rooms').text(), 10)+1);
		$('#selected-rooms').append('<li id="room_lista'+id_sobe+'">Room: '+opis+' <a href="#selected-rooms" class="cancel-cart-item-room">[cancel]</a></li>');
		$('#total-rooms').text(parseInt($('#total-rooms').text(), 10)+parseInt(cena, 10)*broj_nocenja);
	}else{
		$('#counter-rooms').text(parseInt($('#counter-rooms').text(), 10)-1);
		$('#total-rooms').text(parseInt($('#total-rooms').text(), 10)-parseInt(cena, 10)*broj_nocenja);
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

function selektovanaBrzaRezervacija(e){
	e.preventDefault();
	var lista_sedista=$('#selected-seats li');
	if(lista_sedista.length==0){
		console.log("nece moci");
		notify("Could not proceed reservation. You should reserve at least one seat!", 'info');
		return;
	}else if(lista_sedista.length>1){
		notify("Could not proceed reservation. You should reserve only one seat!", 'info');
		return;
	}
	var dolazak=$('#datepicker6').val();
	var broj_nocenja=$('#broj_nocenja').val();
	var hotel_id=$('#selektovan-hotel-id').val();
	$.ajax({
		type:'POST',
		url:'api/brzeSobe/'+hotel_id,
		contentType: 'application/json',
		dataType : 'json',
		data : sobaToJSONsearch(dolazak, broj_nocenja),
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success : renderBrzeSobe
	})
}

function renderBrzeSobe(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	uloga=localStorage.getItem("uloga");
	$('#selected-rooms').empty();
	$('#prikazBrzihSobaTabela').DataTable().clear().destroy();
	//findAllByHotel();
	//$('#hotel-naziv-adresa').text(data[0].hotel.naziv+', '+data[0].hotel.adresa);
	
	//$("#prikazBrzihSobaTabela").find("tr:gt(0)").remove();
	//$("#prikazBrzihSobaTabela").find("th:gt(6)").remove();
	$.each(list, function(index, item){
		var trow=$('<tr></tr>');
		var slika=item.soba.slika;
		if(slika==null || slika==""){
			slika = "../slike/pic1.jpg";
		}
		trow.append('<td align="center" width=100px height=100px><div class="divEntitet"><img class="imgEntitet" src="'+slika+'"></div></td>');
		trow.append('<td>'+item.soba.brojKreveta+'</td>');
		trow.append('<td>'+item.soba.opis+'</td>');
		trow.append('<td>'+item.soba.cena+'</td>');
		trow.append('<td>'+item.nova_cena+'</td>');
		trow.append('<td>'+item.start_date+'</td>');
		trow.append('<td>'+item.end_date+'</td>');
		trow.append('<td><button onclick="selektovanaBrzaSoba(this)"><input type="hidden" id="'+item.id+'">Select</button></td>');
		$('#prikazBrzihSobaTabela').append(trow);
	})
	$('#prikazBrzihSobaTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "order":[[1,'asc']],
	      "columnDefs": [
	                     { "orderable": false, "targets": 0 },
	                     { "orderable": false, "targets": 6 }
	                   ]
	  });
}

function selektovanaBrzaSoba(btn){
	//pokupi sediste i brzu rezervaciju
	var lista_sedista=$('#selected-seats li');
	var sediste;
	if(lista_sedista.length!=1){
		console.log("broj sedista nije 1!");
		return;
	}
	var broj_nocenja=$('#broj_nocenja').val();
	if(broj_nocenja==null || broj_nocenja==0){
		console.log("izaberite broj nocenja");
		return;
	}
	var brza_soba_id=$(btn).find('input[type=hidden]').attr('id');
	$.each(lista_sedista, function(index, item){
		var tokens=item.id.split('-');
		sediste=tokens[2];
	})
	
	var let_id=$('#id-odabranog-leta').val();
	$.ajax({
		type:'POST',
		url:'api/brzeSobe/reserve',
		contentType: 'application/json',
		dataType : 'json',
		data : brzaSobaToJSONsearch(let_id, sediste, brza_soba_id),
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success : function(data){
			window.location.replace("profilROLE_USER.html");
		},
		error:function(data){
			notify("Reservation failed.", "danger");
		}
	})
	
}

function brzaSobaToJSONsearch(let_id, sediste, brza_soba_id){
	return JSON.stringify({
		"let_id" : let_id,
		"brz_id" : brza_soba_id,
		"row_col":sediste
	});
}