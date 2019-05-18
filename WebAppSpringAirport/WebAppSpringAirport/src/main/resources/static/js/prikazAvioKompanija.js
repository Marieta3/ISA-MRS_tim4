/**
 * 
 */
uloga="";

$(document).ready(function(){
	uloga=localStorage.getItem("uloga");
	if(uloga=="ROLE_ADMIN"){
		//$("#nav-bar").append('<li><a href="dodajAvioKompaniju.html">New Airline</a></li>');
		$("#nav-bar").append('<li><button class="dodajAvioBtn" onclick="otvoriModal(\'id03\')">New Airline</button></li>');
	}
});

findAll();
function findAll(){
	$.ajax({
		type:'GET',
		url:'api/avioKompanije',
		dataType:'json',
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:renderAvioKompanije
	})
}

function renderAvioKompanije(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	uloga=localStorage.getItem("uloga");
	if(uloga=="ROLE_ADMIN"){
		var th_nbsp=$('<th>&nbsp;</th>');
		var th_nbsp1=$('<th>&nbsp;</th>');
		$('#prikazAvioKompanijaTabela').find('tr:eq(0)').append(th_nbsp);
		$('#prikazAvioKompanijaTabela').find('tr:eq(0)').append(th_nbsp1);
	}
	//$("#prikazAvioKompanijaTabela tbody").empty();
	$("#prikazAvioKompanijaTabela").find("tr:gt(0)").remove();
	$("#prikazAvioKompanijaTabela").find("th:gt(6)").remove();
	$.each(list, function(index, avioKompanija){
		/*var tr=$('<tr id="avio_' + avioKompanija.id + '"></tr>');
		if (avioKompanija.slika == null){
			avioKompanija.slika = "../slike/aereo2.jpg";
		}
		tr.append('<td align="center" width=100px height=100px>'+ '<div id="divAviokompanija", class="divEntitet">' +
				'<img src=" ' + avioKompanija.slika +' " id="imgProfilnaAviokompanija" class="imgEntitet"> ' + '</div>' +
				'</td>'+'<td>' + avioKompanija.naziv + '</td>' + '<td>'
				+ avioKompanija.adresa + '</td>' + '<td>'
				+ avioKompanija.opis + '</td>'  );
		
		if (uloga == "ROLE_ADMIN") {
			var formaObrisi = $('<form id="formaObrisi" onsubmit="formaObrisi(event, this, \'identifikator\', \'nazivAdresaAvio\')"></form>');
			formaObrisi.append('<input type="hidden" value="' + avioKompanija.id + '">');
			formaObrisi.append('<input id="hiddenNazivAdresa" type="hidden" value="' + avioKompanija.naziv+", "+avioKompanija.adresa + '">');
			formaObrisi.append('<input type="submit" value="Delete" onclick="otvoriModal(\'id02\')">');
			var td = $('<td></td>');
			td.append(formaObrisi);
			tr.append(td);
		
			var formaUpdate = $('<form id="formaUpdate"></form>');
			formaUpdate.append('<input type="hidden" value="' + avioKompanija.id + '">');
			formaUpdate.append('<input type="submit" value="Update" onclick="otvoriModal(\'id01\')">');
			var td1 = $('<td></td>');
			td1.append(formaUpdate);
			tr.append(td1);
		}*/
		
		$('#prikazAvioKompanijaTabela').append(get_row(avioKompanija, "airline", localStorage.getItem('uloga'), 'id02', 'id01'));
		
	})
	$('#prikazAvioKompanijaTabela').DataTable({
	      "aLengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]],
	      "iDisplayLength": 5,
	      "columnDefs": [
	                     { "orderable": false, "targets": 4 }
	                   ]
	  });
}



function formaUpdateairline(e, forma){
	e.preventDefault();
	var id = $(forma).find('input[type=hidden]').val();
	$.ajax({
		type:"GET",
		url:"api/avioKompanije/"+id,
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	$("#h1NazivAvio").text(data.naziv+", "+data.adresa);
        	$("#nazivAvio").val(data.naziv);
        	$("#adresaAvio").val(data.adresa);
        	$("#opisAvio").val(data.opis);
        	
        	$("#identifikator").val(data.id);
        }
		
	})
}

$(document).on('submit', ".modal-content1", function(e){
	e.preventDefault();
	var naziv=$("#nazivAvio").val();
	var adresa=$("#adresaAvio").val();
	var opis=$("#opisAvio").val();
	//var ocena=$("#ocenaHotela").val();
	var id=$("#identifikator").val();
	$.ajax({
		type:"PUT",
		url:"api/avioKompanije/"+id,
		contentType:'application/json',
		dataType:'text',
		data:avioToJSON(id, naziv, adresa, opis),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
        success:function(data){
        	zatvoriModal('id01')
			$('#airline_'+id).remove();
        	$('#prikazAvioKompanijaTabela').DataTable().clear().destroy();
			findAll();
			//dodajNoviEntitet('prikazAvioKompanijaTabela', get_row($.parseJSON(data), "airline", localStorage.getItem('uloga'), 'id02', 'id01'));
        }
	})
	
})
$(document).on('submit', ".modal-content2", function(e){
	e.preventDefault();
	var id=$("#identifikator").val();
	$.ajax({
		type : 'DELETE',
		url : '/api/avioKompanije/' + id,
		/*
		 * Milan: drugi nacin da token posaljete u zahtevu kroz ajax poziv kroz
		 * header
		 */
		headers : {
			Authorization : "Bearer " + localStorage.getItem("accessToken")
		},
		success : function() {
			console.log('blaa');
			$('#airline_' + id).remove();
			$("#id02").css("display", "none");
			$("body").removeClass("modal-open");
			$('#prikazAvioKompanijaTabela').DataTable().clear().destroy();
			findAll();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("okdosdkaasdd");
			alert(errorThrown);
		}
	})
	
})

$(document).on('submit', "#newAvioForma", function(e){
	e.preventDefault();
	console.log("dodavanje avio kompanije");
	var naziv=$("#nazivAvio1").val();
	var adresa=$("#adresaAvio1").val();
	var opis=$("#opisAvio1").val();
	console.log("aaa"+opis);
	console.log("sss"+naziv);
	console.log("cccc"+adresa);
	$.ajax({
		type:'POST',
		url:"api/avioKompanije",
		contentType:'application/json',
		dataType:'json',
		data:avioToJSONadd(naziv, adresa, opis),
		beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("accessToken"));
        },
		success:function(data){
			$("#id03").css("display", "none");
			$("body").removeClass("modal-open");
			ponistavanje('newAvioForma');
			$('#prikazAvioKompanijaTabela').DataTable().clear().destroy();
			findAll();
			//dodajNoviEntitet('prikazAvioKompanijaTabela', get_row(data, "airline", localStorage.getItem('uloga'), 'id02', 'id01'));
		}
	});
})
function avioToJSONadd(naziv, adresa, opis){
	return JSON.stringify({
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis
	});
}
function avioToJSON(id, naziv, adresa, opis){
	return JSON.stringify({
		"id":id,
		"naziv":naziv,
		"adresa":adresa,
		"opis":opis
	});
}



$('document').ready(function() {
	$('#btnSearch').bind('click', searchAvioByName);
});

function searchAvioByName() {
	var naziv = $('#searchName').val();
	console.log(naziv);

	if (naziv) {
		$.ajax({
			type : 'GET',
			url : 'api/avioKompanije/search/' + naziv,
			dataType : 'json',
			success : renderAvioKompanije
		});
	} else{
		findAll();
	}
}