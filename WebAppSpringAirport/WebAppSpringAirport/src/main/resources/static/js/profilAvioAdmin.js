
/**
 * 
 */
ymaps.ready(init);

$(document).ready(function(){
	findAll();
	findAllFlightsByAvio();
})

function init(){
	//pokupiti koordinate iz hidden polja
	var coords= $('#avio_coords').val();
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
			$('#avio_coords').val(coords);
			map.setCenter(coords);
			var placemark=new ymaps.Placemark(coords, {
				
			});
			map.geoObjects.add(placemark);
			//searchControl.showResult(0);
		}
	})
	
}

function findAll(){
	//destinacije su na nivou aplikacije, ne na nivou avio kompanije
	$.ajax({
		type:'GET',
		url:'api/destinacije',
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderDestinacije,
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

function renderDestinacije(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	
	console.log(data);
	uloga=localStorage.getItem("uloga");
	
	if(uloga=="ROLE_AVIO" || uloga == "ROLE_ADMIN"){
		var th_nbsp=$('<th>&nbsp;</th>');
		var th_nbsp1=$('<th>&nbsp;</th>');
		$('#prikazDestinacijaTabela').find('tr:eq(0)').append(th_nbsp);
		$('#prikazDestinacijaTabela').find('tr:eq(0)').append(th_nbsp1);
	}
	$("#prikazDestinacijaTabela").find("tr:gt(0)").remove();
	$("#prikazDestinacijaTabela").find("th:gt(3)").remove();
	$.each(list, function(index, destinacija){
		$('#prikazDestinacijaTabela').append(get_row(destinacija, "destination", localStorage.getItem('uloga'), 'id01', 'id02'));
	})
	$('#prikazDestinacijaTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 3 }
	                   ]
	  });
}

function findAllAirplanes(){
	$.ajax({
		type:'GET',
		url:'api/avion',
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderAirplanes,
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

function renderAirplanes(data){
	/*var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	console.log(data);
	uloga=localStorage.getItem("uloga");
	
	if(uloga=="ROLE_AVIO" || uloga == "ROLE_ADMIN"){
		var th_nbsp=$('<th>&nbsp;</th>');
		var th_nbsp1=$('<th>&nbsp;</th>');
		$('#prikazAvionaTabela').find('tr:eq(0)').append(th_nbsp);
		$('#prikazAvionaTabela').find('tr:eq(0)').append(th_nbsp1);
	}
	$("#prikazAvionaTabela").find("tr:gt(0)").remove();
	$("#prikazAvionaTabela").find("th:gt(8)").remove();
	$.each(list, function(index, airplane){
		console.log("INDEX JEEEE: "+index);
		$('#prikazAvionaTabela').append(get_row(airplane, "airplane", localStorage.getItem('uloga'), 'id05', 'id06'));
	})
	$('#prikazAvionaTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 }
	                   ]
	  });*/
}


$(document).on('submit', ".modal-content3", function(e){
	e.preventDefault();
	console.log("dodavanje dest");
})

$(document).on('submit', "#newDestinationForma", function(e){
	e.preventDefault();
	console.log("dodavanje destinacije");
	var mesto=$("#mestoDestinacije").val();
	var slika=$('#slika_dest').val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	console.log(mesto);
	$.ajax({
		type:'POST',
		url:"api/destinacije",
		contentType:'application/json',
		dataType:'json',
		data:destinacijaToJSONadd(slika,mesto),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			$("#id03").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('newDestinationForma');
			$('#prikazDestinacijaTabela').DataTable().clear().destroy();
			findAll();
			//dodajNoviEntitet('prikazDestinacijaTabela', get_row(data, "destination", localStorage.getItem('uloga'), 'id05', 'id06'));
			//findAllUslugeByHotel();
			$.bootstrapGrowl("Destination added!", {
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

function destinacijaToJSONadd(slika, mesto){
	return JSON.stringify({
		"slika":slika,
		"adresa":mesto
	});
}

$(document).on('submit', "#deleteDestinacijaForma", function(e){
	e.preventDefault();
	var id=$("#identifikatorDestinacija").val();
	$.ajax({
		type : 'DELETE',
		url : '/api/destinacije/' + id,
		headers : {
			Authorization : "Bearer " + localStorage.getItem("accessToken")
		},
		success : function() {
			console.log('blaa');
			$('#destination_' + id).remove();
			$("#id01").css("display", "none");
			$("body").removeClass("modal-open");
			$('#prikazDestinacijaTabela').DataTable().clear().destroy();
			findAll();
			$.bootstrapGrowl("Succesful deletion!", {
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
			console.log("okdosdkaasdd");
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

function formaUpdatedestination(e, forma){
	e.preventDefault();
	var id_destinacije = $(forma).find('input[type=hidden]').val();
	console.log(id_destinacije);
	$.ajax({
		type:"GET",
		url:"api/destinacije/"+id_destinacije,
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	if(data.slika!=null && data.slika!=""){
        		$('#destinacija_img1').attr("src", data.slika);
        	}
        	console.log("["+data.slika+"]");
        	$("#mestoDestinacije1").val(data.adresa);
        	$("#identifikatorDestinacijeUpd").val(data.id);
        	
        }
		
	})
}

$(document).on('submit', "#editDestinationForma", function(e){
	e.preventDefault();
	var mesto=$("#mestoDestinacije1").val();
	console.log("MESTO JEEEEE "+mesto);
	var id=$("#identifikatorDestinacijeUpd").val();
	console.log(id);
	var slika = $('#slika_destinacija1').val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	
	if(slika=="" || slika==null){
		console.log("slika je null");
		slika=$("#destinacija_img1").attr("src");
		console.log("["+slika+"]");
	}
	$.ajax({
		type:"PUT",
		url:"api/destinacije/"+id,
		contentType:'application/json',
		dataType:'text',
		data:destinationToJSONUpd(id, slika,mesto),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	//window.location.replace("profilROLE_HOTEL.html");
        	$("#id02").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('editDestinationForma');
			$('#destination_'+id).remove();
			$('#prikazDestinacijaTabela').DataTable().clear().destroy();
			findAll();
			//dodajNoviEntitet('prikazDestinacijaTabela', get_row($.parseJSON(data), "destination", localStorage.getItem('uloga'), 'id01', 'id02'));
			$.bootstrapGrowl("Destination edited!", {
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
function destinationToJSONUpd(id, slika, mesto){
	return JSON.stringify({
		"id":id,
		"slika":slika,
		"adresa":mesto
	});
}

$(document).on('submit', "#newAirplaneForma", function(e){
	e.preventDefault();
	console.log("dodavanje aviona");
	var model=$("#modelAvion").val();
	var rows = $("#rowsAvion").val();
	var columns = $("#columnsAvion").val();
	var rowsEC = $("#rowsECAvion").val();
	var rowsBC = $("#rowsBCAvion").val();
	var rowsFC = $("#rowsFCAvion").val();
	var slika=$('#slika_avio2').val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	
	$.ajax({
		type:'POST',
		url:"api/avion",
		contentType:'application/json',
		dataType:'json',
		data:avionToJSONadd(slika,model,rows,columns,rowsEC,rowsBC,rowsFC),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			$("#id04").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('newAirplaneForma');
			$('#prikazAvionaTabela').DataTable().clear().destroy();
			findAll();
			//dodajNoviEntitet('prikazAvionaTabela', get_row(data, "airplane", localStorage.getItem('uloga'), 'id05', 'id06'));
			$.bootstrapGrowl("New airplane added!", {
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

function avionToJSONadd(slika,model,rows,columns,rowsEC,rowsBC,rowsFC){
	return JSON.stringify({
		"slika":slika,
		"model":model,
		"brojRedova":rows,
		"brojKolona":columns,
		"brojRedovaEC":rowsEC,
		"brojRedovaBC":rowsBC,
		"brojRedovaFC":rowsFC,
	});
}

$(document).on('submit', "#deleteAvionForma", function(e){
	e.preventDefault();
	var id=$("#identifikatorAviona").val();
	$.ajax({
		type : 'DELETE',
		url : '/api/avion/' + id,
		/*
		 * Milan: drugi nacin da token posaljete u zahtevu kroz ajax poziv kroz
		 * header
		 */
		headers : {
			Authorization : "Bearer " + localStorage.getItem("accessToken")
		},
		success : function() {
			console.log('blaa');
			$('#airplane_' + id).remove();
			$("#id05").css("display", "none");
			$("body").removeClass("modal-open");
			$('#prikazAvionaTabela').DataTable().clear().destroy();
			findAll();
			$.bootstrapGrowl("Succesful deletion!", {
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

function formaUpdateairplane(e, forma){
	e.preventDefault();
	var id_aviona = $(forma).find('input[type=hidden]').val();
	console.log(id_aviona);
	$.ajax({
		type:"GET",
		url:"api/avion/"+id_aviona,
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	if(data.slika!=null && data.slika!=""){
        		$('#avio_img1').attr("src", data.slika);
        	}
        	console.log("["+data.slika+"]");
        	$("#modelAvion1").val(data.model);
        	$("#rowsAvion1").val(data.brojRedova);
        	$("#columnsAvion1").val(data.brojKolona);
        	$("#rowsECAvion1").val(data.brojRedovaEC);
        	$("#rowsBCAvion1").val(data.brojRedovaBC);
        	$("#rowsFCAvion1").val(data.brojRedovaFC);
        	$("#identifikatorAvionaUpd").val(data.id);
        }
		
	})
}

$(document).on('submit', "#editAvionaForma", function(e){
	e.preventDefault();
	var model=$("#modelAvion1").val();
	var rows = $("#rowsAvion1").val();
	var cols = $("#columnsAvion1").val();
	var rowsEC = $("#rowsECAvion1").val();
	var rowsBC = $("#rowsBCAvion1").val();
	var rowsFC = $("#rowsFCAvion1").val();
	console.log("MODEL JEEEEE "+model);
	var id=$("#identifikatorAvionaUpd").val();
	console.log(id);
	var slika = $('#slika_avio3').val().replace(/C:\\fakepath\\/i,'..\\slike\\');
	
	if(slika=="" || slika==null){
		console.log("slika je null");
		slika=$("#avio_img3").attr("src");
		console.log("["+slika+"]");
	}
	$.ajax({
		type:"PUT",
		url:"api/avion/"+id,
		contentType:'application/json',
		dataType:'text',
		data:avionToJSONUpd(id,model,rows,cols,rowsEC,rowsBC,rowsFC,slika),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	//window.location.replace("profilROLE_HOTEL.html");
        	$("#id06").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('editAvionaForma');
			$('#airplane_'+id).remove();
			$('#prikazAvionaTabela').DataTable().clear().destroy();
			findAll();
			//dodajNoviEntitet('prikazAvionaTabela', get_row($.parseJSON(data), "airplane", localStorage.getItem('uloga'), 'id05', 'id06'));
			$.bootstrapGrowl("Airplane edited succesfully!", {
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
function avionToJSONUpd(id,model,rows,cols,rowsEC,rowsBC,rowsFC,slika){
	return JSON.stringify({
		"id":id,
		"model":model,
		"brojRedova":rows,
		"brojKolona":cols,
		"brojRedovaEC":rowsEC,
		"brojRedovaBC":rowsBC,
		"brojRedovaFC":rowsFC,
		"slika":slika
	});
}

$(document).on('submit', "#newFlightForma", function(e){
	e.preventDefault();
	console.log("dodavanje leta");
	var pocetnaDestinacija=$("#pocetnaDestinacija").val();
	var krajnjaDestinacija=$("#krajnjaDestinacija").val();
	
	var vremePolaska=$("#vremePolaska").val();
	console.log("Vreme polaska je: "+vremePolaska);
	
	var vremeDolaska=$("#vremeDolaska").val();
	console.log("Vreme dolaska je: "+vremeDolaska);
	
    var duzinaPutovanja=$("#duzinaPutovanja").val();
	
	var model = $("#modelAvio").val();
	var rows = $("#rowsAvio").val();
	var columns = $("#columnsAvio").val();
	var rowsEC = $("#rowsECAvio").val();
	var rowsBC = $("#rowsBCAvio").val();
	var rowsFC = $("#rowsFCAvio").val();
	
	$.ajax({
		type:'POST',
		url:"api/let",
		contentType:'application/json',
		dataType:'json',
		data:letToJSONadd(pocetnaDestinacija,krajnjaDestinacija,vremePolaska,vremeDolaska,duzinaPutovanja,model,rows,columns,rowsEC,rowsBC,rowsFC),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			$("#id04").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('newFlightForma');
			dodajNoviEntitet('prikazLetaTabela', get_row(data, "flight", localStorage.getItem('uloga'), 'id05', 'id06'));
			$.bootstrapGrowl("Flight added!", {
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

function letToJSONadd(pocetnaDestinacija,krajnjaDestinacija,vremePolaska,vremeDolaska,duzinaPutovanja,model,rows,columns,rowsEC,rowsBC,rowsFC){
	return JSON.stringify({
		"pocetnaDestinacija":pocetnaDestinacija,
		"krajnjaDestinacija":krajnjaDestinacija,
		"vremePolaska":vremePolaska,
		"vremeDolaska":vremeDolaska,
		"duzinaPutovanja":duzinaPutovanja,
		"model":model,
		"brojRedova":rows,
		"brojKolona":columns,
		"brojRedovaEC":rowsEC,
		"brojRedovaBC":rowsBC,
		"brojRedovaFC":rowsFC,
	});
}

function findAllFlightsByAvio(){
	$.ajax({
		type:'GET',
		url:'api/letoviKompanije',
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderFlights,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("flightsssss");
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

function renderFlights(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	console.log("render flights");
	uloga=localStorage.getItem("uloga");
	
	if(uloga=="ROLE_AVIO" || uloga == "ROLE_ADMIN"){
		var th_nbsp=$('<th>&nbsp;</th>');
		$('#prikazLetovaTabela').find('tr:eq(0)').append('<th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th>');
	}
	$("#prikazLetovaTabela").find("tr:gt(0)").remove();
	$("#prikazLetovaTabela").find("th:gt(9)").remove();
	$.each(list, function(index, flight){
		var row=get_row(flight, "flight", localStorage.getItem('uloga'), 'id05', 'id06');
		row.append('<td><a href="#detaljna-sedista"><button  onclick="selektovanLet(this)"><input type="hidden" id="'+flight.id+'">Make Quick</button></a></td>')
		$('#prikazLetovaTabela').append(row);
	})
	
	if ( ! $.fn.DataTable.isDataTable( '#prikazLetovaTabela' ) ) {
	$('#prikazLetovaTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "order":[[1,'asc']],
	      "columnDefs": [
	                     { "orderable": false, "targets": 0 },
	                     { "orderable": false, "targets": 7 }
	                   ]
	  });
	}
}

var firstSeatLabel = 1;
function renderDetaljanLet(){
	firstSeatLabel=1;
	$('#detaljna-sedista').empty();
	$('#detaljna-sedista').append('<div class="container">'+
				'<h3 id="relacija-leta"></h3>'+
				'<div id="seat-map" style="margin-left:400px; border-right:none">'+
					'<div class="front-indicator">Front</div>'+

				'</div>'+
				
			'</div>');
}

		
		function selektovanLet(btn) {
				renderDetaljanLet();
				var let_id=$(btn).find('input[type=hidden]').attr('id');
				$('#id-odabranog-leta').val(let_id);
				console.log(let_id);
				//dobaviti let
				$.ajax({
					type:'GET',
					url:'api/let/'+let_id,
					dataType:'json',
					beforeSend : function(request) {
						request.setRequestHeader("Authorization", "Bearer "
								+ localStorage.getItem("accessToken"));
					},
					success:function(data){
						$('#relacija-leta').text(data.pocetnaDestinacija+'-'+data.krajnjaDestinacija);
						$('.seatCharts-row').remove();
						//$('.booking-details').empty();
						var br_kolona=data.brojKolona;
						var br_redovaFC=data.brojRedovaFC;
						var br_redovaEC=data.brojRedovaEC;
						var br_redovaBC=data.brojRedovaBC;
						var lista=[];
						for(var i=1; i<=br_redovaFC; i++){
							var red='';
							for(var j=1; j<=br_kolona; j++){
								red+='f';
							}
							lista.push(red);
						}
						for(var i=1; i<=br_redovaEC; i++){
							var red='';
							for(var j=1; j<=br_kolona; j++){
								red+='e';
							}
							lista.push(red);
						}
						for(var i=1; i<=br_redovaBC; i++){
							var red='';
							for(var j=1; j<=br_kolona; j++){
								red+='b';
							}
							lista.push(red);
						}
						console.log(lista)
						var $cart = $('#selected-seats'),
						$counter = $('#counter'),
						$total = $('#total'),
						sc = $('#seat-map').seatCharts({
						map: lista,
						seats: {
							f: {
								price   : 100,
								classes : 'first-class', //your custom CSS class
								category: 'First Class'
							},
							e: {
								price   : 40,
								classes : 'economy-class', //your custom CSS class
								category: 'Economy Class'
							},
							b: {
								price:90,
								classes:'business-class',
								category:'Business Class'
							}					
						
						},
						naming : {
							top : false,
							getLabel : function (character, row, column) {
								return firstSeatLabel++;
							},
						},
						legend : {
							node : $('#legend'),
						    items : [
								[ 'f', 'available',   'First Class' ],
								[ 'e', 'available',   'Economy Class'],
								[ 'b', 'available',   'Business Class'],
								[ 'f', 'unavailable', 'Already Booked']
						    ]					
						},
						click: function () {
							if (this.status() == 'available') {
								console.log(this.settings.id);
								console.log(let_id);
								var row_col=this.settings.id;
								var price=$('#'+row_col)[0].attributes[6].value;
								
								selektovanoSediste(let_id, row_col, price);
								return 'available';
							} else if (this.status() == 'selected') {
								
								return 'selected';
							} else if (this.status() == 'unavailable') {
								//seat has been already booked
								return 'unavailable';
							} else {
								return this.style();
							}
						}
					});

					
					//let's pretend some seats have already been booked
					//sc.get(['1_2', '4_1', '7_1', '7_2']).status('unavailable');
					var rezervisana=[];
					$.each(data.sedista, function(index, sediste){
						
						$('#'+sediste.row_col).attr('value', sediste.cena);
						
						if(sediste.rezervisano==true){
							rezervisana.push(sediste.brojReda+'_'+sediste.brojKolone);
						}
					})
					//rezervisana.push(5+'_'+3);
					sc.get(rezervisana).status('unavailable');
					}
				})
				
		
		};
		
function selektovanoSediste(let_id, row_col, price){
	$('#quick-seat-flight-id').val(let_id);
	$('#quick-seat-row-col').val(row_col);
	$('#quick-seat-flight').text($('#relacija-leta').text());
	var tokens=row_col.split('_');
	$('#quick-seat-h1').text('Row: '+tokens[0]+' Column: '+tokens[1]);
	$('#quick-seat-old-cena').val(price);
	otvoriModal('id07');
	
}

function dodavanjeBrzeRezervacije(e){
	e.preventDefault();
	var let_id=$('#quick-seat-flight-id').val();
	console.log(let_id);
	var nova_cena=$('#quick-seat-new-cena').val();
	var row_col=$('#quick-seat-row-col').val();
	
	$.ajax({
		type:'POST',
		url:'api/quick/flight',
		contentType:'application/json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        data:brzoSedisteToJSON(let_id, row_col, nova_cena),
        success:function(data){
        	zatvoriModal('id07');
        }
	});
}

function brzoSedisteToJSON(let_id, row_col, nova_cena){
	return JSON.stringify({
		"id":let_id,
		"novaCena":nova_cena,
		"row_col":row_col
	});
}
