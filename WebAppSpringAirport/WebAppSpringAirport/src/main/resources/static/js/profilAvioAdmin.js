/**
 * 
 */
$(document).ready(function(){
	findAll();
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
		var th_nbsp=$('<th colspan="2">&nbsp;</th>');
		$('#prikazDestinacijaTabela').find('tr:eq(0)').append(th_nbsp);
	}
	$("#prikazDestinacijaTabela").find("tr:gt(0)").remove();
	$("#prikazDestinacijaTabela").find("th:gt(5)").remove();
	$.each(list, function(index, destinacija){
		$('#prikazDestinacijaTabela').append(get_row(destinacija, "destination", localStorage.getItem('uloga'), 'id01', 'id02'));
	})
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
			dodajNoviEntitet('prikazDestinacijaTabela', get_row(data, "destination", localStorage.getItem('uloga'), 'id05', 'id06'));
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
			//get_row(data);
			console.log(data);
			dodajNoviEntitet('prikazDestinacijaTabela', get_row($.parseJSON(data), "destination", localStorage.getItem('uloga'), 'id01', 'id02'));

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

