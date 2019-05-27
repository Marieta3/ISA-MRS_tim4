/**
 * 
 */
$(document).ready(function(){
	findAllByHotel();
	findAllUslugeByHotel();
	ymaps.ready(init);
})

function init(){
	//pokupiti koordinate iz hidden polja
	var coords= $('#hotel_coords').val();
	console.log("aaa"+coords);
	var coord_list=coords.split(',');
	var map = new ymaps.Map('map', {
        center: [coord_list[0], coord_list[1]],
        zoom: 12,
        controls: ['zoomControl', 'searchControl'],
        behaviors: ['drag']
    });
	var placemark=new ymaps.Placemark([coord_list[0], coord_list[1]], {
		
	});
	map.geoObjects.add(placemark);
	var searchControl=map.controls.get('searchControl');
	//var coords;
	/*searchControl.search('Moscow').then(function(){
		coords=searchControl.getResultsArray()[0].geometry._coordinates;
		var placemark=new ymaps.Placemark(coords, {
			
		});
		map.geoObjects.add(placemark);
		map.setCenter(coords);
	})*/
	searchControl.events.add('load', function(event){
		if(!event.get('skip') && searchControl.getResultsCount()){
			coords=searchControl.getResultsArray()[0].geometry._coordinates;
			console.log("pronadjeno: "+searchControl.getResultsArray()[0].properties.get('text'));
			$("#adresa").val(searchControl.getResultsArray()[0].properties.get('text'));
			$('#hotel_coords').val(coords);
			map.setCenter(coords);
			var placemark=new ymaps.Placemark(coords, {
				
			});
			map.geoObjects.add(placemark);
			//searchControl.showResult(0);
		}
	})
	
}
function getUslugeSobe(add, upd){
	$.ajax({
		type:'GET',
		url:'api/uslugeHotela/'+localStorage.getItem('hotel_id'),
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:function(data){
			var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
			$('#grid-'+add).remove();
			$('#grid-'+upd).remove();
			var grid_container=$('<div class="grid-container" id="grid-'+add+'"></div>');
			var grid_container1=$('<div class="grid-container" id="grid-'+upd+'"></div>');
			var padding=0;
			$.each(list, function(index, usluga){
				if(index%2==0){
					padding=padding+80;
				}
				var grid_item=$('<div class="grid-item">'+usluga.opis+'<br><input type="checkbox" value="'+usluga.id+'"></div>');
				grid_container.append(grid_item);
				grid_container1.append(grid_item);
			})
			
			$('#newRoomForma').css("padding-bottom", padding+'px');
			$('#editRoomForma').css("padding-bottom", padding+'px');
			dodajUslugeUModalAdd(grid_container);
			dodajUslugeUModalUpd(grid_container1);
		}
		});
}
function dodajUslugeUModalAdd(add, upd){
	$.ajax({
		type:'GET',
		url:'api/uslugeHotela/'+localStorage.getItem('hotel_id'),
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:function(data){
			var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
			$('#grid-'+add).remove();
			var grid_container=$('<div class="grid-container" id="grid-'+add+'"></div>');
			var padding=0;
			$.each(list, function(index, usluga){
				if(index%2==0){
					padding=padding+80;
				}
				var grid_item=$('<div class="grid-item">'+usluga.opis+'<br><input type="checkbox" value="'+usluga.id+'"></div>');
				grid_container.append(grid_item);
			})
			
			$('#newRoomForma').css("padding-bottom", padding+'px');
			grid_container.insertBefore('.addSobaBtn');
		}
	});
}

function dodajUslugeUModalUpd(upd){
	//var grid_container=getUslugeSobe("id04");
	//grid_container.insertAfter('#lblServices1');
	//$('#id04 .container-modal').append(grid_container);
	//grid_container.insertBefore('.updSobaBtn');
	$.ajax({
		type:'GET',
		url:'api/uslugeHotela/'+localStorage.getItem('hotel_id'),
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:function(data){
			var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
			$('#grid-'+upd).remove();
			var grid_container=$('<div class="grid-container" id="grid-'+upd+'"></div>');
			var padding=0;
			$.each(list, function(index, usluga){
				if(index%2==0){
					padding=padding+80;
				}
				var grid_item=$('<div class="grid-item">'+usluga.opis+'<br><input type="checkbox" id="usluga_soba_upd'+usluga.id+'" value="'+usluga.id+'"></div>');
				grid_container.append(grid_item);
			})
			
			$('#editRoomForma').css("padding-bottom", padding+'px');
			grid_container.insertBefore('.updSobaBtn');
		}
	});
}
function findAllUslugeByHotel(){
	$.ajax({
		type:'GET',
		url:'api/uslugeHotela',
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderUsluge
	});
}
function findAllByHotel(){
	$.ajax({
		type:'GET',
		url:'api/sobeHotela',
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderSobe,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.bootstrapGrowl("An error occured!", {
				  ele: 'body', // which element to append to
				  type: 'danger', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 2000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		}
	});
}

function renderUsluge(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	uloga=localStorage.getItem("uloga");
	
	if(uloga=="ROLE_HOTEL"){
		var th_nbsp=$('<th>&nbsp;</th>');
		var th_nbsp1=$('<th>&nbsp;</th>');
		$('#prikazUslugaTabela').find('tr:eq(0)').append(th_nbsp);
		$('#prikazUslugaTabela').find('tr:eq(0)').append(th_nbsp1);
	}
	$("#prikazUslugaTabela").find("tr:gt(0)").remove();
	$("#prikazUslugaTabela").find("th:gt(3)").remove();
	$.each(list, function(index, usluga){
		$('#prikazUslugaTabela').append(get_row(usluga, "service", localStorage.getItem('uloga'), 'id05', 'id06'));
	});
	$('#prikazUslugaTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 3 }
	                   ]
	  });
}
function renderSobe(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	uloga=localStorage.getItem("uloga");
	
	if(uloga=="ROLE_HOTEL"){
		var th_nbsp=$('<th>&nbsp;</th>');
		var th_nbsp1=$('<th>&nbsp;</th>');
		$('#prikazSobaTabela').find('tr:eq(0)').append(th_nbsp);
		$('#prikazSobaTabela').find('tr:eq(0)').append(th_nbsp1);
	}
	$("#prikazSobaTabela").find("tr:gt(0)").remove();
	$("#prikazSobaTabela").find("th:gt(7)").remove();
	$.each(list, function(index, soba){
		$('#prikazSobaTabela').append(get_row(soba, "room", localStorage.getItem('uloga'), 'id01', 'id04'));
	})
	$('#prikazSobaTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 }
	                   ]
	  });
}

function formaUpdateservice(e, forma){
	e.preventDefault();
	var id_usluge = $(forma).find('input[type=hidden]').val();
	
	$.ajax({
		type:"GET",
		url:"api/usluge/"+id_usluge,
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	
        	$("#opisUsluge1").val(data.opis);
        	$("#cenaUsluge1").val(data.cena);
        	$("#identifikatorUslugaUpd").val(data.id);
        }
		
	})
}

$(document).on('submit', "#editServiceForma", function(e){
	e.preventDefault();
	var opis=$("#opisUsluge1").val();
	var cena=$("#cenaUsluge1").val();
	var id=$("#identifikatorUslugaUpd").val();
	
	$.ajax({
		type:"PUT",
		url:"api/usluge/"+id,
		contentType:'application/json',
		dataType:'text',
		data:uslugaToJSON(id, opis, cena),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	$("#id06").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('editServiceForma');
			$('#service_'+id).remove();
			//dodajNoviEntitet('prikazUslugaTabela', get_row($.parseJSON(data), "service", localStorage.getItem('uloga'), 'id05', 'id06'));
			$('#prikazUslugaTabela').DataTable().clear().destroy();
			findAllUslugeByHotel();
			$.bootstrapGrowl("Service updated!", {
				  ele: 'body', // which element to append to
				  type: 'success', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
        	$.bootstrapGrowl("An error occured!", {
        		  ele: 'body', // which element to append to
        		  type: 'danger', // (null, 'info', 'danger', 'success')
        		  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
        		  align: 'right', // ('left', 'right', or 'center')
        		  width: 'auto', // (integer, or 'auto')
        		  delay: 2000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
        		  allow_dismiss: false, // If true then will display a cross to close the popup.
        		  stackup_spacing: 10 // spacing between consecutively stacked growls.
        		});
        }
	})
	
})

function uslugaToJSON(id, opis, cena){
	return JSON.stringify({
		"id":id,
		"opis":opis,
		"cena":cena
	});
}

function formaUpdateroom(e, forma){
	e.preventDefault();
	var id_sobe = $(forma).find('input[type=hidden]').val();
	$.ajax({
		type:"GET",
		url:"api/sobe/"+id_sobe,
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	if(data.slika!=null && data.slika!=""){
        		$('#room_img1').attr("src", data.slika);
        	}
        	$("#opisSobe1").val(data.opis);
        	$("#brojKreveta1").val(data.brojKreveta);
        	$("#identifikatorSobaUpd").val(data.id);
        	//cekiranje postojecih usluga sobe
        	$.each(data.usluge, function(index, usluga){
        		$('#usluga_soba_upd'+usluga.id).prop('checked', true);
        	})
        }
		
	})
}

$(document).on('submit', "#editRoomForma", function(e){
	e.preventDefault();
	var opis=$("#opisSobe1").val();
	var broj_kreveta=$("#brojKreveta1").val();
	var id=$("#identifikatorSobaUpd").val();
	//povuci selektovane usluge
	var checkedVals = $('input[type=checkbox]:checked').map(function() {
		return this.value;
	}).get();
	var slika = $('#slika_room1').val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	
	if(slika=="" || slika==null){
		slika=$("#room_img1").attr("src");
	}
	$.ajax({
		type:"PUT",
		url:"api/sobe/"+id,
		contentType:'application/json',
		dataType:'text',
		data:sobaToJSON(id, opis, slika, broj_kreveta, checkedVals),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	$("#id04").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('editRoomForma');
			$('#room_'+id).remove();
			//dodajNoviEntitet('prikazSobaTabela', get_row($.parseJSON(data), "room", localStorage.getItem('uloga'), 'id01', 'id04'));
			$('#prikazSobaTabela').DataTable().clear().destroy();
			findAllByHotel();
			$.bootstrapGrowl("Room updated!", {
				  ele: 'body', // which element to append to
				  type: 'success', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
        	$.bootstrapGrowl("An error occured!", {
        		  ele: 'body', // which element to append to
        		  type: 'danger', // (null, 'info', 'danger', 'success')
        		  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
        		  align: 'right', // ('left', 'right', or 'center')
        		  width: 'auto', // (integer, or 'auto')
        		  delay: 2000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
        		  allow_dismiss: false, // If true then will display a cross to close the popup.
        		  stackup_spacing: 10 // spacing between consecutively stacked growls.
        		});
        }
	})
	
})
function sobaToJSON(id, opis, slika, broj_kreveta, usluge){
	return JSON.stringify({
		"id":id,
		"slika":slika,
		"opis":opis,
		"brojKreveta":broj_kreveta,
		"usluge":usluge
	});
}
$(document).on('submit', ".modal-content3", function(e){
	e.preventDefault();
	console.log("dodavanje sobe");
	var opis=$("#opisSobe").val();
	var broj_kreveta=$("#brojKreveta").val();
	var slika=$('#slika_room').val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	//pokupiti sve usluge koje su checked
	
	var checkedVals = $('input[type=checkbox]:checked').map(function() {
		return this.value;
	}).get();
	var id_hotela=localStorage.getItem("hotel_id");
	$.ajax({
		type:'POST',
		url:"api/sobe",
		contentType:'application/json',
		dataType:'json',
		data:sobaToJSONadd(opis, slika, broj_kreveta, id_hotela, checkedVals),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			$("#id03").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('newRoomForma');
			//dodajNoviEntitet('prikazSobaTabela', get_row(data, "room", localStorage.getItem('uloga'), 'id01', 'id04'));
			$('#prikazSobaTabela').DataTable().clear().destroy();
			findAllByHotel();
			$.bootstrapGrowl("Room added!", {
				  ele: 'body', // which element to append to
				  type: 'success', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.bootstrapGrowl("An error occured!", {
				  ele: 'body', // which element to append to
				  type: 'danger', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 2000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		}
	});
})

$(document).on('submit', ".modal-content2", function(e){
	e.preventDefault();
	console.log("dodavanje usluge");
	var opis=$("#opisUsluge").val();
	var cena=$("#cenaUsluge").val();
	var id_hotela=localStorage.getItem("hotel_id");
	$.ajax({
		type:'POST',
		url:"api/usluge",
		contentType:'application/json',
		dataType:'json',
		data:uslugaToJSONadd(opis, cena, id_hotela),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			$("#id02").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('newUslugaForma');
			//dodajNoviEntitet('prikazUslugaTabela', get_row(data, "service", localStorage.getItem('uloga'), 'id05', 'id06'));
			$('#prikazUslugaTabela').DataTable().clear().destroy();
			findAllUslugeByHotel();
			$.bootstrapGrowl("Service added!", {
				  ele: 'body', // which element to append to
				  type: 'success', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		},

		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.bootstrapGrowl("An error occured!", {
				  ele: 'body', // which element to append to
				  type: 'danger', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 2000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		}
	});
})
function get_trow(data){
	var tr=$('<tr></tr>');
	tr.append('<td>' + data.opis + '</td>' + '<td>' + data.cena + '</td>');
	var formaObrisi = $('<form id="formaObrisiUslugu" onsubmit="formaObrisi(event, this, \'identifikatorUsluga\', \'usluga\')"></form>');
	formaObrisi.append('<input type="hidden" value="' + data.id + '">');
	formaObrisi.append('<input id="hiddenUsluga" type="hidden" value="' + data.opis+ '">');
	formaObrisi.append('<input type="submit" value="Delete" onclick="otvoriModal(\'id05\')">');
	var td = $('<td></td>');
	td.append(formaObrisi);
	tr.append(td);

	var formaUpdate = $('<form id="formaUpdateUsluga"></form>');
	formaUpdate.append('<input type="hidden" value="' + data.id + '">');
	formaUpdate.append('<input type="submit" value="Update" onclick="otvoriModal(\'id06\')">');
	var td1 = $('<td></td>');
	td1.append(formaUpdate);
	tr.append(td1);
	return tr;
}
$(document).on('submit', "#deleteSobaForma", function(e){
	e.preventDefault();
	var id=$("#identifikatorSoba").val();
	$.ajax({
		type : 'DELETE',
		url : '/api/sobe/' + id,
		/*
		 * Milan: drugi nacin da token posaljete u zahtevu kroz ajax poziv kroz
		 * header
		 */
		headers : {
			Authorization : "Bearer " + localStorage.getItem("accessToken")
		},
		success : function() {
			$('#room_' + id).remove();
			$("#id01").css("display", "none");
			$("body").removeClass("modal-open");
			$('#prikazSobaTabela').DataTable().clear().destroy();
			findAllByHotel();
			$.bootstrapGrowl("Room deleted!", {
				  ele: 'body', // which element to append to
				  type: 'success', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.bootstrapGrowl("An error occured!", {
				  ele: 'body', // which element to append to
				  type: 'danger', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 2000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		}
	})
	
})

$(document).on('submit', "#deleteUslugaForma", function(e){
	e.preventDefault();
	var id=$("#identifikatorUsluga").val();
	$.ajax({
		type : 'DELETE',
		url : '/api/usluge/' + id,
		/*
		 * Milan: drugi nacin da token posaljete u zahtevu kroz ajax poziv kroz
		 * header
		 */
		headers : {
			Authorization : "Bearer " + localStorage.getItem("accessToken")
		},
		success : function() {
			$('#service_' + id).remove();
			$("#id05").css("display", "none");
			$("body").removeClass("modal-open");
			$('#prikazUslugaTabela').DataTable().clear().destroy();
			findAllUslugeByHotel();
			$.bootstrapGrowl("Service deleted!", {
				  ele: 'body', // which element to append to
				  type: 'success', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 3000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.bootstrapGrowl("An error occured!", {
				  ele: 'body', // which element to append to
				  type: 'danger', // (null, 'info', 'danger', 'success')
				  offset: {from: 'top', amount: 20}, // 'top', or 'bottom'
				  align: 'right', // ('left', 'right', or 'center')
				  width: 'auto', // (integer, or 'auto')
				  delay: 2000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
				  allow_dismiss: false, // If true then will display a cross to close the popup.
				  stackup_spacing: 10 // spacing between consecutively stacked growls.
				});
		}
	})
	
})

function sobaToJSONadd(opis, slika, broj_kreveta, id_hotela, usluge){
	return JSON.stringify({
		"opis":opis,
		"slika":slika,
		"brojKreveta":broj_kreveta,
		"idHotela":id_hotela,
		"usluge":usluge
	});
}
function uslugaToJSONadd(opis, cena, id_hotela){
	return JSON.stringify({
		"opis":opis,
		"cena":cena,
		"hotel_id":id_hotela
	});
}


