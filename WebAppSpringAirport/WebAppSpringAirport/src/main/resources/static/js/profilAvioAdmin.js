/**
 * 
 */
$(document).ready(function(){
	findAll();
	findAllAirplanes();
})


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
			console.log("okdosdkaasdd");
			alert(errorThrown);
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
			console.log("okdosdkaasdd");
			alert(errorThrown);
		}
	});
}

function renderAirplanes(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
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
	  });
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
		/*
		 * Milan: drugi nacin da token posaljete u zahtevu kroz ajax poziv kroz
		 * header
		 */
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
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("okdosdkaasdd");
			alert(errorThrown);
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
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("okdosdkaasdd");
			alert(errorThrown);
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
	console.log(vremePolaska);
	
	var vremeDolaska=$("#vremeDolaska").val();
	console.log(vremeDolaska);
	
    var duzinaPutovanja=$("#duzinaPutovanja").val();
	
	var model = $("#modelAvio")
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
		//data:letToJSONadd(pocetnaDestinacija,krajnjaDestinacija,vremePolaska,vremeDolaska,duzinaPutovanja,model,rows,columns,rowsEC,rowsBC,rowsFC),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			console.log(data); 
			$("#id07").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('newFlightForma');
			dodajNoviEntitet('prikazLetaTabela', get_row(data, "flight", localStorage.getItem('uloga'), 'id08', 'id09'));
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

function findAllFlights(){
	$.ajax({
		type:'GET',
		url:'api/let',
		dataType:'json',
		beforeSend : function(request) {
			request.setRequestHeader("Authorization", "Bearer "
					+ localStorage.getItem("accessToken"));
		},
		success:renderFlights,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("flightsssss");
			alert(errorThrown);
		}
	});
}

function renderFlights(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	console.log(data);
	uloga=localStorage.getItem("uloga");
	
	if(uloga=="ROLE_AVIO" || uloga == "ROLE_ADMIN"){
		var th_nbsp=$('<th colspan="2">&nbsp;</th>');
		$('#prikazLetovaTabela').find('tr:eq(0)').append(th_nbsp);
	}
	$("#prikazLetovaTabela").find("tr:gt(0)").remove();
	$("#prikazLetovaTabela").find("th:gt(7)").remove();
	$.each(list, function(index, flight){
		console.log("INDEX JEEEE: "+index);
		$('#prikazLetovaTabela').append(get_row(flight, "flight", localStorage.getItem('uloga'), 'id08', 'id09'));
	})
}


